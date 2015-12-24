package com.lwjfork.lib_alipay.utils;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lwjfork.lib_alipay.constant.PayResult;


public class AlipayHandler<T extends  OrderInfoModel> extends Handler {
	 public static String TAG = "AlipayHandler";
	 
	public static final int SDK_PAY_FLAG = 1;

	public static final int SDK_CHECK_FLAG = 2;
	
	
	public Activity activity;
	public T orderInfoModel;
	public IAlipayListener alipayListener;
	public AlipayHandler(Activity activity) {
		super();
		this.activity = activity;
	}
	

	@Override
	public void handleMessage(android.os.Message msg) {

        switch (msg.what) {
            case SDK_PAY_FLAG:
                Log.d(TAG, "result " + msg.toString());
                
                
                @SuppressWarnings("unchecked")
				HandlerCallbackObj<T> handlerCallbackObj = (HandlerCallbackObj<T>)msg.obj;
                String result = handlerCallbackObj.result;
                orderInfoModel =  handlerCallbackObj.orderInfoModel;
                alipayListener = handlerCallbackObj.alipayListener;
                
                
                PayResult payResult = new PayResult(result);
             
                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();

                String resultStatus = payResult.getResultStatus();

                Log.d(TAG, "result code " + resultInfo + " msg "
                        + resultInfo);

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    //TODO 支付成功
                    alipayListener.paySuccess(payResult);
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        //TODO 支付结果确认中
                        //  UtilWidget.showToast(mContext, "支付结果确认中");
                        alipayListener.payWaitConfirm(payResult);

                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        //TODO 支付失败
                        // UtilWidget.showToast(mContext, "支付失败");
                        alipayListener.payFail(payResult);
                    }
                }
                break;
            case SDK_CHECK_FLAG:
            	Toast.makeText(activity, "true", Toast.LENGTH_LONG).show();
            	break;
            
            default:
                break;
        }

	}
}
