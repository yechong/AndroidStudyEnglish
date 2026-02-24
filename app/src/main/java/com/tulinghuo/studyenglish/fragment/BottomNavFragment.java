package com.tulinghuo.studyenglish.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.listener.OnBottomNavClickListener;

public class BottomNavFragment extends Fragment {

    private OnBottomNavClickListener listener;

    private ImageView homeIV;
    private TextView homeTV;
    private ImageView myIV;
    private TextView myTV;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 确保Activity实现了接口
        if (context instanceof OnBottomNavClickListener) {
            listener = (OnBottomNavClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnBottomNavClickListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout layoutHome = view.findViewById(R.id.layout_home);
        RelativeLayout layoutMy = view.findViewById(R.id.layout_my);

        homeIV = view.findViewById(R.id.home_iv);
        homeTV = view.findViewById(R.id.home_tv);
        myIV = view.findViewById(R.id.my_iv);
        myTV = view.findViewById(R.id.my_tv);

        updateSelectedState(0);

        layoutHome.setOnClickListener(v -> {
            updateSelectedState(0);
            if (listener != null) {
                listener.onNavItemClick(0);
            }
        });
        layoutMy.setOnClickListener(v -> {
            updateSelectedState(1);
            if (listener != null) {
                listener.onNavItemClick(1);
            }
        });
    }

    // 更新选中状态
    private void updateSelectedState(int position) {
        // 重置所有按钮状态
        homeIV.setSelected(false);
        homeTV.setSelected(false);
        myIV.setSelected(false);
        myTV.setSelected(false);

        // 设置当前选中状态
        switch (position) {
            case 0:
                homeIV.setSelected(true);
                homeTV.setSelected(true);
                break;
            case 1:
                myIV.setSelected(true);
                myTV.setSelected(true);
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}