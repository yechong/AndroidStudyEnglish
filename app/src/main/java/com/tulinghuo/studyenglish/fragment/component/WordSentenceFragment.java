package com.tulinghuo.studyenglish.fragment.component;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.util.CommonUtil;

public class WordSentenceFragment extends Fragment {

    private LinearLayout root;
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
        root = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, CommonUtil.dpToPx(getContext(), 10), 0, 0);
        root.setLayoutParams(layoutParams);
        root.setOrientation(LinearLayout.VERTICAL); // 默认是水平，根据你的需求设置

        TextView textView1 = new TextView(getContext());
        LinearLayout.LayoutParams textParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textView1.setLayoutParams(textParams1);
        textView1.setText(content);
        textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        TextView textView2 = new TextView(getContext());
        LinearLayout.LayoutParams textParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams2.setMargins(0, CommonUtil.dpToPx(getContext(), 5), 0, 0);
        textView2.setLayoutParams(textParams2);
        textView2.setText(translation);
        textView2.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        root.addView(textView1);
        root.addView(textView2);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (root != null) {
            root.requestLayout();   // 刷新界面高度
        }
    }
}