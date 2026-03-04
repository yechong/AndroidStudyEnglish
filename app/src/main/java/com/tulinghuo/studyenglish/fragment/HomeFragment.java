package com.tulinghuo.studyenglish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.tulinghuo.studyenglish.activity.LoginActivity;
import com.tulinghuo.studyenglish.activity.SearchActivity;
import com.tulinghuo.studyenglish.adapter.TaskListAdapter;
import com.tulinghuo.studyenglish.event.TaskListEvent;
import com.tulinghuo.studyenglish.util.CommonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeFragment extends Fragment {

    private TextView addTaskTV;
    private TextView add_task_float_tv;
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        prepareData();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onTaskListEvent(TaskListEvent event) {
        Log.i("HomeFragment", "onTaskListEvent");
        if (event.existData()) {
            add_task_float_tv.setVisibility(View.VISIBLE);
            addTaskTV.setVisibility(View.GONE);
        }
        else {
            add_task_float_tv.setVisibility(View.GONE);
            addTaskTV.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        RelativeLayout searchBox = getView().findViewById(R.id.search_box);
        addTaskTV = getView().findViewById(R.id.add_task_tv);
        add_task_float_tv = getView().findViewById(R.id.add_task_float_tv);

        addTaskTV.setVisibility(View.GONE);
        add_task_float_tv.setVisibility(View.GONE);

        searchBox.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        addTaskTV.setOnClickListener(v -> {
            if (CommonUtil.isLogin(getContext())) {
                Intent intent = new Intent(getActivity(), BookListActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        add_task_float_tv.setOnClickListener(v -> {
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