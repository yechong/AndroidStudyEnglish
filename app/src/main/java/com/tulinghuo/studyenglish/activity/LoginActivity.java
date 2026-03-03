package com.tulinghuo.studyenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.util.CommonUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText email_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
    }

    private void initViews() {
        ImageView backIV = findViewById(R.id.back_iv);
        email_et = findViewById(R.id.email_et);
        TextView send_code = findViewById(R.id.send_code);

        backIV.setOnClickListener(v -> finish());

        send_code.setOnClickListener(v -> {
            if (!CommonUtil.isValidEmail(email_et.getText().toString())) {
                Toast.makeText(this, "请输入合法的邮箱地址", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(LoginActivity.this, LoginCodeActivity.class);
                intent.putExtra("email", email_et.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}