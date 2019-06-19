package com.example.administrator.rolemanage.base.basecontract;


/**
 * Created by lib on 2016/9/14.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);

    void finishView();

    void showProcess();

    void dismissProcess();

}
