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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.model.Book;
import com.tulinghuo.studyenglish.util.HttpUtil;

import java.util.LinkedList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> bookList = new LinkedList<>();

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private OnItemClickListener listener;

    public BookAdapter() {
        bookList.add(new Book());
    }

    public void setBookList(List<Book> bookList) {
        this.bookList.clear();
        this.bookList.addAll(bookList);
        mainHandler.post(() -> notifyDataSetChanged());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // 接口定义点击事件
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView coverIV;
        private TextView titleTV;
        private TextView wordsTV;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            coverIV = itemView.findViewById(R.id.cover_iv);
            titleTV = itemView.findViewById(R.id.title_tv);
            wordsTV = itemView.findViewById(R.id.words_tv);
        }

        public void bind(final Book item) {
            titleTV.setText(item.getName());
            wordsTV.setText("共 " + item.getWords() + " 词");
        }
    }

    public void loadBookList() {
        Log.i("BookAdapter", "loadBookList");
        HttpUtil.getAsync("/api_book.php?method=listBook", new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("BookAdapter", response);
                JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                Gson gson = new Gson();
                List<Book> list = new LinkedList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject item = jsonArray.get(i).getAsJsonObject();
                    list.add(gson.fromJson(item, Book.class));
                }
                setBookList(list);
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("BookAdapter", "error -> " + e.getMessage());
            }
        });
    }
}
