package com.tulinghuo.studyenglish.fragment.component;

import android.graphics.Typeface;
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

public class WordTranslateFragment extends Fragment {

    private String pos;
    private String translation;

    public WordTranslateFragment() {
        // Required empty public constructor
    }

    public static WordTranslateFragment newInstance(String pos, String translation) {
        WordTranslateFragment fragment = new WordTranslateFragment();
        Bundle args = new Bundle();
        args.putString("pos", pos);
        args.putString("translation", translation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getString("pos");
            translation = getArguments().getString("translation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getContext());
        root.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
        marginParams.topMargin = CommonUtil.dpToPx(getContext(), 10);
        root.setLayoutParams(marginParams);
        root.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView1 = new TextView(getContext());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params1.rightMargin = CommonUtil.dpToPx(getContext(), 4);
        textView1.setLayoutParams(params1);
        textView1.setText(pos + ".");
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView1.setTypeface(textView1.getTypeface(), Typeface.BOLD);
        textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.gray3));

        TextView textView2 = new TextView(getContext());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView2.setLayoutParams(params2);
        textView2.setText(translation);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        root.addView(textView1);
        root.addView(textView2);

        return root;
    }
}