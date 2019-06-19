package com.example.administrator.rolemanage.module;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.administrator.rolemanage.R;
import com.example.administrator.rolemanage.base.activitynew.BaseActivity;
import com.example.administrator.rolemanage.base.basecontractT.BasePresenterT;
import com.example.administrator.rolemanage.module.login.LoginActivity;
import com.example.administrator.rolemanage.utils.organized.PerfUtil;

/**
 * Created by lib on 2017/3/11.
 */

public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();


    @Override
    public int initContentView() {
        return R.layout.act_splash;
    }

    @Override
    public BasePresenterT initPresenter() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        handler.postDelayed(this::goMainActivity, 1000);
    }

    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void initListeners(Bundle savedInstanceState) {

    }

    private void goMainActivity() {
        if (PerfUtil.isLogin()) {
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            startActivity(new Intent(mContext, MainActivity.class));
        }
        finish();
    }


}
