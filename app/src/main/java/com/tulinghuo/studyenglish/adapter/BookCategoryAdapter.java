package com.tulinghuo.studyenglish.adapter;

import android.util.Log;
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
    private int selectedPosition = 0;  // 记录选中的位置

    public BookCategoryAdapter(List<BookCategory> bookCategoryList) {
        this.bookCategoryList = bookCategoryList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
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
                listener.onItemClick(selectedPosition);
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
            underLineV.setSelected(isSelected);
        }
    }
}
