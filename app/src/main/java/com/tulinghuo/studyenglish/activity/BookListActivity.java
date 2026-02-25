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

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<BookCategory> bookCategoryList;

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

        // 准备数据
        prepareData();

        // 设置Adapter
        BookCategoryAdapter adapter = new BookCategoryAdapter(bookCategoryList);

        // 设置点击事件
        adapter.setOnItemClickListener(position -> {
            BookCategory bookCategory = bookCategoryList.get(position);
            Toast.makeText(BookListActivity.this, "点击了: " + bookCategory.getName(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }

    private void prepareData() {
        bookCategoryList = new ArrayList<>();
        bookCategoryList.add(new BookCategory("热门"));
        bookCategoryList.add(new BookCategory("留学"));
        bookCategoryList.add(new BookCategory("大学"));
        bookCategoryList.add(new BookCategory("高中"));
        bookCategoryList.add(new BookCategory("初中"));
        bookCategoryList.add(new BookCategory("小学"));
        bookCategoryList.add(new BookCategory("全部"));
    }
}