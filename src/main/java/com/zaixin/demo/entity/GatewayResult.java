package com.zaixin.demo.entity;

public class GatewayResult {

    private AlipaySystemOauthTokenResponseBean alipay_system_oauth_token_response;
    private String sign;

    public AlipaySystemOauthTokenResponseBean getAlipay_system_oauth_token_response() {
        return alipay_system_oauth_token_response;
    }

    public void setAlipay_system_oauth_token_response(AlipaySystemOauthTokenResponseBean alipay_system_oauth_token_response) {
        this.alipay_system_oauth_token_response = alipay_system_oauth_token_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class AlipaySystemOauthTokenResponseBean {
        /**
         * access_token : publicpBa869cad0990e4e17a57ecf7c5469a4b2
         * user_id : 2088411964574197
         * expires_in : 300
         * re_expires_in : 300
         * refresh_token : publicpB0ff17e364f0743c79b0b0d7f55e20bfc
         */

        private String access_token;
        private String user_id;
        private int expires_in;
        private int re_expires_in;
        private String refresh_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public int getRe_expires_in() {
            return re_expires_in;
        }

        public void setRe_expires_in(int re_expires_in) {
            this.re_expires_in = re_expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }
}
