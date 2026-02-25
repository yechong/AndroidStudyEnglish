package com.tulinghuo.studyenglish.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.model.BookCategory;

import java.util.List;

public class BookCategoryAdapter extends RecyclerView.Adapter<BookCategoryAdapter.ViewHolder> {

    private List<BookCategory> bookCategoryList;
    private OnItemClickListener listener;

    public BookCategoryAdapter(List<BookCategory> bookCategoryList) {
        this.bookCategoryList = bookCategoryList;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 接口定义点击事件
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookCategory item = bookCategoryList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return bookCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTV = itemView.findViewById(R.id.category_name_tv);
        }

        public void bind(final BookCategory item, final OnItemClickListener listener) {
            categoryNameTV.setText(item.getName());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
