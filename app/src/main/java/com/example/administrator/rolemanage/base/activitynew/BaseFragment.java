package com.example.administrator.rolemanage.base.activitynew;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.rolemanage.base.basecontractT.BasePresenterT;
import com.example.administrator.rolemanage.base.basecontractT.IView;
import com.example.administrator.rolemanage.utils.organized.KeyboardUtil;
import com.example.administrator.rolemanage.utils.organized.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 */
public abstract class BaseFragment<V extends IView, P extends BasePresenterT> extends Fragment {
    public Handler mHandler = new Handler();
    protected View contentView;

    protected P mPresenter;

    protected Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
        if (contentView == null) {
            contentView = inflater.inflate(initContentView(), container, false);
            unbinder = ButterKnife.bind(this, contentView);
            initViews(savedInstanceState);
            initDatas(savedInstanceState);
            initListeners(savedInstanceState);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) {
            parent.removeView(contentView);
        }
        refreshUIWhenViewRecreate();
        mUseViewPager = useViewPager();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            if (!mUseViewPager) {//用ViewPager的时候不销毁
                mPresenter.detach();
                mPresenter = null;
            }
        }
        super.onDestroyView();
        //  2018/7/11  这里解绑的话，有的fragment里异步回调方法，回来后如果使用已经解绑的fragment里的控件，就会空指针。
        //可以正常解绑，需要注意非空判断，或其他一些处理；
//        unbinder.unbind();
    }

    private boolean mUseViewPager;

    protected boolean useViewPager() {
        return false;
    }

    /**
     * 返回布局id
     *
     */
    public abstract int initContentView();

    public P initPresenter() {
        return null;
    }

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected abstract void initDatas(@Nullable Bundle savedInstanceState);

    public abstract void initListeners(@Nullable Bundle savedInstanceState);

    /**
     * 第一次加载、（首页切换底部的时候），会自动调用刷新。其他需要刷新的情况，手动调用处理
     */
    public void refreshUIWhenViewRecreate() {

    }

    /*---------------------------------------------------------------------*/


    public void showProcess() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.showProcessDialog(getActivity(), R.string.zhengzaichuliqingshaodeng);
                ToastUtils.showProcessBar(getActivity());
            }
        });
    }

    public void dismissProcess() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.dismissProcessDialog();
                ToastUtils.dismissProcessBar();
            }
        });
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void hideKeyboard(long delayMillis) {
        //延迟弹出软键盘
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.hideKeyboard(getActivity());
            }
        }, delayMillis);
    }

    public void hideKeyboard() {
        hideKeyboard(KeyboardUtil.KEYBOARD_DELAYED_TIME);
    }

    /*---------------------------------------------------------------------*/

    @Override
    public void onPause() {
        super.onPause();
    }

}
