package com.tulinghuo.studyenglish.adapter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.event.BookCategoryChangeEvent;
import com.tulinghuo.studyenglish.event.StudyWordEvent;
import com.tulinghuo.studyenglish.event.TaskListEvent;
import com.tulinghuo.studyenglish.model.Task;
import com.tulinghuo.studyenglish.util.HttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> taskList = new ArrayList<>();

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public void setTaskList(List<Task> list) {
        this.taskList.clear();
        this.taskList.addAll(list);
        EventBus.getDefault().postSticky(new TaskListEvent(this.taskList.size()));
        mainHandler.post(() -> notifyDataSetChanged());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        private ImageView cover_iv;
        private TextView book_name_tv;
        private TextView words_info;
        private TextView remain_days;
        private TextView today_new_words;
        private TextView today_total_new_words;
        private TextView today_review_words;
        private TextView today_total_review_words;

        private TextView study;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            cover_iv = itemView.findViewById(R.id.cover_iv);
            book_name_tv = itemView.findViewById(R.id.book_name_tv);
            words_info = itemView.findViewById(R.id.words_info);
            remain_days = itemView.findViewById(R.id.remain_days);
            today_new_words = itemView.findViewById(R.id.today_new_words);
            today_total_new_words = itemView.findViewById(R.id.today_total_new_words);
            today_review_words = itemView.findViewById(R.id.today_review_words);
            today_total_review_words = itemView.findViewById(R.id.today_total_review_words);
            study = itemView.findViewById(R.id.study);
        }

        public void bind(final Task task) {
            Gson gson = new Gson();
            Log.i("TaskListAdapter", gson.toJson(task));
            book_name_tv.setText(task.getBook().getName());
            words_info.setText(task.getTotalStudyWords() + " / " + task.getBook().getWords());
            remain_days.setText("剩余 " + task.getRemainDays() + " 天");
            today_new_words.setText(String.valueOf(task.getTodayNewWords()));
            today_total_new_words.setText(String.valueOf(task.getTodayTotalNewWords()));
            today_review_words.setText(String.valueOf(task.getTodayReviewWords()));
            today_total_review_words.setText(String.valueOf(task.getTodayTotalReviewWords()));

            String imageUrl = task.getBook().getCover();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(itemView.getContext())  // 使用 itemView 的 Context
                        .load(imageUrl)                // 加载图片 URL
                        .placeholder(R.drawable.book) // 占位图（你需要准备这个资源）
                        .error(R.drawable.book)             // 错误图（你需要准备这个资源）
                        .centerCrop()                   // 图片裁剪方式，可根据需求调整
                        .into(cover_iv);                  // 设置到 ImageView
            }
            else {
                // 如果没有图片 URL，可以设置一个默认图
                cover_iv.setImageResource(R.drawable.book);
            }

            study.setOnClickListener(v -> EventBus.getDefault().post(new StudyWordEvent(task.getId())));
        }
    }

    public void loadTaskList() {
        Log.i("TaskListAdapter", "loadTaskList");
        HttpUtil.getAsync("/api_task.php?method=listTask", new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("TaskListAdapter", response);
                JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                Gson gson = new Gson();
                List<Task> list = new LinkedList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject item = jsonArray.get(i).getAsJsonObject();
                    list.add(gson.fromJson(item, Task.class));
                }
                setTaskList(list);
                if (!list.isEmpty()) {
                    EventBus.getDefault().post(new BookCategoryChangeEvent(list.get(0).getId()));
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("TaskListAdapter", "error -> " + e.getMessage());
            }
        });
    }
}
