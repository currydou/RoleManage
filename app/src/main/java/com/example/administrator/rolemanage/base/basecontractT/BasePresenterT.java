package com.example.administrator.rolemanage.base.basecontractT;


/**
 * Created by next on 2018/4/12.
 */

public abstract class BasePresenterT<V extends IView, M extends IDataSource> implements IPresenter{

    protected V mView;
    protected M mRepository;


    public BasePresenterT() {
    }

    public BasePresenterT(V view) {
        this.mView = view;
    }

    public BasePresenterT(V view, M model) {
        this.mRepository = model;
        this.mView = view;
    }

    public void start() {

    }


    public void attach(V mView) {
        this.mView = mView;
    }

    public void detach() {
        mView = null;
        if (mRepository != null) {
            mRepository.onDestroy();
            mRepository = null;
        }
    }

    /**
     * 获取V层
     *
     * @return
     */
    public V getView() {
        return mView;
    }
}