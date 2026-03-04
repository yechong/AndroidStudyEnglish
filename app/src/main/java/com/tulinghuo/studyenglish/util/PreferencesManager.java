package com.tulinghuo.studyenglish.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static PreferencesManager instance;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "app_preferences";

    private PreferencesManager(Context context) {
        // 使用Application Context避免内存泄漏
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context);
        }
        return instance;
    }

    // 保存字符串
    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    // 获取字符串
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // 保存布尔值
    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    // 获取布尔值
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // 保存整型
    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    // 获取整型
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // 保存长整型
    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    // 获取长整型
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    // 保存浮点型
    public void putFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    // 获取浮点型
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    // 移除某个键值对
    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    // 清除所有数据
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    // 检查是否包含某个键
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }
}