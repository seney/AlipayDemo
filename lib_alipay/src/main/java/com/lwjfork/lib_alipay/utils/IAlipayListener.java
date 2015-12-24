package com.lwjfork.lib_alipay.utils;


import com.lwjfork.lib_alipay.constant.PayResult;

/**
 * Created by lwj on 2015/7/29.
 */
public interface IAlipayListener {

       void paySuccess(PayResult payResult);
       void payFail(PayResult payResult);

       void payWaitConfirm(PayResult payResult);


}
