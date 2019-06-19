package com.example.administrator.rolemanage.base.lazyfragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.rolemanage.utils.other.ViewInject;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LazyFragment extends Fragment {

    // 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
    // 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
    private boolean isPrepared;
    //标志当前页面是否可见
    private boolean isVisible;

    private Handler mHandler = new Handler();
    protected Activity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            autoInjectAllField();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if (getUserVisibleHint()) {//这里判断一次可见，后面还有一次，可以优化
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();//这里有可能没准备好，如果做一些动画结束操作是不是有些缺陷？暂时这样，没见用到这个方法。
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        getData();//数据请求
    }

    protected void onInvisible() {
    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void getData();//是不是要每次可见的时候都加载数据，还是加载过了，这里就不再自动加载。
    //如果需要加个isPrepared来判断，具体看莱聚+里的


    public final void autoInjectAllField() throws IllegalAccessException,
            IllegalArgumentException {
        // 得到Activity对应的Class
        Class<?> clazz = getClass();
        // 得到该Activity的所有字段
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 判断字段是否标注InjectView
            if (field.isAnnotationPresent(ViewInject.class)) {
                // 如果标注了，就获得它的id
                ViewInject inject = field.getAnnotation(ViewInject.class);
                int id = inject.value();
                if (id > 0) {
                    // 反射访问私有成员，必须加上这句
                    field.setAccessible(true);
                    // 然后对这个属性复制
                    field.set(this, findViewById(id));
                }
            }
        }
    }

    public final View findViewById(int id) {
        View view = getView();
        if (view == null) {
            return null;
        }
        return view.findViewById(id);
    }

    public void runOnUiThread(Runnable action) {
        if (mHandler != null) {
            mHandler.post(action);
        }
    }
}
