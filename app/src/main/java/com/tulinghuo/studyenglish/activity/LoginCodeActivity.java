package com.tulinghuo.studyenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tulinghuo.studyenglish.MyApp;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.util.CommonUtil;
import com.tulinghuo.studyenglish.util.Constant;
import com.tulinghuo.studyenglish.util.HttpUtil;
import com.tulinghuo.studyenglish.vo.HttpResponseVO;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

public class LoginCodeActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private TextView send_txt;
    private EditText code_et;
    private String email;
    private boolean allowSendCode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_code);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = getIntent().getStringExtra("email");

        initViews();

        // 这里是防止刷请求
        if (Constant.sendSecond >= 60) {
            doSendEmailCode();
        }
        else {
            startCountdown();
        }
    }

    private void initViews() {
        ImageView backIV = findViewById(R.id.back_iv);
        TextView login_tv = findViewById(R.id.login_tv);
        code_et = findViewById(R.id.code_et);
        send_txt = findViewById(R.id.send_txt);

        backIV.setOnClickListener(v -> finish());

        login_tv.setOnClickListener(v -> {
            String code = code_et.getText().toString();
            if (CommonUtil.isBlank(code)) {
                runOnUiThread(() -> Toast.makeText(LoginCodeActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show());
            }
            else {
                doLogin();
            }
        });

        send_txt.setOnClickListener(v -> {
            if (allowSendCode) {
                doSendEmailCode();
            }
        });
    }

    private void doSendEmailCode() {
        allowSendCode = false;
        LoadingDialog ld = new LoadingDialog(this);
        ld.setLoadingText("发送验证码中，请稍后...");
        ld.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        HttpUtil.postFormDataAsync("/api_user.php?method=send_mail_code", params, new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("LoginCodeActivity", "response -> " + response);
                Gson gson = new Gson();
                HttpResponseVO responseVO = gson.fromJson(response, HttpResponseVO.class);
                if (!responseVO.isRequestSuccess()) {
                    runOnUiThread(() -> Toast.makeText(LoginCodeActivity.this, responseVO.getMsg(), Toast.LENGTH_SHORT).show());
                    allowSendCode = true;
                }
                else {
                    startCountdown();
                }
                runOnUiThread(ld::close);
            }

            @Override
            public void onFailure(Exception e) {
                allowSendCode = true;
                runOnUiThread(ld::close);
            }
        });
    }

    private void doLogin() {
        LoadingDialog ld = new LoadingDialog(this);
        ld.setLoadingText("发送验证码中，请稍后...");
        ld.show();
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("code", code_et.getText().toString().trim());
        HttpUtil.postFormDataAsync("/api_user.php?method=login", params, new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("LoginCodeActivity", "doLogin -> " + response);
                runOnUiThread(ld::close);

                Gson gson = new Gson();
                HttpResponseVO responseVO = gson.fromJson(response, HttpResponseVO.class);
                if (!responseVO.isRequestSuccess()) {
                    runOnUiThread(() -> Toast.makeText(LoginCodeActivity.this, responseVO.getMsg(), Toast.LENGTH_SHORT).show());
                }
                else {
                    JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                    String token = jsonObject.getAsJsonObject("data").get("token").getAsString();
                    MyApp.getTokenManager().saveToken(token);

                    Intent intent = new Intent(LoginCodeActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    runOnUiThread(() -> {
                        startActivity(intent);
                        finish();
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(ld::close);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void startCountdown() {
        CommonUtil.startCountDown();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (Constant.sendSecond > 0) {
                    allowSendCode = false;
                    runOnUiThread(() -> {
                        send_txt.setText(Constant.sendSecond + "s后重发");
                        send_txt.setTextColor(ContextCompat.getColor(LoginCodeActivity.this, R.color.un_active));
                    });
                    handler.postDelayed(this, 1000);
                }
                else {
                    allowSendCode = true;
                    runOnUiThread(() -> {
                        send_txt.setText("重新发送");
                        send_txt.setTextColor(ContextCompat.getColor(LoginCodeActivity.this, R.color.blue2));
                    });
                }
            }
        });
    }
}