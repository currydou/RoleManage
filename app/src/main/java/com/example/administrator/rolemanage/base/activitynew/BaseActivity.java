package com.example.administrator.rolemanage.base.activitynew;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.administrator.rolemanage.R;
import com.example.administrator.rolemanage.base.basecontractT.BasePresenterT;
import com.example.administrator.rolemanage.base.basecontractT.IView;
import com.example.administrator.rolemanage.utils.organized.KeyboardUtil;
import com.example.administrator.rolemanage.utils.organized.StringUtils;
import com.example.administrator.rolemanage.utils.organized.ToastUtils;

import butterknife.ButterKnife;

/**
 * 需要使用 无actionbar主题(对应主题 baseTheme)
 * toolbar 在实现类的布局里统一复用的toolbar布局文件或者自定义;
 * 使用mView的方法，必须先判断不为空！
 * <p>
 * Created by next on 2018/6/15.
 */

public abstract class BaseActivity<V extends IView, P extends BasePresenterT> extends AppCompatActivity implements IView {

    protected BaseActivity mContext;
    public P mPresenter;
    protected Toolbar mToolbar;
    private InputMethodManager mMethodManager;
    protected Intent mIntent;
    public Handler mHandler = new Handler();

    /*----------------------------生命周期------------------------------------*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        mContext = this;
        mMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initToolbars();
        ButterKnife.bind(this);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
        initViews(savedInstanceState);
        initDatas(savedInstanceState);
        initListeners(savedInstanceState);
       /* //有盟统计
        MobclickAgent.setDebugMode(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);*/
        StringUtils.d("当前界面名称=", getClass().getCanonicalName());
    }

    @Override
    protected void onNewIntent(Intent intent) {  //用于 singletask 模式接收参数
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.attach((V) this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();
            mPresenter = null;
        }
        super.onDestroy();
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }
*/

    @Override
    public Resources getResources() {
        /*************** Start 限制字体不跟随系统变化 2016-12-22***************/
        Resources res = super.getResources();
        Configuration config = new Configuration();
        try {
            config.setToDefaults();
            //选完附件回来有时候会空指针，Locale.toString()
            res.updateConfiguration(config, res.getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
        /*************** End 限制字体不跟随系统变化 2016-12-22***************/
        //return super.getResources();
    }
    /*----------------------------抽象方法------------------------------------*/


    /**
     * 返回布局id
     *
     */
    public abstract int initContentView();


    /**
     * 实例化presenter
     *
     * @return 不用的话，返回null
     */
    public abstract P initPresenter();

    /**
     * 初始化View
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initDatas(Bundle savedInstanceState);

    /**
     * 初始化数据监听
     */
    public abstract void initListeners(Bundle savedInstanceState);


    public P getPresenter() {
        return mPresenter;
    }
    /*----------------------------实现方法------------------------------------*/
    protected void initToolbars() {
        View view = findViewById(R.id.toolbar);
        if (view != null) {
            mToolbar = (Toolbar) view;
            mToolbar.setTitle("");
            mToolbar.setSubtitle("");
            mToolbar.setLogo(null);
            //去除内间距
            mToolbar.setContentInsetsAbsolute(0, 0);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setSupportActionBar(mToolbar);

        }
    }

    public void showProcess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.showProcessDialog(BaseActivity.this, R.string.zhengzaichuliqingshaodeng);
                ToastUtils.showProcessBar(mContext);
            }
        });
    }

    public void dismissProcess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.dismissProcessDialog();
                ToastUtils.dismissProcessBar();
            }
        });
    }

    protected void handleIntent() {
        mIntent = getIntent();
    }

    /*----------------------------一般方法------------------------------------*/

    public void hideKeyboard(long delayMillis) {
        //延迟弹出软键盘
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.hideKeyboard(mContext);
            }
        }, delayMillis);
    }

    public void hideKeyboard() {
        hideKeyboard(KeyboardUtil.KEYBOARD_DELAYED_TIME);
    }

    public void showKeyboard(View view) {
        showKeyboard(view, KeyboardUtil.KEYBOARD_DELAYED_TIME);
    }

    public void showKeyboard(final View view, long delayMillis) {
        //延迟弹出软键盘
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }, delayMillis);
    }

}
