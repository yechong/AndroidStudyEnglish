package com.tulinghuo.studyenglish.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    private static OkHttpClient client;
    private static Gson gson;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //    private static final String BASE_URL = "http://local.studyenglish.club";
    private static final String BASE_URL = "https://study.tulinghuo.com";

    // 初始化OkHttpClient和Gson
    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        gson = new Gson();
    }

    private static String buildUrl(String url) {
        return BASE_URL + url;
    }

    /**
     * 同步GET请求
     *
     * @param url 请求地址
     * @return 响应字符串
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(buildUrl(url))
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                throw new IOException("请求失败，状态码：" + response.code());
            }
        }
    }

    /**
     * 带参数的同步GET请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String get(String url, Map<String, String> params) throws IOException {
        // 构建带参数的URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(buildUrl(url)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                throw new IOException("请求失败，状态码：" + response.code());
            }
        }
    }

    /**
     * 异步GET请求
     *
     * @param url      请求地址
     * @param callback 回调接口
     */
    public static void getAsync(String url, final HttpCallback callback) {
        Request request = new Request.Builder()
                .url(buildUrl(url))
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().string());
                }
                else {
                    callback.onFailure(new IOException("请求失败，状态码：" + response.code()));
                }
                response.close();
            }
        });
    }

    /**
     * 同步POST请求（JSON格式）
     *
     * @param url      请求地址
     * @param jsonBody JSON字符串
     * @return 响应字符串
     * @throws IOException
     */
    public static String postJson(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(buildUrl(url))
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                throw new IOException("请求失败，状态码：" + response.code());
            }
        }
    }

    /**
     * 同步POST请求（将Java对象转为JSON）
     *
     * @param url    请求地址
     * @param object Java对象
     * @return 响应字符串
     * @throws IOException
     */
    public static String postJson(String url, Object object) throws IOException {
        String jsonBody = gson.toJson(object);
        return postJson(url, jsonBody);
    }

    /**
     * 同步POST请求（Map参数）
     *
     * @param url    请求地址
     * @param params Map参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String postJson(String url, Map<String, Object> params) throws IOException {
        String jsonBody = gson.toJson(params);
        return postJson(url, jsonBody);
    }

    /**
     * 异步POST请求（JSON格式）
     *
     * @param url      请求地址
     * @param jsonBody JSON字符串
     * @param callback 回调接口
     */
    public static void postJsonAsync(String url, String jsonBody, final HttpCallback callback) {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(buildUrl(url))
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().string());
                }
                else {
                    callback.onFailure(new IOException("请求失败，状态码：" + response.code()));
                }
                response.close();
            }
        });
    }

    /**
     * 异步POST请求（Java对象）
     *
     * @param url      请求地址
     * @param object   Java对象
     * @param callback 回调接口
     */
    public static void postJsonAsync(String url, Object object, final HttpCallback callback) {
        String jsonBody = gson.toJson(object);
        postJsonAsync(url, jsonBody, callback);
    }

    /**
     * 异步发送 POST form-data 表单请求
     *
     * @param url      请求地址
     * @param params   表单参数键值对
     * @param callback 回调接口
     */
    public static void postFormDataAsync(String url, Map<String, String> params, final HttpCallback callback) {
        // 构建 multipart form-data 请求体
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        // 添加表单参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && value != null) {
                    // 添加文本表单字段
                    multipartBuilder.addFormDataPart(key, value);
                }
            }
        }

        RequestBody body = multipartBuilder.build();

        // 构建请求
        Request request = new Request.Builder()
                .url(buildUrl(url))
                .post(body)
                .addHeader("Content-Type", MultipartBody.FORM.toString())
                .build();

        // 发送异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().string());
                }
                else {
                    callback.onFailure(new IOException("请求失败，状态码：" + response.code()));
                }
                response.close();
            }
        });
    }

    /**
     * 带Headers的POST请求
     *
     * @param url      请求地址
     * @param jsonBody JSON字符串
     * @param headers  Headers
     * @return 响应字符串
     * @throws IOException
     */
    public static String postJsonWithHeaders(String url, String jsonBody, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(buildUrl(url))
                .post(RequestBody.create(jsonBody, JSON));

        // 添加自定义Headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                throw new IOException("请求失败，状态码：" + response.code());
            }
        }
    }

    /**
     * 解析JSON响应为Java对象
     *
     * @param response JSON响应字符串
     * @param classOfT 目标类
     * @return Java对象
     */
    public static <T> T parseResponse(String response, Class<T> classOfT) {
        return gson.fromJson(response, classOfT);
    }

    /**
     * 解析JSON响应为JsonObject
     *
     * @param response JSON响应字符串
     * @return JsonObject
     */
    public static JsonObject parseToJsonObject(String response) {
        return JsonParser.parseString(response).getAsJsonObject();
    }

    /**
     * HTTP请求回调接口
     */
    public interface HttpCallback {
        void onSuccess(String response);

        void onFailure(Exception e);
    }

    /**
     * 简单回调实现示例（可选）
     */
    public static abstract class SimpleHttpCallback implements HttpCallback {
        @Override
        public void onSuccess(String response) {
            // 默认实现
        }

        @Override
        public void onFailure(Exception e) {
            e.printStackTrace();
        }
    }
}