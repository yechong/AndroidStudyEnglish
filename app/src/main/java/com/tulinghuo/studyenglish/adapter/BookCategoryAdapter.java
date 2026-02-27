package com.tulinghuo.studyenglish.adapter;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.event.BookCategoryChangeEvent;
import com.tulinghuo.studyenglish.model.BookCategory;
import com.tulinghuo.studyenglish.util.HttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

public class BookCategoryAdapter extends RecyclerView.Adapter<BookCategoryAdapter.ViewHolder> {

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private List<BookCategory> bookCategoryList = new LinkedList<>();
    private OnItemClickListener listener;
    private int selectedPosition = 0;  // 记录选中的位置

    public void setBookCategoryList(List<BookCategory> bookCategoryList) {
        this.bookCategoryList.clear();
        this.bookCategoryList.addAll(bookCategoryList);
        mainHandler.post(() -> notifyDataSetChanged());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 接口定义点击事件
    public interface OnItemClickListener {
        void onItemClick(BookCategory bookCategory);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookCategory item = bookCategoryList.get(position);
        boolean isSelected = (position == selectedPosition);
        holder.bind(item, isSelected);

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            // 只更新改变的两个item
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedPosition);

            if (listener != null) {
                listener.onItemClick(bookCategoryList.get(selectedPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryNameTV;
        public View underLineV;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            categoryNameTV = itemView.findViewById(R.id.category_name_tv);
            underLineV = itemView.findViewById(R.id.underLine_v);
        }

        public void bind(final BookCategory item, boolean isSelected) {
            categoryNameTV.setText(item.getName());
            categoryNameTV.setTypeface(Typeface.DEFAULT);
            categoryNameTV.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.un_active));
            if (isSelected) {
                categoryNameTV.setTypeface(Typeface.DEFAULT_BOLD);
                categoryNameTV.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black2));
            }
            underLineV.setSelected(isSelected);
        }
    }

    public void loadCategoryList() {
        Log.i("BookCategoryAdapter", "loadCategoryList");
        HttpUtil.getAsync("/api_book.php?method=listCategory", new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("BookCategoryAdapter", response);
                JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                Gson gson = new Gson();
                List<BookCategory> list = new LinkedList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject item = jsonArray.get(i).getAsJsonObject();
                    list.add(gson.fromJson(item, BookCategory.class));
                }
                setBookCategoryList(list);
                if (!list.isEmpty()) {
                    EventBus.getDefault().post(new BookCategoryChangeEvent(list.get(0).getId()));
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("BookCategoryAdapter", "error -> " + e.getMessage());
            }
        });
    }
}
