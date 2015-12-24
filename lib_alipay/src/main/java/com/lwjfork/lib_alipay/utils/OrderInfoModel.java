package com.lwjfork.lib_alipay.utils;

/**
 * Created by lwj on 2015/7/29.
 */
public class OrderInfoModel {

    public String orderInfo;// 待签名的字符串
    public String sign;// 签名
    public String sign_type = "";// 签名类型


    public String getOrderInfo() {
        return orderInfo;
    }
    public void setString(String orderInfo) {
        this.orderInfo = orderInfo;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign_type() {
        return sign_type;
    }
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }



}
