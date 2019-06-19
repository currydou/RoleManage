package com.example.administrator.rolemanage.okhttp.V2;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.yodoo.atinvoice.R;
import com.yodoo.atinvoice.base.FeiKongBaoApplication;
import com.yodoo.atinvoice.constant.HttpConstant;
import com.yodoo.atinvoice.model.base.BaseResponse;
import com.yodoo.atinvoice.utils.organized.JsonDecoder;
import com.yodoo.atinvoice.utils.organized.StringUtils;
import com.yodoo.atinvoice.utils.organized.ToastUtils;
import com.yodoo.atinvoice.utils.other.IntentUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yodoo.atinvoice.okhttp.V2.HttpUtil.GET;
import static com.yodoo.atinvoice.okhttp.V2.HttpUtil.POST;
import static com.yodoo.atinvoice.okhttp.V2.HttpUtil.judgeIfHttps;

/**
 */

public class OkHttpRequest {
    private static final String TAG = "OkHttpRequest";

    private Handler handler = new Handler(Looper.getMainLooper());

    private static OkHttpRequest instance;

    private String networkError = FeiKongBaoApplication.instance.getString(R.string.network_error);

    private OkHttpClient okHttpClient;

    private boolean useHttps = false;

    public static OkHttpRequest getInstance() {
        if (instance == null) {
            synchronized (OkHttpRequest.class) {
                if (instance == null) {
                    instance = new OkHttpRequest();
                    return instance;
                }
            }
        }
        return instance;
    }

    private OkHttpRequest() {
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpRequest.class) {
                if (okHttpClient == null) {
                    HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    };
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS);
                    if (useHttps) {
                        HttpsManager.buildSSLSocketFactory(builder);
                        builder.hostnameVerifier(DO_NOT_VERIFY);
                    }
                    okHttpClient = builder.build();
                    return okHttpClient;
                }
            }

        }
        return okHttpClient;
    }

    public void getEnqueue(String url, RequestParams params, BaseCallback callback, Type type) {
        //  2018/5/16  超时时间
        enqueueRequest(GET, url, params, callback, type);
    }

    public void postEnqueue(String url, RequestParams params, BaseCallback callback, Type type) {
        //  2018/5/16  超时时间
        enqueueRequest(POST, url, params, callback, type);
    }

    private void enqueueRequest(@HttpUtil.RequestMethod int requestMethod, String url,
                                RequestParams params, BaseCallback callback, final Type type) {
        try {
            if (!HttpUtil.isNetAvailable(FeiKongBaoApplication.instance)) {
                //2018/5/16  show toast网络错误
                callback.onFailure(networkError);
                callback.onNetWorkFailure();
                return;
            }
            useHttps = judgeIfHttps(url);
            callback.onStart();
            Request.Builder builder = new Request.Builder();
            switch (requestMethod) {
                case GET:
                    url = HttpUtil.getFullUrl(url, params.getParamsList(), params.isUrlEncoder());
                    builder.get();
                    break;
                case POST:
                    builder.post(new ProgressRequestBody(params.getRequestBody(), new ProgressCallBackDelegate(callback)));
                    break;
            }
            HttpUtil.addHeaders(builder, params);
            builder.tag(params.getRequestTag())
                    .url(url);
            Request request = builder.build();
            HttpUtil.log_d("请求url:" + request.toString());
            getOkHttpClient().newCall(request).enqueue(new MyCallBack(callback, type));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String executeRequest(@HttpUtil.RequestMethod int requestMethod, String url, RequestParams params) {
        try {
            if (!HttpUtil.isNetAvailable(FeiKongBaoApplication.instance)) {
                // 2018/5/16  show toast网络错误
                return networkError;
            }
            useHttps = judgeIfHttps(url);
            Request.Builder builder = new Request.Builder();
            switch (requestMethod) {
                case GET:
                    url = HttpUtil.getFullUrl(url, params.getParamsList(), params.isUrlEncoder());
                    builder.get();
                    break;
                case POST:
                    builder.post(params.getRequestBody());
                    break;
            }
            HttpUtil.addHeaders(builder, params);
            builder.tag(params.getRequestTag())
                    .url(url);
            Request request = builder.build();
            HttpUtil.log_d("请求url:" + request.toString());

            Response response = getOkHttpClient().newCall(request).execute();
            String json = response.body().string();
            HttpUtil.log_d(request.tag().toString() + "请求返回:" + json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return networkError;
        }
    }

    private class MyCallBack<T extends BaseResponse> implements Callback {

        private BaseCallback<T> callBack;
        private Type type;

        public MyCallBack(BaseCallback<T> callBack, Type type) {
            this.callBack = callBack;
            this.type = type;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            String failStr = call.request().toString();
            HttpUtil.log_d("请求onFailure" + failStr);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        callBack.onFailure(networkError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            int code = 0;
            String message = "";
            String responseJson = null;
            if (response.body() == null) {
                return;
            }
            responseJson = response.body().string();
            HttpUtil.log_d(HttpUtil.getUrlEndName(call) + "\n" + "请求onResponse:" + responseJson);
            if (responseJson.contains(HttpConstant.HttpRespKey.KEY_CODE) &&
                    responseJson.contains(HttpConstant.HttpRespKey.KEY_MESSAGE)) {  //是否有code和message
                if (responseJson.contains(HttpConstant.HttpRespKey.KEY_CODE)) {
                    code = JsonDecoder.getInt(responseJson, HttpConstant.HttpRespKey.KEY_CODE);
                }
                if (responseJson.contains(HttpConstant.HttpRespKey.KEY_MESSAGE)) {
                    message = JsonDecoder.getString(responseJson, HttpConstant.HttpRespKey.KEY_MESSAGE);
                }
                if (handleCodeHere(code, responseJson)) {
                    return;
                }
                requestCallback(code, message, responseJson, type);
            } else if (responseJson.contains(HttpConstant.HttpRespKey.KEY_STATUS_CODE) &&
                    responseJson.contains(HttpConstant.HttpRespKey.KEY_MSG)) {      //是否有statuscode和msg
                if (responseJson.contains(HttpConstant.HttpRespKey.KEY_STATUS_CODE)) {
                    code = JsonDecoder.getInt(responseJson, HttpConstant.HttpRespKey.KEY_STATUS_CODE);
                }
                if (responseJson.contains(HttpConstant.HttpRespKey.KEY_MSG)) {
                    message = JsonDecoder.getString(responseJson, HttpConstant.HttpRespKey.KEY_MSG);
                }
                if (handleCodeHere(code, responseJson)) {
                    return;
                }
                requestCallback(code, message, responseJson, type);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.onFailure(networkError);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }

        private boolean handleCodeHere(int code, String responseJson) {
            boolean handleHere = false;
            switch (code) {
                case HttpConstant.HttpCode.CODE_2://token失效
                    //case HttpConstant.HttpCode.CODE_10030://token失效  后台确认没有用到
                case HttpConstant.HttpCode.CODE_10002://token失效
                    ToastUtils.dismissProcessDialog();
                    ToastUtils.dismissProcessBar();
                    IntentUtils.entryTokenNotValide(FeiKongBaoApplication.instance);
                    handleHere = true;
                    break;
            }
            return handleHere;
        }


        private void requestCallback(final int code, final String message, final String responseJson, Type type) {
            T t = null;
            try {
                t = HttpUtil.gson.fromJson(responseJson, type);
            } catch (Exception e) {
                e.printStackTrace();
                StringUtils.d(TAG, e.getMessage());
            }
            try {
                final T finalT = t;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(code, message, responseJson, finalT);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                StringUtils.d(TAG, e.getMessage());
            }
        }

    }

    public class ProgressCallBackDelegate implements ProgressCallback {

        private BaseCallback callBack;

        public ProgressCallBackDelegate(BaseCallback callBack) {
            this.callBack = callBack;
        }

        @Override
        public void updateProgress(final int progress, final long networkSpeed, final boolean done) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onProgress(progress, networkSpeed, done);
                    }
                }
            });
        }
    }

    //  2018/4/6   取消请求 review
    public void cancleAll(Object tag) {
        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    public void download(String url, final String filePath, final String fileName, final FileDownloadCallback callback) {
        Request.Builder builder = new Request.Builder();
        builder.tag(111)
                .url(url);
        Request request = builder.build();
        HttpUtil.log_d("请求url:" + request.toString());
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200 || response.body() == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure();
                        }
                    });
                    return;
                }
                //将返回结果转化为流，并写入文件
                int len;
                byte[] buf = new byte[2048];
                InputStream inputStream = response.body().byteStream();
                /**
                 * 写入本地文件
                 */
                String responseFileName = getHeaderFileName(response);
                File file = null;
                /**
                 *如果服务器没有返回的话,使用自定义的文件名字
                 */
                if (TextUtils.isEmpty(responseFileName)) {
                    file = new File(filePath + "/" + fileName);
                } else {
                    file = new File(filePath + "/" + responseFileName);
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((len = inputStream.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, len);
                }
                try {
                    callback.onSuccess(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            }
        });

    }

    /**
     * 解析文件头
     * Content-Disposition:attachment;filename=FileName.txt
     * Content-Disposition: attachment; filename*="UTF-8''%E6%9B%BF%E6%8D%A2%E5%AE%9E%E9%AA%8C%E6%8A%A5%E5%91%8A.pdf"
     */
    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(dispositionHeader)) {
            dispositionHeader.replace("attachment;filename=", "");
            dispositionHeader.replace("filename*=utf-8", "");
            String[] strings = dispositionHeader.split("; ");
            if (strings.length > 1) {
                dispositionHeader = strings[1].replace("filename=", "");
                dispositionHeader = dispositionHeader.replace("\"", "");
                return dispositionHeader;
            }
            return "";
        }
        return "";
    }

}
