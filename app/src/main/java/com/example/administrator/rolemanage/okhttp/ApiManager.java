package com.example.administrator.rolemanage.okhttp;

import com.example.administrator.rolemanage.BuildConfig;
import com.example.administrator.rolemanage.constant.Constant;
import com.example.administrator.rolemanage.model.UserInfo;
import com.example.administrator.rolemanage.model.base.BaseResponse;
import com.example.administrator.rolemanage.okhttp.V2.BaseCallback;
import com.example.administrator.rolemanage.okhttp.V2.OkHttpRequest;
import com.example.administrator.rolemanage.okhttp.V2.RequestParams;
import com.google.gson.reflect.TypeToken;

/**
 */
public class ApiManager {

    private static final String TAG = "ApiManager";

    public static void login(RequestParams requestParams, BaseCallback callBack) {//
        String url = BuildConfig.BASE_URL + Constant.URL_LOGIN;
        OkHttpRequest.getInstance().postEnqueue(url, requestParams, callBack, new TypeToken<BaseResponse<UserInfo>>() {
        }.getType());
    }


}
