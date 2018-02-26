package com.zaixin.demo.controller;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping
public class SayHiController {
    @RequestMapping(value = "getUserId",produces ="text/plain;charset=UTF-8")
    public String getUserId(Map<String,Object> map, HttpSession session){
        AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse= (AlipaySystemOauthTokenResponse) session.getAttribute("alipaySystemOauthTokenResponse");
        map.put("msg","获取用户基本信息(token="+alipaySystemOauthTokenResponse.getAccessToken()+"|UserId="+alipaySystemOauthTokenResponse.getAlipayUserId()+")");
        return "sayHi";
    }

    @RequestMapping(value = "x",produces ="text/plain;charset=UTF-8")
    public String getUserInfo(Map<String,Object> map,HttpSession session){
        AlipayUserInfoShareResponse alipayUserInfoShareResponse= (AlipayUserInfoShareResponse) session.getAttribute("alipayUserInfoShareResponse");
        map.put("msg","获取用户详细信息(UserId="+alipayUserInfoShareResponse.getUserId()+"|省"
                +alipayUserInfoShareResponse.getProvince()+"手机号"+alipayUserInfoShareResponse.getPhone()+"邮箱"+alipayUserInfoShareResponse.getEmail());
        return "sayHi";
    }
}
