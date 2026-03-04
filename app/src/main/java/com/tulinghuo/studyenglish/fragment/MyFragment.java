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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tulinghuo.studyenglish.MyApp;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.activity.LoginActivity;
import com.tulinghuo.studyenglish.util.CommonUtil;
import com.tulinghuo.studyenglish.util.HttpUtil;
import com.tulinghuo.studyenglish.util.PreferencesManager;
import com.tulinghuo.studyenglish.vo.HttpResponseVO;
import com.tulinghuo.studyenglish.vo.UserVO;

public class MyFragment extends Fragment {

    private LinearLayout login_block;
    private RelativeLayout user_detail;
    private RelativeLayout logout;
    private TextView nickname;

    private AlertDialog dialog;

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
        logout = getView().findViewById(R.id.logout);
        user_detail = getView().findViewById(R.id.user_detail);
        nickname = getView().findViewById(R.id.nickname);

        user_detail.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);

        login_block.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyFragment.this.getContext());
            builder.setTitle("提示")
                    .setMessage("确认退出账号登录吗？")
                    .setPositiveButton("确定", (dialog, which) -> doLogout())
                    .setNegativeButton("取消", null);  // 如果不需要处理取消逻辑，可以设为null

            dialog = builder.create();
            dialog.setCancelable(false);  // 设置点击对话框外部或返回键是否可以取消
            dialog.setCanceledOnTouchOutside(false);  // 设置点击对话框外部是否取消
            dialog.show();
        });
    }

    private void loadUserInfo() {
        Gson gson = new Gson();
        // 先从本地缓存中取，优先显示登录用户信息，同时请求后台取最新的用户信息，如果此时没有登录，才显示未登录状态
        PreferencesManager preferencesManager = PreferencesManager.getInstance(getContext());
        String json = preferencesManager.getString("userVO", "");
        if (!CommonUtil.isBlank(json)) {
            UserVO userVO = gson.fromJson(json, UserVO.class);
            updateUserView(true, userVO);
        }

        HttpUtil.getAsync("/api_user.php?method=user_info", new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                HttpResponseVO responseVO = gson.fromJson(response, HttpResponseVO.class);
                if (responseVO.isRequestSuccess()) {
                    JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                    UserVO userVO = gson.fromJson(jsonObject.getAsJsonObject("data"), UserVO.class);
                    updateUserView(true, userVO);
                }
                else {
                    updateUserView(false, null);
                    MyApp.getTokenManager().clearToken();
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    private void updateUserView(boolean existUser, UserVO userVO) {
        if (getActivity() == null) return;

        PreferencesManager preferencesManager = PreferencesManager.getInstance(getContext());
        getActivity().runOnUiThread(() -> {
            if (existUser) {
                login_block.setVisibility(View.GONE);
                user_detail.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);
                nickname.setText(userVO.getNickname());

                // 保存到本地缓存中
                Gson gson = new Gson();
                preferencesManager.putString("userVO", gson.toJson(userVO));
            }
            else {
                login_block.setVisibility(View.VISIBLE);
                user_detail.setVisibility(View.GONE);
                logout.setVisibility(View.GONE);
                // 从本地缓存中移除
                preferencesManager.remove("userVO");
            }
        });

    }

    private void doLogout() {
        HttpUtil.postFormDataAsync("/api_user.php?method=logout", null, new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                updateUserView(false, null);
                MyApp.getTokenManager().clearToken();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }
}