package com.tulinghuo.studyenglish;

import android.app.Application;

import com.tulinghuo.studyenglish.util.TokenManager;

public class MyApp extends Application {
    private static TokenManager tokenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        tokenManager = new TokenManager(this); // 直接使用Application Context
    }

    public static TokenManager getTokenManager() {
        return tokenManager;
    }
}