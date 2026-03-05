package com.tulinghuo.studyenglish.fragment.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tulinghuo.studyenglish.R;

public class WordSentenceFragment extends Fragment {

    private View rootView;
    private String content;
    private String translation;

    public WordSentenceFragment() {
    }

    public static WordSentenceFragment newInstance(String content, String translation) {
        WordSentenceFragment fragment = new WordSentenceFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        args.putString("translation", translation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString("content");
            translation = getArguments().getString("translation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_word_sentence, container, false);
        TextView content_tv = rootView.findViewById(R.id.content_tv);
        TextView translation_tv = rootView.findViewById(R.id.translation_tv);

        content_tv.setText(content);
        translation_tv.setText(translation);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            rootView.requestLayout();   // 刷新界面高度
        }
    }
}