package com.example.administrator.rolemanage.module.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rolemanage.R;
import com.example.administrator.rolemanage.base.activitynew.BaseActivity;
import com.example.administrator.rolemanage.base.basecontractT.BasePresenterT;
import com.example.administrator.rolemanage.model.UserInfo;
import com.example.administrator.rolemanage.model.base.BaseResponse;
import com.example.administrator.rolemanage.okhttp.ApiManager;
import com.example.administrator.rolemanage.okhttp.V2.BaseCallback;
import com.example.administrator.rolemanage.okhttp.V2.RequestParams;
import com.example.administrator.rolemanage.utils.businessutil.IntentUtils;
import com.example.administrator.rolemanage.utils.organized.PerfUtil;
import com.example.administrator.rolemanage.utils.organized.ToastUtils;
import com.example.administrator.rolemanage.view.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etMobile) ClearEditText etMobile;
    @BindView(R.id.etPws) ClearEditText etPws;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.logoBig) View logoBig;


    @Override
    public int initContentView() {
        return R.layout.activity_login;
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

    @OnClick({R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
        }
    }

    public void login() {
        String tel = etMobile.getText().toString().trim();
        String pwd = etPws.getText().toString().trim();
        if (TextUtils.isEmpty(tel) || TextUtils.isEmpty(pwd)) {
            ToastUtils.showToast("参数为空");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.addParams("tel", tel);
        requestParams.addParams("passWord", pwd);
        ApiManager.login(requestParams, new BaseCallback<BaseResponse<UserInfo>>() {
            @Override
            protected void onSuccess(int code, String message, String responseJson, BaseResponse<UserInfo> baseResponse) {
                ToastUtils.showToast(message);
                PerfUtil.setUserInfo(baseResponse.getData());
                IntentUtils.entryMain(mContext);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

}
