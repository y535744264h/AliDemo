package com.zaixin.demo.filter;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.google.gson.Gson;
import com.zaixin.demo.constants.AlipayService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class OAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String state=servletRequest.getParameter("state");
        if(state!=null&&"Test".equals(state)){
            String app_id=servletRequest.getParameter("app_id");
            String auth_code=servletRequest.getParameter("auth_code");
            String scope=servletRequest.getParameter("scope");
            AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse=getAccessToken(auth_code,"code");
            HttpSession session=((HttpServletRequest)servletRequest).getSession();
            session.setAttribute("alipaySystemOauthTokenResponse",alipaySystemOauthTokenResponse);
            if(scope!=null&&"auth_user".equals(scope)){
                AlipayUserInfoShareResponse alipayUserInfoShareResponse=getUserInfo(alipaySystemOauthTokenResponse.getAccessToken());
                session.setAttribute("alipayUserInfoShareResponse",alipayUserInfoShareResponse);
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            //拦截受保护请求
            String requestURL=((HttpServletRequest)servletRequest).getRequestURL().toString();
            System.out.println("请求地址="+requestURL);
            String type="";
            if(requestURL.contains("getUserId")){
                type="auth_base";
            }else if(requestURL.contains("getUserInfo")){
                type="auth_user";
            }else{
                    type="auth_user";
            }
            requestURL=URLEncoder.encode(requestURL,"UTF-8");
            System.out.println("请求地址="+requestURL);
            StringBuffer OAuthUrl=new StringBuffer("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm");
            OAuthUrl.append("?app_id="+ AlipayService.APPID);//开发者应用的app_id
            OAuthUrl.append("&scope="+type);//接口权限值，目前只支持auth_user和auth_base两个值
            OAuthUrl.append("&redirect_uri="+ requestURL);//回调页面，是 经过转义 的url链接（url必须以http或者https开头），比如：http%3A%2F%2Fexample.com在请求之前，开发者需要先到开发者中心对应应用内，配置授权回调地址。
            OAuthUrl.append("&state=Test");//商户自定义参数，用户授权后，重定向到redirect_uri时会原样回传给商户。 为防止CSRF攻击，建议开发者请求授权时传入state参数，该参数要做到既不可预测，又可以证明客户端和当前第三方网站的登录认证状态存在关联。
            /**
             * 关于scope的说明：
             * auth_base：以auth_base为scope发起的网页授权，是用来获取进入页面的用户的userId的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（通常是业务页面）。
             * auth_user：以auth_user为scope发起的网页授权，是用来获取用户的基本信息的（比如头像、昵称等）。但这种授权需要用户手动同意，用户同意后，就可在授权后获取到该用户的基本信息。若想获取用户信息，scope的值中需要有该值存在，如scope=auth_user,auth_base。
             */
            System.out.println("转发至="+OAuthUrl.toString());
            ((HttpServletResponse)servletResponse).sendRedirect(OAuthUrl.toString());
        }

    }

    @Override
    public void destroy() {

    }
    //使用auth_code换取接口access_token及用户userId
    //换取授权访问令牌，开发者可通过获取到的auth_code换取access_token和用户userId。auth_code作为换取access_token的票据，
    //每次用户授权完成，回调地址中的auth_code将不一样，auth_code只能使用一次，一天未被使用自动过期。
    public AlipaySystemOauthTokenResponse getAccessToken(String code,String authorizationCode){
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayService.APPID, AlipayService.APP_PRIVATE_KEY,AlipayService.FORMAT, AlipayService.CHARSET, AlipayService.ALIPAY_PUBLIC_KEY,AlipayService.SIGN_TYPE);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);//用户对应用授权后得到，即第二步中开发者获取到的auth_code值
        request.setGrantType("authorization_code");//	值为authorization_code时，代表用code换取；值为refresh_token时，代表用refresh_token换取
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            return oauthTokenResponse;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public AlipayUserInfoShareResponse getUserInfo(String accountToKen){
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayService.APPID, AlipayService.APP_PRIVATE_KEY,AlipayService.FORMAT, AlipayService.CHARSET, AlipayService.ALIPAY_PUBLIC_KEY, AlipayService.SIGN_TYPE);
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        try {
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, accountToKen);
            return userinfoShareResponse;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }
}
