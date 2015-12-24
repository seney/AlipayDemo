package com.lwjfork.lib_alipay.utils;

import android.app.Activity;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.alipay.sdk.app.PayTask;
import com.lwjfork.lib_alipay.config.PartnerConfig;
import com.lwjfork.lib_alipay.constant.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lwj on 2015/7/29.
 */
public class AliPayUtils<T extends OrderInfoModel> {
	public static String TAG = "AliPayUtils";

	private volatile static AliPayUtils<?> aliPayUtils;

	/** Returns singleton class instance */

	public static AliPayUtils getInstance() {
		if (aliPayUtils == null) {
			synchronized (AliPayUtils.class) {
				if (aliPayUtils == null) {
					aliPayUtils = new AliPayUtils();
				}
			}
		}
		return aliPayUtils;
	}

	AlipayHandler<T> mHandler;

	/**
	 * 客户端签名
	 * 
	 * @param orderInfoModel
	 * @param alipayListener
	 */
	public void payClientSign(Activity activity, T orderInfoModel,
			IAlipayListener alipayListener) {
		initHandler(activity);
		String payInfo = getPayInfo(orderInfoModel, true);
		startPay(activity, payInfo, orderInfoModel, alipayListener);

	}
   /**
    * 初始化  handler
    * @param activity
    */
	private void initHandler(Activity activity){
		if(mHandler == null){
			mHandler = new AlipayHandler<T>(activity);
		}
	}
	
	
	/**
	 * 服务端签名
	 * 
	 * @param orderInfoModel
	 * @param alipayListener
	 */
	public void pay(Activity activity, T orderInfoModel,
			IAlipayListener alipayListener) {
		initHandler(activity);
		String payInfo = getPayInfo(orderInfoModel, false);
		Log.e("payInfo", payInfo);
		startPay(activity, payInfo, orderInfoModel, alipayListener);
	}

	/**
	 * 支付
	 * 
	 * @param activity
	 * @param payInfo
	 * @param orderInfoModel
	 * @param alipayListener
	 */
	public void startPay(final Activity activity, final String payInfo,
			T orderInfoModel, IAlipayListener alipayListener) {
		aliPay(activity, payInfo,orderInfoModel,alipayListener);
	}

	/**
	 * 支付
	 * 
	 * @param activity
	 * @param payInfo
	 * @param orderInfoModel
	 * @param alipayListener
	 */
	public void aliPay(final Activity activity, final String payInfo,
			T orderInfoModel, IAlipayListener alipayListener) {
		if(mHandler == null){
			mHandler = new AlipayHandler<T>(activity);
		}
		if(callbackObj == null){
			callbackObj = new HandlerCallbackObj<T>();
		}
		callbackObj.alipayListener = alipayListener;
		callbackObj.orderInfoModel = orderInfoModel;
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = AlipayHandler.SDK_PAY_FLAG;
				callbackObj.result = result;
				msg.obj = callbackObj;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 完整的符合支付宝参数规范的订单信息
	 * 
	 * @param orderInfoModel
	 * @param isClientSign
	 * @return
	 */
	public String getPayInfo(T orderInfoModel, boolean isClientSign) {

		// 订单
		String string = orderInfoModel.getOrderInfo();
		String sign = orderInfoModel.getSign();
		String signType = orderInfoModel.getSign_type();

		if (isClientSign) {
			// 对订单做RSA 签名
			sign = sign(string);
			System.out.println("sign "+sign);
			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
        Log.e("sign", sign);
		
			signType = getSignType(signType);
		

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = string + "&sign=\"" + sign + "\"&" + signType;
		return payInfo;
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion(Activity activity) {
		PayTask payTask = new PayTask(activity);
		String version = payTask.getVersion();
		Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, PartnerConfig.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType(String signType) {
		if(TextUtils.isEmpty(signType)){
			return "sign_type=\"RSA\"";
		}else{
			return "sign_type=\""+signType + "\"";
					
		}
		
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	HandlerCallbackObj<T> callbackObj ;
	public void check(final Activity activity) {
		if(mHandler == null){
			mHandler = new AlipayHandler<T>(activity);
		}
		if(callbackObj == null){
			callbackObj = new HandlerCallbackObj<T>();
		}
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(activity);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = AlipayHandler.SDK_CHECK_FLAG;
				callbackObj.isExist = isExist;
				msg.obj = callbackObj;
				
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}
}
