package com.tulinghuo.studyenglish.fragment.component;

import android.os.Bundle;
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


public class WordSynoFragment extends Fragment {

    private WordVO wordVO;

    public WordSynoFragment() {
        // Required empty public constructor
    }

    public static WordSynoFragment newInstance(WordVO vo) {
        WordSynoFragment fragment = new WordSynoFragment();
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

        for (WordVO.SynoVO vo : wordVO.getSynoList()) {
            LinearLayout rootLinearLayout = new LinearLayout(getContext());
            rootLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rootLinearLayout.getLayoutParams();
            layoutParams.topMargin = CommonUtil.dpToPx(getContext(), 10);
            rootLinearLayout.setLayoutParams(layoutParams);
            rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView textViewVt = new TextView(getContext());
            textViewVt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textViewVt.setText(vo.getPos() + ".");
            textViewVt.setTextSize(15); // sp单位
            textViewVt.setTextColor(ContextCompat.getColor(getContext(), R.color.gray3));

            LinearLayout innerLinearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams innerLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            innerLayoutParams.setMarginStart(CommonUtil.dpToPx(getContext(), 5));
            innerLinearLayout.setLayoutParams(innerLayoutParams);
            innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textViewTop = new TextView(getContext());
            textViewTop.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            ((LinearLayout.LayoutParams) textViewTop.getLayoutParams()).bottomMargin = CommonUtil.dpToPx(getContext(), 2);
            textViewTop.setText(vo.getTranslation());
            textViewTop.setTextSize(15); // sp单位
            textViewTop.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            innerLinearLayout.addView(textViewTop);

            for (String word : vo.getWords()) {
                TextView textViewBottom = new TextView(getContext());
                textViewBottom.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                ((LinearLayout.LayoutParams) textViewBottom.getLayoutParams()).topMargin = CommonUtil.dpToPx(getContext(), 2);
                textViewBottom.setText(word);
                textViewBottom.setTextSize(15); // sp单位
                textViewBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                innerLinearLayout.addView(textViewBottom);
            }
            rootLinearLayout.addView(textViewVt);
            rootLinearLayout.addView(innerLinearLayout);
            root.addView(rootLinearLayout);
        }
        return root;
    }
}