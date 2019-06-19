
package com.example.administrator.rolemanage.base.api;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.administrator.rolemanage.utils.other.FkbConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问网络数据
 *
 * @author xmh
 *         <p/>
 *         2016年7月6日
 */
@SuppressWarnings({"JavaDoc", "unused", "deprecation"})
public class HttpRequest<T> {

    private static final Map<String, String> KEY_MAP = new HashMap<String, String>();
    private static final String CONTENT_TYPE = "application/json;charset=utf-8";
    private static final String KEY_CONTENT_TYPE = "Content-Type";
    private static final Handler HANDLER = new Handler();
    private static final int DEF_TIME_OUT_RESPONSE = 15 * 1000;
    private static final int DEF_TIME_OUT_CONNECT = 10 * 1000;
    private static final int DEF_TIME_OUT = 15 * 1000;
    private static final int TIMEOUT_STATUS_CODE = 408;
    private static final String TAG = "API";
    private static final int CODE_OK = 1;

//    private static AsyncHttpClient httpClient = new AsyncHttpClient();
    private static int mSuccessCode = CODE_OK;

//    private ResponseHandler mResponseHandler = new ResponseHandler();
    private CommonCallback<T> mCommonCallback;
    private SpecialCallback mSpecialCallback;

    private String mContentType = CONTENT_TYPE;
    private boolean isShowToast = true;
    private boolean isSelfResolution;
    private Context mContext;
    private Toast mToast;

    public interface CommonCallback<T> {

        /**
         * 访问成功后回调此方法
         *
         * @param arg0 json解析后得到的对象
         */
        void onRequestSuccess(T arg0);

        /**
         * 访问失败后回调此方法
         *
         * @param errorCode    错误码
         * @param errorMessage 错误信息
         */
        void onRequestFailure(int errorCode, String errorMessage);

    }

    public interface SpecialCallback {

        void onStart();

        void onCancel();

        void onFinish();

        void onRetry(int retryNo);

        void onProgress(long bytesWritten, long totalSize, long progress);

    }

    public enum RequestType {
        GET, PUT, POST, DELETE
    }

    public static void setSuccessCode(int code) {
        mSuccessCode = code;
    }

    public static void putServerUrl(String url) {
        KEY_MAP.put(FkbConstants.IzhuoKey.URL, url);
    }

    public static void putResultKeyName(String result) {
        KEY_MAP.put(FkbConstants.IzhuoKey.RESULT, result);
    }

    public static void putContentKeyName(String content) {
        KEY_MAP.put(FkbConstants.IzhuoKey.CONTENT, content);
    }

    public static void putErrorCodeKeyName(String code) {
        KEY_MAP.put(FkbConstants.IzhuoKey.CODE, code);
    }

    /**
     * 此方法最好在程序启动时就调用，否则会报错
     */
    public static void putCallbackKeyNames(String url, String content,
                                           String code) {
        putCallbackKeyNames(url, null, content, code);
    }

    /**
     * 此方法最好在程序启动时就调用，否则会报错
     */
    public static void putCallbackKeyNames(String url, String result,
                                           String content, String code) {
        putServerUrl(url);
        putResultKeyName(result);
        putContentKeyName(content);
        putErrorCodeKeyName(code);
    }

    public HttpRequest(Context context) {
//        this(context, DEF_TIME_OUT_CONNECT, DEF_TIME_OUT_RESPONSE);
    }

//    public HttpRequest(Context context, int timeout) {
//        this(context, timeout, timeout);
//    }

//    public HttpRequest(Context context, int connectTimeout, int responseTimeout) {
//        this.mContext = context;
//        this.addHeader(KEY_CONTENT_TYPE, mContentType);
//
//        this.setConnectTimeout(connectTimeout);
//        this.setResponseTimeout(responseTimeout);
//    }
//
//    public static <T> HttpRequest<T> newAsyncInstance(Context context, int timeout) {
//        httpClient = new AsyncHttpClient();
//        HttpRequest<T> httpRequest = new HttpRequest<T>(context, timeout);
//        httpRequest.setUseSynchronousMode(false);
//        return httpRequest;
//    }
//
//    public static <T> HttpRequest<T> newAsyncInstance(Context context) {
//        return newAsyncInstance(context, DEF_TIME_OUT);
//    }
//
//    public static <T> HttpRequest<T> newSyncInstance(Context context, int timeout) {
//        httpClient = new SyncHttpClient();
//        HttpRequest<T> httpRequest = new HttpRequest<T>(context, timeout);
//        httpRequest.setUseSynchronousMode(true);
//        return httpRequest;
//    }
//
//    public static <T> HttpRequest<T> newSyncInstance(Context context) {
//        return newSyncInstance(context, DEF_TIME_OUT);
//    }

//    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
////        httpClient.setSSLSocketFactory(sslSocketFactory);
//    }
//
//    public static AsyncHttpClient getHttpClient() {
//        return httpClient;
//    }
//
//    public void setTimeout(int value) {
//        httpClient.setTimeout(value);
//    }
//
//    public void setConnectTimeout(int value) {
//        httpClient.setConnectTimeout(value);
//    }
//
//    public void setResponseTimeout(int value) {
//        httpClient.setResponseTimeout(value);
//    }
//
//    public void setTag(Object TAG) {
//        mResponseHandler.setTag(TAG);
//    }
//
//    public void addHeader(String header, String value) {
//        httpClient.addHeader(header, value);
//    }
//
//    public void removeHeader(String header) {
//        httpClient.removeHeader(header);
//    }
//
//    /**
//     * 根据TAG，取消请求
//     */
//    public void cancelRequestsByTAG(Object TAG, boolean mayInterruptIfRunning) {
//        httpClient.cancelRequestsByTAG(TAG, mayInterruptIfRunning);
//    }
//
//    /**
//     * 取消请求
//     */
//    public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
//        try {
//            httpClient.cancelRequests(context, mayInterruptIfRunning);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 取消所有请求
//     */
//    public void cancelAllRequests(boolean mayInterruptIfRunning) {
//        try {
//            httpClient.cancelAllRequests(mayInterruptIfRunning);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 取消请求
//     */
//    public void cancelRequests(Context context) {
//        cancelRequests(context, false);
//    }
//
//    /**
//     * 取消所有请求
//     */
//    public void cancelAllRequests() {
//        cancelAllRequests(false);
//    }
//
//    /**
//     * 根据TAG，取消请求
//     */
//    public void cancelRequestsByTAG(Object TAG) {
//        cancelRequestsByTAG(TAG, false);
//    }
//
//    public void setContentType(String contentType) {
//        this.mContentType = contentType;
//        this.addHeader(KEY_CONTENT_TYPE, mContentType);
//    }
//
//    public void delete(String action) {
//        request(RequestType.DELETE, action, new RequestParams(), String.class);
//    }
//
//    public void delete(String action, RequestParams params) {
//        request(RequestType.DELETE, action, params, String.class);
//    }
//
//    /**
//     * 异步网络请求，get方式
//     *
//     * @param action
//     * @param params
//     * @param type
//     */
//    public void get(String action, RequestParams params, final Type type) {
//        request(RequestType.GET, action, params, type);
//    }
//
//    /**
//     * 异步网络请求，put方式
//     *
//     * @param action
//     * @param params
//     * @param type
//     */
//    public void put(String action, RequestParams params, final Type type) {
//        request(RequestType.PUT, action, params, type);
//    }
//
//    /**
//     * 异步网络请求，post方式
//     *
//     * @param action
//     * @param params
//     * @param type
//     */
//    public void post(String action, RequestParams params, final Type type) {
//        request(RequestType.POST, action, params, type);
//    }
//
//    public void post(String action, Map<String, ?> params, final Type type) {
//        request(RequestType.POST, action, params, type);
//    }
//
//    public void get(String action, Map<String, ?> params, final Type type) {
//        request(RequestType.GET, action, params, type);
//    }
//
//    public void put(String action, Map<String, ?> params, final Type type) {
//        request(RequestType.PUT, action, params, type);
//    }
//
//    public void put(String action, Object params, final Type type) {
//        try {
//            put(action, getHttpEntity(params), type);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void post(String action, Object params, final Type type) {
//        try {
//            post(action, getHttpEntity(params), type);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void put(String action, HttpEntity entity, Type type) {
//        put(action, entity, mContentType, type);
//    }
//
//    public void post(String action, HttpEntity entity, Type type) {
//        post(action, entity, mContentType, type);
//    }
//
//    public void get(String action, HttpEntity entity, Type type) {
//        get(action, entity, mContentType, type);
//    }
//
//    public void delete(String action, HttpEntity entity, Type type) {
//        delete(action, entity, mContentType, type);
//    }
//
//    public void put(String action, HttpEntity entity, String contentType,
//                    Type type) {
//        request(RequestType.PUT, action, entity, contentType, type);
//    }
//
//    public void post(String action, HttpEntity entity, String contentType,
//                     Type type) {
//        request(RequestType.POST, action, entity, contentType, type);
//    }
//
//    public void get(String action, HttpEntity entity, String contentType,
//                    Type type) {
//        request(RequestType.GET, action, entity, contentType, type);
//    }
//
//    public void delete(String action, HttpEntity entity, String contentType,
//                       Type type) {
//        request(RequestType.DELETE, action, entity, contentType, type);
//    }
//
//    public HttpEntity getHttpEntity(String content) throws UnsupportedEncodingException {
//        d("请求参数：" + content);
//        StringEntity entity = new StringEntity(content, "utf-8");
//        entity.setContentType(mContentType);
//        return entity;
//    }
//
//    public void setCommonCallback(CommonCallback<T> callback) {
//        this.mCommonCallback = callback;
//    }
//
//    @SuppressWarnings("unused")
//    public void setSpecialCallback(SpecialCallback callback) {
//        this.mSpecialCallback = callback;
//    }
//
//    public void setUseSynchronousMode(boolean sync) {
//        mResponseHandler.setUseSynchronousMode(sync);
//    }
//
//    /**
//     * 设为false，将不再有任何提示，默认为true
//     *
//     * @param isShowToast
//     */
//    public void setShowToast(boolean isShowToast) {
//        this.isShowToast = isShowToast;
//    }
//
//    /**
//     * 设为true的话，程序将不再解析返回数据，而是直接回调，默认为false
//     *
//     * @param isSelfResolution
//     */
//    public void setSelfResolution(boolean isSelfResolution) {
//        this.isSelfResolution = isSelfResolution;
//    }
//
//    protected void showText(int resId) {
//        showText(getString(resId));
//    }
//
//    protected void showText(int res, Object... object) {
//        if (mContext != null) {
//            showText(getString(res, object));
//        }
//    }
//
//    protected void showText(final String text) {
//        HANDLER.post(new Runnable() {
//            @Override
//            public void run() {
//                if (isShowToast && !TextUtils.isEmpty(text) && mContext != null) {
//                    if (mToast != null) {
//                        mToast.cancel();
//                    }
//                    mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
//                    mToast.show();
//                }
//            }
//        });
//    }
//
//    protected String getString(int resId, Object... obj) {
//        if (mContext != null) {
//            return mContext.getString(resId, obj);
//        }
//        return null;
//    }
//
//    protected String getString(int resId) {
//        if (mContext != null) {
//            return mContext.getString(resId);
//        }
//        return null;
//    }
//
//    protected void i(String msg) {
//        if (FkbConstants.DEBUG) {
//            if (msg != null) {
//                Log.i(TAG, msg);
//            }
//        }
//    }
//
//    protected void d(String msg) {
//        if (FkbConstants.DEBUG) {
//            if (msg != null) {
//                Log.d(TAG, msg);
//            }
//        }
//    }
//
//    protected void e(String msg) {
//        if (FkbConstants.DEBUG) {
//            if (msg != null) {
//                Log.e(TAG, msg);
//            }
//        }
//    }
//
//    private String getHttpClientUrl(String action) {
//        String url = KEY_MAP.get(FkbConstants.IzhuoKey.URL);
//        if (action.startsWith(FkbConstants.IzhuoKey.HTTP_HEAD)
//                || action.startsWith(FkbConstants.IzhuoKey.HTTPS_HEAD)) {
//            url = action;
//        } else if (!TextUtils.isEmpty(url)) {
//            url = url + action;
//        } else {
//            throw new NullPointerException("请在程序启动的时候设置接口的服务器地址！");
//        }
//
//        d("url：" + url);
//        return url;
//    }
//
//    private HttpEntity getHttpEntity(Object params)
//            throws UnsupportedEncodingException {
//        String json = JsonDecoder.objectToJson(params);
//        return getHttpEntity(json);
//    }
//
//    private void request(RequestType requestType, String action,
//                         HttpEntity entity, String contentType, Type type) {
//        setContentType(contentType);
//        d("请求方式：" + requestType.name());
//        mResponseHandler.setType(type);
//        String httpClientUrl = getHttpClientUrl(action);
//        switch (requestType) {
//            case POST:
//                httpClient.post(mContext, httpClientUrl, entity, contentType, mResponseHandler);
//                break;
//            case PUT:
//                httpClient.put(mContext, httpClientUrl, entity, contentType, mResponseHandler);
//                break;
//            case GET:
//                httpClient.get(mContext, httpClientUrl, entity, contentType, mResponseHandler);
//                break;
//            case DELETE:
//                httpClient.delete(mContext, httpClientUrl, entity, contentType, mResponseHandler);
//                break;
//        }
//    }
//
//    private void request(RequestType requestMode, String action,
//                         RequestParams params, final Type type) {
//        d("请求参数：" + params.toString());
//        d("请求方式：" + requestMode.name());
//        mResponseHandler.setType(type);
//        String httpClientUrl = getHttpClientUrl(action);
//        switch (requestMode) {
//            case GET:
//                httpClient.get(mContext, httpClientUrl, params, mResponseHandler);
//                break;
//            case POST:
//                httpClient.post(mContext, httpClientUrl, params, mResponseHandler);
//                break;
//            case PUT:
//                httpClient.put(mContext, httpClientUrl, params, mResponseHandler);
//                break;
//            case DELETE:
//                httpClient.delete(mContext, httpClientUrl, null, params, mResponseHandler);
//                break;
//        }
//    }
//
//    private void request(RequestType requestMode, String action,
//                         Map<String, ?> params, final Type type) {
//        RequestParams requestParams = getRequestParams(action, params);
//        request(requestMode, action, requestParams, type);
//    }
//
//    @SuppressWarnings("UnusedParameters")
//    private RequestParams getRequestParams(String action, Map<String, ?> params) {
//        RequestParams requestParams = new RequestParams();
//        if (params != null) {
//            for (String key : params.keySet()) {
//                Object object = params.get(key);
//                if (object != null) {
//                    requestParams.put(key, object);
//                }
//            }
//        }
//        return requestParams;
//    }
//
//    @SuppressWarnings({"unchecked"})
//    private void requestCallback(String response) {
//        if (response != null) {
//            try {
//                success((T) response);
//            } catch (Exception e) {
//                e.printStackTrace();
//                showText(R.string.izhuo_toast_net_exception);
//                failure(FkbConstants.IzhuoError.CODE_SERVER_ERROR, e.getMessage());
//            }
//        } else {
//            d(FkbConstants.IzhuoError.NO_RESPONSE);
//            failure(FkbConstants.IzhuoError.CODE_SERVER_ERROR, FkbConstants.IzhuoError.NO_RESPONSE);
//        }
//    }
//
//    /**
//     * 请求成功回调
//     *
//     * @param response
//     * @param type
//     */
//    @SuppressWarnings("unchecked")
//    private void requestCallback(String response, Type type) {
//        if (TextUtils.isEmpty(response)) {
//            d(FkbConstants.IzhuoError.NO_RESPONSE);
//            failure(FkbConstants.IzhuoError.CODE_SERVER_ERROR, FkbConstants.IzhuoError.NO_RESPONSE);
//            return;
//        }
//        try {
//            JsonElement jsonData = new JsonParser().parse(response);
//            if (!jsonData.isJsonObject()) {
//                failure(FkbConstants.IzhuoError.CODE_SERVER_ERROR, FkbConstants.IzhuoError.NO_RESPONSE);
//                return;
//            }
//            JSONObject json = new JSONObject(response);
//            // if (!KEY_MAP.containsKey(IzhuoKey.RESULT)) {
//            // throw new NullPointerException(
//            // "请设置是否返回的keyName，请调用putResultKey(String result)");
//            // }
//            String resultKey = KEY_MAP.get(FkbConstants.IzhuoKey.RESULT);
//            if ((json.has(resultKey) ? json.getBoolean(resultKey) : json
//                    .getInt(KEY_MAP.get(FkbConstants.IzhuoKey.CODE)) == mSuccessCode)) {
//                if (!KEY_MAP.containsKey(FkbConstants.IzhuoKey.CONTENT)) {
//                    throw new NullPointerException(
//                            "请设置返回内容的keyName，请调用putContentKey(String content)");
//                }
//                String result = json.getString(KEY_MAP.get(FkbConstants.IzhuoKey.CONTENT));
//                T t;
//                if (type.equals(String.class)) {
//                    t = (T) result;
//                } else {
//                    t = JsonDecoder.jsonToObject(result, type);
//                }
//                if (t != null) {
//                    success(t);
//                } else {
//                    failure(FkbConstants.IzhuoError.CODE_SERVER_ERROR,
//                            FkbConstants.IzhuoError.get(FkbConstants.IzhuoError.CODE_SERVER_ERROR));
//                    showText(R.string.izhuo_toast_unknown_exception);
//                }
//            } else {
//                if (!KEY_MAP.containsKey(FkbConstants.IzhuoKey.CODE)) {
//                    throw new NullPointerException(
//                            "请设置错误码的keyName，请调用putErrorCodeKey(String code)");
//                }
//                int code = json.getInt(KEY_MAP.get(FkbConstants.IzhuoKey.CODE));
//                String message = FkbConstants.IzhuoError.get(code);
//                if (message != null) {
//                    showText(message);
//                } else {
//                    showText(R.string.izhuo_toast_unknown_exception);
//                }
//                d("code：" + code);
//                failure(code, message);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showText(R.string.izhuo_toast_net_exception);
//            failure(FkbConstants.IzhuoError.CODE_SERVER_ERROR, e.getMessage());
//        }
//    }
//
//    private void success(int statusCode, Header[] headers, String response, Type type) {
//        try {
//            for (Header header : headers) {
//                d(String.valueOf(header));
//            }
//            if (TextUtils.isEmpty(response)) {
//                failure(statusCode, getString(R.string.izhuo_toast_net_exception));
//                showText(R.string.izhuo_toast_net_exception);
//                return;
//            } else if (!MySSLSocketFactory.tokenValid(response)) {
//                FeikongbaoApplication.getInstance().startLogin();
//                return;
//            }
//            d("response：" + response);
//            d("type：" + type);
//            if (!response.contains(FkbConstants.IzhuoKey.ERROR)) {
//                if (isSelfResolution) {
//                    if (type.equals(String.class)) {
//                        requestCallback(response);
//                    } else {
//                        throw new IllegalStateException("在需要自己解析的时候，type请传String.class");
//                    }
//                } else {
//                    requestCallback(response, type);
//                }
//            } else {
//                JSONObject jsonObject = new JSONObject(response);
//                String errorMessage = jsonObject.getString(FkbConstants.IzhuoKey.ERROR);
//                failure(statusCode, errorMessage);
//                showText(errorMessage);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showText(R.string.izhuo_toast_net_exception);
//            failure(statusCode, FkbConstants.IzhuoError.JSON_ERROR);
//        }
//    }
//
//    private void failure(int statusCode, Header[] headers, String errorResponse, Throwable error) {
//        if (headers != null) {
//            for (Header header : headers) {
//                d(String.valueOf(header));
//            }
//        }
//        d("statusCode:" + statusCode);
//        if (statusCode == TIMEOUT_STATUS_CODE) {
//            failure(statusCode,
//                    getString(R.string.izhuo_toast_net_connect_timeout));
//            showText(R.string.izhuo_toast_net_connect_timeout);
//            return;
//        }
//        if (TextUtils.isEmpty(errorResponse) || error == null) {
//            failure(statusCode, getString(R.string.izhuo_toast_net_exception));
//            showText(R.string.izhuo_toast_net_exception);
//            return;
//        }
//        d("error:" + error.getLocalizedMessage());
//        String message = error.getMessage();
//        d("message:" + message);
//        d("errorResponse:" + errorResponse);
//        try {
//            JsonElement jsonData = new JsonParser().parse(errorResponse);
//            if (!jsonData.isJsonObject() && !jsonData.isJsonArray()) {
//                failure(statusCode, errorResponse);
//                showText(errorResponse);
//                return;
//            }
//            JSONObject jsonObject = new JSONObject(errorResponse);
//            String errorMessage = jsonObject.getString(FkbConstants.IzhuoKey.ERROR);
//            failure(statusCode, errorMessage);
//            showText(errorMessage);
//        } catch (Exception e) {
//            // e.printStackTrace();
//            if (!TextUtils.isEmpty(message)) {
//                showText(message);
//            } else {
//                showText(R.string.izhuo_toast_net_exception);
//            }
//            failure(statusCode, message);
//        }
//    }
//
//    private class ResponseHandler extends TextHttpResponseHandler {
//
//        private Type type;
//
//        @Override
//        public void onStart() {
//            super.onStart();
//            start();
//        }
//
//        @Override
//        public void onCancel() {
//            super.onCancel();
//            cancel();
//        }
//
//        @Override
//        public void onFinish() {
//            super.onFinish();
//            finish();
//        }
//
//        @Override
//        public void onProgress(long bytesWritten, long totalSize) {
//            super.onProgress(bytesWritten, totalSize);
//            progress(bytesWritten, totalSize);
//        }
//
//        @Override
//        public void onRetry(int retryNo) {
//            super.onRetry(retryNo);
//            retry(retryNo);
//        }
//
//        @Override
//        public void onSuccess(final int statusCode, final Header[] headers,
//                              final String responseString) {
//            if (type == null) {
//                throw new NullPointerException("Please set type!");
//            }
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    success(statusCode, headers, responseString, type);
//                }
//            }).start();
//        }
//
//        @Override
//        public void onFailure(final int statusCode, final Header[] headers,
//                              final String responseString, final Throwable throwable) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    failure(statusCode, headers, responseString, throwable);
//                }
//            }).start();
//        }
//
//        public void setType(Type type) {
//            this.type = type;
//        }
//    }
//
//    private void success(final T t) {
//        HANDLER.post(new Runnable() {
//            @Override
//            public void run() {
//                if (mCommonCallback != null) {
//                    mCommonCallback.onRequestSuccess(t);
//                }
//            }
//        });
//    }
//
//    private void failure(final int errorCode, final String errorMessage) {
//        HANDLER.post(new Runnable() {
//            @Override
//            public void run() {
//                if (mCommonCallback != null) {
//                    mCommonCallback.onRequestFailure(errorCode, errorMessage);
//                }
//            }
//        });
//    }
//
//    private void retry(int retryNo) {
//        if (mSpecialCallback != null) {
//            mSpecialCallback.onRetry(retryNo);
//        }
//    }
//
//    private void start() {
//        if (mSpecialCallback != null) {
//            mSpecialCallback.onStart();
//        }
//    }
//
//    private void cancel() {
//        if (mSpecialCallback != null) {
//            mSpecialCallback.onCancel();
//        }
//    }
//
//    private void finish() {
//        if (mSpecialCallback != null) {
//            mSpecialCallback.onFinish();
//        }
//    }
//
//    private void progress(final long bytesWritten, final long totalSize, final long count) {
//        HANDLER.post(new Runnable() {
//            @Override
//            public void run() {
//                if (mSpecialCallback != null) {
//                    mSpecialCallback.onProgress(bytesWritten, totalSize, count);
//                }
//            }
//        });
//    }
//
//    private void progress(final long bytesWritten, final long totalSize) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
//                // 上传进度显示
//                d(bytesWritten + " / " + totalSize);
//                progress(bytesWritten, totalSize, count);
//            }
//        }).start();
//    }

}
