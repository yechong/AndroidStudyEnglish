package com.tulinghuo.studyenglish.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tulinghuo.studyenglish.fragment.component.WordSentenceFragment;

import java.util.ArrayList;
import java.util.List;

public class WordSentenceAdapter extends FragmentStateAdapter {

    private List<WordSentenceFragment> fragmentList = new ArrayList<>();

    public WordSentenceAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    // 添加Fragment的方法
    public void addFragment(WordSentenceFragment fragment) {
        fragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 与 getItem() 类似，但要求每次都返回一个新的 Fragment 实例
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
