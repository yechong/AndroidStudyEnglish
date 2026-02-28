package com.tulinghuo.studyenglish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.activity.BookListActivity;
import com.tulinghuo.studyenglish.activity.SearchActivity;
import com.tulinghuo.studyenglish.adapter.TaskListAdapter;

public class HomeFragment extends Fragment {

    private RecyclerView taskListRecycleView;
    private TaskListAdapter taskListAdapter;

    // 创建静态工厂方法，方便传递参数
    public static HomeFragment newInstance(String message) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        prepareData();
    }

    private void initViews() {
        RelativeLayout searchBox = getView().findViewById(R.id.search_box);
        TextView addTaskTV = getView().findViewById(R.id.add_task_tv);
        addTaskTV.setVisibility(View.GONE);

        searchBox.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        addTaskTV.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BookListActivity.class);
            startActivity(intent);
        });

        initTaskListRecycleView();
    }

    private void initTaskListRecycleView() {
        // 初始化RecyclerView
        taskListRecycleView = getView().findViewById(R.id.task_list_recyclerView);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskListRecycleView.setLayoutManager(layoutManager);
        // 可选：优化性能
        taskListRecycleView.setHasFixedSize(true);
        // 设置Adapter
        taskListAdapter = new TaskListAdapter();
        taskListRecycleView.setAdapter(taskListAdapter);
    }

    private void prepareData() {
        taskListAdapter.loadTaskList();
    }
}