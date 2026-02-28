package com.tulinghuo.studyenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.tulinghuo.studyenglish.event.BookCategoryChangeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(BookCategoryChangeEvent event) {
        int categoryId = event.getCategoryId();
        Log.i("BookAdapter", "收到 BookCategoryChangeEvent");
        bookAdapter.loadBookList(categoryId);
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
            EventBus.getDefault().post(new BookCategoryChangeEvent(bookCategory.getId()));
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
            if (book.getIsUsed() == 1) {
                Toast.makeText(BookListActivity.this, "该词书已添加", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(BookListActivity.this, CreateTaskActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

        bookListRecyclerView.setAdapter(bookAdapter);
    }

    private void prepareData() {
        bookCategoryAdapter.loadCategoryList();
    }
}