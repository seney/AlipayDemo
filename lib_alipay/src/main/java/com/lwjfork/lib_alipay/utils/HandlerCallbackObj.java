package com.lwjfork.lib_alipay.utils;

public class HandlerCallbackObj<T extends  OrderInfoModel> {
	public String result;
	public boolean isExist;
	public T orderInfoModel;
	public IAlipayListener alipayListener;
}
