package com.tulinghuo.studyenglish.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.fragment.HomeFragment;
import com.tulinghuo.studyenglish.fragment.MyFragment;
import com.tulinghuo.studyenglish.listener.OnBottomNavClickListener;

public class MainActivity extends AppCompatActivity implements OnBottomNavClickListener {

    private HomeFragment homeFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeFragment = HomeFragment.newInstance("");
        myFragment = MyFragment.newInstance("");

        if (savedInstanceState == null) {
            // 默认显示首页
            switchFragment(homeFragment);
        }
    }

    @Override
    public void onNavItemClick(int position) {
        switch (position) {
            case 0:
                switchFragment(homeFragment);
                break;
            case 1:
                switchFragment(myFragment);
                break;
        }
    }

    // 切换Fragment的方法
    private void switchFragment(Fragment targetFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, targetFragment)
                .commit();
    }
}