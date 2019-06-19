package com.example.administrator.rolemanage.base.api;

import android.content.Context;
import android.os.Handler;

import java.lang.reflect.Type;

/**
 * Created by xmh on 16/7/6.
 * <p/>
 * 自定义网络请求框架
 */
@SuppressWarnings("deprecation")
public class MyHttpRequest<T> extends HttpRequest<String> {

    private Type mType;

    private CommonCallback<T> mCommonCallback;

    private static final Handler HANDLER = new Handler();

    public MyHttpRequest(Context context) {
        super(context);

//        setSelfResolution(true);
//        setShowToast(false);
    }

//    public MyHttpRequest(Context context, int timeout) {
//        super(context, timeout);
//
//        setSelfResolution(true);
//        setShowToast(false);
//    }
//
//    public MyHttpRequest(Context context, int connectTimeout, int responseTimeout) {
//        super(context, connectTimeout, responseTimeout);
//
//        setSelfResolution(true);
//        setShowToast(false);
//    }
//
//
//    @Override
//    public void post(String action, Map<String, ?> params, Type type) {
//        super.post(action, params, String.class);
//
//        mType = type;
//    }
//
//    @Override
//    public void get(String action, Map<String, ?> params, Type type) {
//        super.get(action, params, String.class);
//
//        mType = type;
//    }
//
//    @Override
//    public void put(String action, Map<String, ?> params, Type type) {
//        super.put(action, params, String.class);
//
//        mType = type;
//    }
//
//    @Override
//    public void put(String action, Object params, Type type) {
//        super.put(action, params, String.class);
//
//        mType = type;
//    }
//
//    @Override
//    public void post(String action, Object params, Type type) {
//        super.post(action, params, String.class);
//
//        mType = type;
//    }
//
//
//    public void setMyCommonCallback(final CommonCallback<T> commonCallback) {
//        mCommonCallback = commonCallback;
//        super.setCommonCallback(new CommonCallback<String>() {
//            @Override
//            public void onRequestSuccess(String arg0) {
//                requestCallback(arg0, mType);
//            }
//
//            @Override
//            public void onRequestFailure(int errorCode, String errorMessage) {
//                if (commonCallback != null) {
//                    commonCallback.onRequestFailure(errorCode, errorMessage);
//                }
//            }
//        });
//    }

    private void success(final T t) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (mCommonCallback != null) {
                    mCommonCallback.onRequestSuccess(t);
                }
            }
        });
    }

    private void failure(final int errorCode, final String errorMessage) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (mCommonCallback != null) {
                    mCommonCallback.onRequestFailure(errorCode, errorMessage);
                }
            }
        });
    }

}
