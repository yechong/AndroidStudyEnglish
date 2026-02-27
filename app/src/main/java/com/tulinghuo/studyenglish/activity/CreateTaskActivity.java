package com.tulinghuo.studyenglish.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tulinghuo.studyenglish.R;

import java.util.LinkedList;
import java.util.List;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
    }

    private void initViews() {
        ImageView backIV = findViewById(R.id.back_iv);
        backIV.setOnClickListener(v -> finish());

        List<String> perDayWords = new LinkedList<>();
        for (int i = 5; i < 100; i += 5) {
            perDayWords.add(i + "个");
        }
        ArrayAdapter<String> perDayWordsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perDayWords);
        perDayWordsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner perDayWordsSpinner = findViewById(R.id.per_day_words);
        perDayWordsSpinner.setAdapter(perDayWordsAdapter);
        perDayWordsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // parent.getAdapter().getItem(position) 获取选中项
                String selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> finishDays = new LinkedList<>();
        for (int i = 5; i < 100; i += 5) {
            finishDays.add(i + "天");
        }
        ArrayAdapter<String> finishDaysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, finishDays);
        finishDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner finishDaysSpinner = findViewById(R.id.finish_days);
        finishDaysSpinner.setAdapter(finishDaysAdapter);
        finishDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // parent.getAdapter().getItem(position) 获取选中项
                String selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}