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
import com.tulinghuo.studyenglish.vo.WordVO;

public class WordPhraseFragment extends Fragment {

    private WordVO wordVO;

    public WordPhraseFragment() {
    }

    public static WordPhraseFragment newInstance(WordVO vo) {
        WordPhraseFragment fragment = new WordPhraseFragment();
        Bundle args = new Bundle();
        args.putSerializable("word", vo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wordVO = (WordVO) getArguments().getSerializable("word");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getContext());
        root.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        root.setOrientation(LinearLayout.VERTICAL);
        for (WordVO.PhraseVO vo : wordVO.getPhraseList()) {
            LinearLayout rootLinearLayout = new LinearLayout(getContext());
            rootLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rootLinearLayout.getLayoutParams();
            layoutParams.topMargin = CommonUtil.dpToPx(getContext(), 10);
            rootLinearLayout.setLayoutParams(layoutParams);
            rootLinearLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textViewEnglish = new TextView(getContext());
            textViewEnglish.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textViewEnglish.setText(vo.getContent());
            textViewEnglish.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            textViewEnglish.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

            TextView textViewChinese = new TextView(getContext());
            textViewChinese.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textViewChinese.setText(vo.getTranslation());
            textViewChinese.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
            textViewChinese.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            rootLinearLayout.addView(textViewEnglish);
            rootLinearLayout.addView(textViewChinese);
            root.addView(rootLinearLayout);
        }
        return root;
    }
}