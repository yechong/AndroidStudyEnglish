package com.tulinghuo.studyenglish.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.adapter.BookCategoryAdapter;
import com.tulinghuo.studyenglish.model.BookCategory;
import com.tulinghuo.studyenglish.util.HttpUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookCategoryAdapter bookCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        prepareData();
    }

    private void initViews() {
        ImageView backIV = findViewById(R.id.back_iv);
        backIV.setOnClickListener(v -> finish());

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        // 可选：优化性能
        recyclerView.setHasFixedSize(true);

        // 设置Adapter
        bookCategoryAdapter = new BookCategoryAdapter();

        // 设置点击事件
        bookCategoryAdapter.setOnItemClickListener(bookCategory -> {
            Toast.makeText(BookListActivity.this, "点击了: " + bookCategory.getName(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(bookCategoryAdapter);
    }

    private void prepareData() {
//        List<BookCategory> list = new LinkedList<>();
//        list.add(new BookCategory("热门"));
//        list.add(new BookCategory("留学"));
//        list.add(new BookCategory("大学"));
//        list.add(new BookCategory("高中"));
//        list.add(new BookCategory("初中"));
//        list.add(new BookCategory("小学"));
//        list.add(new BookCategory("全部"));
//        bookCategoryAdapter.setBookCategoryList(list);
//        HttpUtil.getAsync();
        bookCategoryAdapter.loadCategoryList();
    }
}