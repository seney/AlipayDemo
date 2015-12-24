package com.lwjfork.alipay;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.lwjfork.lib_alipay.config.PartnerConfig;
import com.lwjfork.lib_alipay.constant.PayResult;
import com.lwjfork.lib_alipay.utils.AliPayUtils;
import com.lwjfork.lib_alipay.utils.IAlipayListener;


public class AlipaydemoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_main);
    }


    @SuppressWarnings("unchecked")
    public void testPayClient(){
        // 订单
        ClientOrderInfo orderInfoModel = new ClientOrderInfo();
        orderInfoModel.orderInfo = getOrderInfo(orderInfoModel);
        AliPayUtils.getInstance().payClientSign(this, orderInfoModel, new IAlipayListener() {

            @Override
            public void payWaitConfirm(PayResult payResult) {
                Toast.makeText(AlipaydemoActivity.this, "确认中/Confirming : " + payResult.getResultStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void paySuccess(PayResult payResult) {
                Toast.makeText(AlipaydemoActivity.this, "成功/Success : " + payResult.getResultStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void payFail(PayResult payResult) {
                Toast.makeText(AlipaydemoActivity.this, "失败/Failure : " + payResult.getResultStatus(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @SuppressWarnings("unchecked")
    public void testPayServer(){
        // 订单
        ServerOrderInfo orderInfoModel = new ServerOrderInfo();
        /**
         * 必须赋值
         *    注意  是否 encode 编码了
         *             支付宝接受 待签名字串 是 明文  不可  encode 编码
         *                       签名字串  必须是 encode 过一次的
        orderInfoModel.sign = "";
        orderInfoModel.orderInfo = "";
        orderInfoModel.sign_type = "";
         sign_type 可以不赋值
       **/

        AliPayUtils.getInstance().pay(this, orderInfoModel, new IAlipayListener() {

            @Override
            public void payWaitConfirm(PayResult payResult) {
                // TODO Auto-generated method stub
                Toast.makeText(AlipaydemoActivity.this, "确认中/Confirming : " + payResult.getResultStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void paySuccess(PayResult payResult) {
                // TODO Auto-generated method stub
                Toast.makeText(AlipaydemoActivity.this, "成功/Success : " + payResult.getResultStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void payFail(PayResult payResult) {
                // TODO Auto-generated method stub
                Toast.makeText(AlipaydemoActivity.this, "失败/Failure : " + payResult.getResultStatus(), Toast.LENGTH_LONG).show();

            }
        });
    }


    /**
     * call alipay sdk pay. 调用SDK支付
     *     客户端进行签名
     *
     */
    @SuppressWarnings("unchecked")
    public void clientsignpay(View v) {

        testPayClient();

    }
    /**
     * call alipay sdk pay. 调用SDK支付
     *    服务端进行签名
     */
    @SuppressWarnings("unchecked")
    public void serversignpay(View v) {

        testPayServer();

    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     *
     */
    public void check(View v){
        AliPayUtils.getInstance().check(this);

    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(ClientOrderInfo orderInfoModel) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + PartnerConfig.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderInfoModel.orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + orderInfoModel.subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + orderInfoModel.body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + orderInfoModel.price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
