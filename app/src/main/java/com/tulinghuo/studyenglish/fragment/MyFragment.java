package com.tulinghuo.studyenglish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.activity.LoginActivity;
import com.tulinghuo.studyenglish.util.HttpUtil;
import com.tulinghuo.studyenglish.vo.HttpResponseVO;
import com.tulinghuo.studyenglish.vo.UserVO;

public class MyFragment extends Fragment {

    private LinearLayout login_block;
    private RelativeLayout user_detail;
    private TextView nickname;

    // 创建静态工厂方法，方便传递参数
    public static MyFragment newInstance(String message) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadUserInfo();
    }

    private void initViews() {
        login_block = getView().findViewById(R.id.login_block);
        user_detail = getView().findViewById(R.id.user_detail);
        nickname = getView().findViewById(R.id.nickname);

        user_detail.setVisibility(View.GONE);

        login_block.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    private void loadUserInfo() {
        // TODO 先从本地缓存中取，优先显示登录用户信息，同时请求后台取最新的用户信息，如果此时没有登录，才显示未登录状态

        HttpUtil.getAsync("/api_user.php?method=user_info", new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                HttpResponseVO responseVO = gson.fromJson(response, HttpResponseVO.class);
                if (responseVO.isRequestSuccess()) {
                    JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                    UserVO userVO = gson.fromJson(jsonObject.getAsJsonObject("data"), UserVO.class);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            login_block.setVisibility(View.GONE);
                            user_detail.setVisibility(View.VISIBLE);
                            nickname.setText(userVO.getNickname());
                            // TODO 保存到本地缓存中
                        });
                    }
                }
                else {
                    login_block.setVisibility(View.VISIBLE);
                    user_detail.setVisibility(View.GONE);
                    // TODO 从本地缓存中移除
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}