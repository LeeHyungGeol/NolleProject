package com.example.adefault.util;

public class UserToken {

    private static String token = "";
    private static String url = "http://f5ad55a614fb.ngrok.io";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        UserToken.url = url;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserToken.token = token;
    }
}
