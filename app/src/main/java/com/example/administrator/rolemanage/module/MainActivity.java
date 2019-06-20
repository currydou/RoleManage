package com.example.administrator.rolemanage.module;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.rolemanage.R;
import com.example.administrator.rolemanage.base.activitynew.BaseActivity;
import com.example.administrator.rolemanage.base.basecontractT.BasePresenterT;
import com.example.administrator.rolemanage.model.base.BaseResponse;
import com.example.administrator.rolemanage.okhttp.ApiManager;
import com.example.administrator.rolemanage.okhttp.V2.BaseCallback;
import com.example.administrator.rolemanage.okhttp.V2.RequestParams;
import com.example.administrator.rolemanage.utils.businessutil.IntentUtils;
import com.example.administrator.rolemanage.utils.organized.ToastUtils;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {



    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public BasePresenterT initPresenter() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void initListeners(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btnLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                logout();
                break;
        }
    }

    public void logout() {
        RequestParams requestParams = new RequestParams();
        ApiManager.logout(requestParams, new BaseCallback<BaseResponse<Object>>() {
            @Override
            protected void onSuccess(int code, String message, String responseJson, BaseResponse<Object> baseResponse) {
                IntentUtils.logOut(mContext);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

}
