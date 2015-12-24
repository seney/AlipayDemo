package com.lwjfork.alipay;


import com.lwjfork.lib_alipay.utils.OrderInfoModel;

import java.net.URLDecoder;

public class ServerOrderInfo extends OrderInfoModel {

	public ServerOrderInfo() {
		super();

		this.orderInfo = "partner%3D%25222088901478526505%2522%26seller_id%3D%25222088901478526505%2522%26out_trade_no%3D%2522S1000000033P34%2522%26subject%3D%2522%25E7%2588%25B1%25E5%25AE%25A0%25E5%259B%25A2%25E8%25AE%25A2%25E5%258D%2595%2523S1000000033P34%2522%26body%3D%2522%25E7%2588%25B1%25E5%25AE%25A0%25E5%259B%25A2%25E8%25AE%25A2%25E5%258D%2595%2523S1000000033P34%2522%26total_fee%3D%25221768%2522%26notify_url%3D%2522http%25253A%25252F%25252Ftest.orderapi.goumin.com%25252Fnotify%25252Falipaydirectapp%2522%26service%3D%2522mobile.securitypay.pay%2522%26_input_charset%3D%2522utf-8%2522%26return_url%3D%2522http%253A%252F%252Ftest.goumin.com%252Forder-list%2522%26it_b_pay%3D%25221d%2522%26show_url%3D%2522http%253A%252F%252Faichongtuan.newgoumin.com%252Forder-list%2522"
		;
		this.orderInfo = URLDecoder.decode(this.orderInfo);
		this.orderInfo = URLDecoder.decode(this.orderInfo);
		this.sign = "es59FOhQI5R9ok/tY+k4ujCHiJmYr8Skbm0Kipjj38aGYuUX" +
				"I14fJ3QxEwuftTPOgx/Ca/TK46MIIpLLFoEFaj2TcuiHEDOp76PRSP" +
				"G12bmNz7WWRzdSnkLHvfhJFI8UhqKL6U+BdoDjnA/zqvvhcTauWEkJbXeVf" +
				"VsWaCv6mrw="
		;
		this.sign_type = "RSA";
	}
}
