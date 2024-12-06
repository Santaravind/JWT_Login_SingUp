package com.JWT.Authentication;

public class LoginResponce {

    private String token;
    private long tokenExpireTime;

    public LoginResponce(String token, long tokenExpireTime) {
        this.token = token;
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public LoginResponce() {
    }
}
