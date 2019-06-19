package com.example.administrator.rolemanage.okhttp;


import com.example.administrator.rolemanage.model.base.BaseResponse;

/**
 * Created by lib on 2016/9/14.
 */
public interface ApiCallBack<T extends BaseResponse> {
    void onFailure(String msg);

    void onSuccess(T response);
}
