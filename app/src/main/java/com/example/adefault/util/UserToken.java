package com.example.adefault.util;

public class UserToken {

    private static String token = "";
    private static String url = "http://9941c96d004e.ngrok.io";

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
