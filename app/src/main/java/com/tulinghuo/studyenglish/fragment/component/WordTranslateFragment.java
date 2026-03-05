package com.tulinghuo.studyenglish.fragment.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tulinghuo.studyenglish.R;

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
        View view = inflater.inflate(R.layout.fragment_word_translate, container, false);

        TextView pos_tv = view.findViewById(R.id.pos_tv);
        TextView translate_tv = view.findViewById(R.id.translate_tv);

        pos_tv.setText(pos + ".");
        translate_tv.setText(translation);

        return view;
    }
}