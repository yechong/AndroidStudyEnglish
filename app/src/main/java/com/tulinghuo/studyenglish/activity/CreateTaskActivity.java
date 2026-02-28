package com.tulinghuo.studyenglish.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.model.Book;
import com.tulinghuo.studyenglish.util.ConstantUtil;
import com.tulinghuo.studyenglish.util.DateUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CreateTaskActivity extends AppCompatActivity {

    private Book book;
    private TextView endDateTV;
    private TextView minuteTV;
    private List<Integer> perDayWordsList;
    private List<Integer> finishDaysList;
    private Spinner perDayWordsSpinner;
    private Spinner finishDaysSpinner;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            book = (Book) bundle.getSerializable("book");
        }
        initViews();
    }

    private void initViews() {
        ImageView backIV = findViewById(R.id.back_iv);
        ImageView coverIV = findViewById(R.id.cover_iv);
        TextView titleTV = findViewById(R.id.title_tv);
        TextView wordsTV = findViewById(R.id.words_tv);
        endDateTV = findViewById(R.id.end_date_tv);
        minuteTV = findViewById(R.id.minute_tv);
        TextView createTaskTV = findViewById(R.id.create_task_tv);

        backIV.setOnClickListener(v -> finish());

        createTaskTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CreateTaskActivity.this)
                        .setTitle("提示")
                        .setMessage("确认创建该计划吗？")
                        .setPositiveButton("确定", (dialog, which) -> {

                            dialog.dismiss();
                            finish();
                        })
                        .setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });

        int words = 0;
        if (book != null) {
            words = book.getWords();
            String imageUrl = book.getCover();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)  // 使用 itemView 的 Context
                        .load(imageUrl)                // 加载图片 URL
                        .placeholder(R.drawable.book) // 占位图（你需要准备这个资源）
                        .error(R.drawable.book)             // 错误图（你需要准备这个资源）
                        .centerCrop()                   // 图片裁剪方式，可根据需求调整
                        .into(coverIV);                  // 设置到 ImageView
            }
            else {
                // 如果没有图片 URL，可以设置一个默认图
                coverIV.setImageResource(R.drawable.book);
            }
            titleTV.setText(book.getName());
            wordsTV.setText("共 " + words + " 词");
        }

        int defaultPerDayWords = 20;
        int defaultFinishDays = ConstantUtil.calculateDays(words, defaultPerDayWords);

        initPerDayWordsSpinnerView(words, defaultPerDayWords);
        initFinishDaysSpinnerView(words, defaultFinishDays);

        calculateDateAndMinutes(words, defaultPerDayWords);
    }

    private void calculateDateAndMinutes(int words, int perDayWords) {
        perDayWords = Math.min(words, perDayWords);
        int days = (words + perDayWords - 1) / perDayWords;
        String dateStr = DateUtil.formatDateByPattern(DateUtil.addDays(new Date(), days), "yyyy年MM月dd");
        endDateTV.setText(dateStr);

        int minutes = perDayWords * 2;
        minuteTV.setText("预计每天" + minutes + "分钟");
    }

    private void initPerDayWordsSpinnerView(int words, int defaultPerDayWords) {
        int defaultPosition = 0;
        perDayWordsList = new LinkedList<>();
        List<String> perDayWordsContentList = new LinkedList<>();
        for (int i = 5, index = 0; i <= 100; i += 5, index++) {
            perDayWordsList.add(i);
            perDayWordsContentList.add(i + "个");
            if (i == defaultPerDayWords) defaultPosition = index;
        }
        ArrayAdapter<String> perDayWordsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perDayWordsContentList);
        perDayWordsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        perDayWordsSpinner = findViewById(R.id.per_day_words);
        perDayWordsSpinner.setAdapter(perDayWordsAdapter);
        perDayWordsSpinner.setSelection(defaultPosition);
        perDayWordsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int perDayWords = perDayWordsList.get(position);
                int finishDays = ConstantUtil.calculateDays(words, perDayWords);
                int finishDaysPosition = 0;
                for (int i = 0; i < finishDaysList.size(); i++) {
                    if (finishDaysList.get(i) == finishDays) {
                        finishDaysPosition = i;
                        break;
                    }
                }
                finishDaysSpinner.setSelection(finishDaysPosition);
                calculateDateAndMinutes(words, perDayWords);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initFinishDaysSpinnerView(int words, int defaultFinishDays) {
        int defaultPosition = 0;
        finishDaysList = new LinkedList<>();
        List<String> finishDaysContentList = new LinkedList<>();
        for (int i = 5, index = 0; i <= 360; i += 5, index++) {
            finishDaysList.add(i);
            finishDaysContentList.add(i + "天");
            if (i == defaultFinishDays) defaultPosition = index;
        }
        ArrayAdapter<String> finishDaysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, finishDaysContentList);
        finishDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        finishDaysSpinner = findViewById(R.id.finish_days);
        finishDaysSpinner.setAdapter(finishDaysAdapter);
        finishDaysSpinner.setSelection(defaultPosition);
        finishDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int finishDays = finishDaysList.get(position);
                int perDayWords = ConstantUtil.calculatePerDayWords(words, finishDays);
                int perDayWordsPosition = 0;
                for (int i = 0; i < perDayWordsList.size(); i++) {
                    if (perDayWordsList.get(i) == perDayWords) {
                        perDayWordsPosition = i;
                        break;
                    }
                }
                perDayWordsSpinner.setSelection(perDayWordsPosition);
                calculateDateAndMinutes(words, perDayWords);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}