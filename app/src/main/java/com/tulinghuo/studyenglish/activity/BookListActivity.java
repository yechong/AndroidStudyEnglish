package com.tulinghuo.studyenglish.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.adapter.BookAdapter;
import com.tulinghuo.studyenglish.adapter.BookCategoryAdapter;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView bookCategoryListRecyclerView;
    private RecyclerView bookListRecyclerView;
    private BookCategoryAdapter bookCategoryAdapter;
    private BookAdapter bookAdapter;

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

        initBookCategoryListRecyclerView();
        initBookListRecyclerView();
    }

    private void initBookCategoryListRecyclerView() {
        // 初始化RecyclerView
        bookCategoryListRecyclerView = findViewById(R.id.book_category_list_recyclerView);

        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bookCategoryListRecyclerView.setLayoutManager(layoutManager);

        // 可选：优化性能
        bookCategoryListRecyclerView.setHasFixedSize(true);

        // 设置Adapter
        bookCategoryAdapter = new BookCategoryAdapter();

        // 设置点击事件
        bookCategoryAdapter.setOnItemClickListener(bookCategory -> {
            Toast.makeText(BookListActivity.this, "点击了: " + bookCategory.getName(), Toast.LENGTH_SHORT).show();
        });

        bookCategoryListRecyclerView.setAdapter(bookCategoryAdapter);
    }

    private void initBookListRecyclerView() {
        // 初始化RecyclerView
        bookListRecyclerView = findViewById(R.id.book_list_recyclerView);

        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookListRecyclerView.setLayoutManager(layoutManager);

        // 可选：优化性能
        bookListRecyclerView.setHasFixedSize(true);

        // 设置Adapter
        bookAdapter = new BookAdapter();

        // 设置点击事件
        bookAdapter.setOnItemClickListener(book -> {
            Toast.makeText(BookListActivity.this, "点击了: " + book.getName(), Toast.LENGTH_SHORT).show();
        });

        bookListRecyclerView.setAdapter(bookAdapter);
    }

    private void prepareData() {
        bookCategoryAdapter.loadCategoryList();
        bookAdapter.loadBookList();
    }
}