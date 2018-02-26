package com.zaixin.demo;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.zaixin.demo.constants.AlipayService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		//在SDK调用前需要进行初始化，以Java代码为示例如下：
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayService.URL, AlipayService.APPID, AlipayService.APP_PRIVATE_KEY, AlipayService.FORMAT, AlipayService.CHARSET, AlipayService.ALIPAY_PUBLIC_KEY, AlipayService.SIGN_TYPE);
	}
}
