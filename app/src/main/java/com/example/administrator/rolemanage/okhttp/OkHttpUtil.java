package com.example.administrator.rolemanage.okhttp;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yodoo.atinvoice.BuildConfig;
import com.yodoo.atinvoice.R;
import com.yodoo.atinvoice.base.FeiKongBaoApplication;
import com.yodoo.atinvoice.constant.HttpConstant;
import com.yodoo.atinvoice.constant.PerfUtilConstant;
import com.yodoo.atinvoice.model.base.BaseResponse;
import com.yodoo.atinvoice.utils.organized.AppUtils;
import com.yodoo.atinvoice.utils.organized.JsonDecoder;
import com.yodoo.atinvoice.utils.organized.PerfUtil;
import com.yodoo.atinvoice.utils.organized.StringUtils;
import com.yodoo.atinvoice.utils.organized.ToastUtils;
import com.yodoo.atinvoice.utils.organized.UploadUtil;
import com.yodoo.atinvoice.utils.other.IntentUtils;
import com.yodoo.wbz.wxapi.WxUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lib on 2016/9/14.
 */
public class OkHttpUtil {
    private final String TAG = "OkHttpUtil";
    private static OkHttpUtil instance;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FORM_DATA = MediaType.parse("multipart/form-data; charset=utf-8");

    private OkHttpClient okHttpsClient;
    private OkHttpClient okHttpClient;
    public Gson gson = new Gson();
    private boolean resolveBySelf = false;
    private boolean buildHeaderTicket = false;
    private boolean useHttps = true;

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                    return instance;
                }
            }

        }
        return instance;
    }

    public OkHttpClient getOkHttpsClient() {
        if (okHttpsClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpsClient == null) {
                    HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    };
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    buildSSLSocketFactory(builder);
                    builder.hostnameVerifier(DO_NOT_VERIFY)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS);
                    okHttpsClient = builder.build();
                    return okHttpsClient;
                }
            }

        }
        return okHttpsClient;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS);
                    okHttpClient = builder.build();
                    return okHttpClient;
                }
            }

        }
        return okHttpClient;
    }

    private void buildSSLSocketFactory(OkHttpClient.Builder builder) {
        try {
            SSLSocketFactory sslSocketFactory;
            if (BuildConfig.RELEASE) {
                sslSocketFactory = getSSLSocketFactory(FeiKongBaoApplication.instance.getAssets().open("service-atpiao.crt"));
            } else {
                sslSocketFactory = getSSLSocketFactory(FeiKongBaoApplication.instance.getAssets().open("public-cert.crt"));
            }
            if (sslSocketFactory != null) {
                builder.sslSocketFactory(sslSocketFactory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OkHttpUtil() {

    }

    /**
     * 载入证书
     */
    public static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            //用我们的证书创建一个keystore
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = "server" + Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //创建一个trustmanager，只信任我们创建的keystore
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String postJsonExecute(String url, Object object) {
        if (!AppUtils.isNetAvailable(FeiKongBaoApplication.instance)) {
            ToastUtils.showToast(FeiKongBaoApplication.instance,R.string.toast_net_not_available);
            return "";
        }

        String reqJson = gson.toJson(object);
        RequestBody body = RequestBody.create(JSON, reqJson);
        Request.Builder builder = new Request.Builder();
        buildHeadToken(builder);
        builder.header("user-agent", "Android")
                .header("loginType", "Android")
                .header("version", StringUtils.getVersion())
                .url(url)
                .post(body);
        Request request = builder.build();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, request.tag().toString() + "请求url:" + request.toString());
            Log.d(TAG, request.tag().toString() + "请求json:" + reqJson);
        }
        try {
            Response response = getOkHttpsClient().newCall(request).execute();
            String json = response.body().string();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, request.tag().toString() + "请求返回:" + json);
            }
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public <T extends BaseResponse> void postFormdata(File file, String url, Map<String, String> param,
                                                      final ApiCallBack<T> callBack, final Type type) {
        if (!AppUtils.isNetAvailable(FeiKongBaoApplication.instance)) {
            ToastUtils.showToast(FeiKongBaoApplication.instance,R.string.toast_net_not_available);
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "请求url:" + url);
        }
        Log.d(TAG, "请求url:" + url);
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(MultipartBody.FORM);
        if (param != null && param.size() > 0) {
            Iterator<String> it = param.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = param.get(key);
               /* multipartBuilder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, value));*/
                multipartBuilder.addFormDataPart(key, value);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "uploadImg: " + key + "=" + value);
                }
            }
        }
        if (file != null) {
            file = UploadUtil.getInstance().compressImage(file);
            RequestBody fileBody = RequestBody.create(FORM_DATA, file);
            multipartBuilder.addFormDataPart("file", file.getAbsolutePath(), fileBody);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "uploadImg: filename=" + file.getName());
            }
        }
        RequestBody requestBody = multipartBuilder.build();
        Request.Builder builder = new Request.Builder();
        buildHeadToken(builder);
        builder.header("user-agent", "Android")
                .header("loginType", "Android")
                .header("version", StringUtils.getVersion())
                .tag(type)
                .url(url)
                .post(requestBody);
        Request request = builder.build();
        getOkHttpsClient().newCall(request).enqueue(new MyCallBack(callBack, type));
    }

    public <T extends BaseResponse> void postJsonEnqueue(String url, Object object, final ApiCallBack<T> callBack, final Type type) {
//        if (!AppUtils.isNetAvailable(FeiKongBaoApplication.instance)) {
//            ToastUtils.showToast(FeiKongBaoApplication.instance,R.string.toast_net_not_available);
//            return;
//        }

        String reqJson = gson.toJson(object);
        RequestBody body = RequestBody.create(JSON, reqJson);

        Request.Builder builder = new Request.Builder();
        buildHeadToken(builder);
        buildHeadTicket(builder);
        buildHeadAccessToken(builder);
        builder.header("user-agent", "Android")
                .header("loginType", "Android")
                .header("version", StringUtils.getVersion())
                .tag(type)
                .url(url)
                .post(body);
        Request request = builder.build();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "请求url:" + request.toString());
            Log.d(TAG, "request.tag : " + request.tag().toString());
            Log.d(TAG, "请求json:" + reqJson);
        }
        getOkHttpsClient().newCall(request).enqueue(new MyCallBack(callBack, type));
    }

    public <T extends BaseResponse> void postKeyValueEnqueue(String url, FormBody.Builder builder, final ApiCallBack<T> callBack, final Type type) {
        postKeyValueEnqueue(url, builder, callBack, type, true);
    }

    public <T extends BaseResponse> void postKeyValueEnqueue(String url, FormBody.Builder formBodyBuilder, final ApiCallBack<T> callBack, final Type type, boolean addCommonPm) {
//        if (addCommonPm) {
//            builder.add(Constant.PM_TOKEN, PerfUtil.getThirdUserInfo().getLoginToken());
//            builder.add(Constant.PM_ACTIVE_UID, PerfUtil.getString(Constant.SP_ACTIVE_UID));
//        }
        if (!AppUtils.isNetAvailable(FeiKongBaoApplication.instance)) {
            ToastUtils.showToast(FeiKongBaoApplication.instance,R.string.toast_net_not_available);
            return;
        }
        Request.Builder builder = new Request.Builder();
        buildHeadToken(builder);
        builder.tag(type)
                .header("user-agent", "Android")
                .header("loginType", "Android")
                .header("version", StringUtils.getVersion())
                .url(url)
                .post(formBodyBuilder.build());
        Request request = builder.build();
        if (addCommonPm && BuildConfig.DEBUG) {
            Log.d(TAG, "get请求url:" + url);
            Log.d(TAG, "request.tag : " + request.tag().toString());
        }
        getOkHttpsClient().newCall(request).enqueue(new MyCallBack(callBack, type));
    }

    public <T extends BaseResponse> void getEnqueue(String url, final ApiCallBack<T> callBack,
                                                    final Type type) {
        if (!AppUtils.isNetAvailable(FeiKongBaoApplication.instance)) {
            ToastUtils.showToast(FeiKongBaoApplication.instance,R.string.toast_net_not_available);
            return;
        }
        Request.Builder builder = new Request.Builder();
        buildHeadToken(builder);
        buildHeadAccessToken(builder);
        builder.header("user-agent", "Android")
                .header("loginType", "Android")
                .header("version", StringUtils.getVersion())
                .url(url);
        Request request = builder.build();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "get请求url:" + url);
        }
        if (useHttps) {
            getOkHttpsClient().newCall(request).enqueue(new MyCallBack(callBack, type));
        } else {
            getOkHttpClient().newCall(request).enqueue(new MyCallBack(callBack, type));
        }
    }

    private void buildHeadToken(Request.Builder builder) {
        if (PerfUtil.getUserInfo() != null) {
            String token = PerfUtil.getUserInfoNotNull().getLoginToken();
            if (!TextUtils.isEmpty(token)) {
                builder.header("token", token);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "请求token:" + token);
                }
            }
        }
    }

    private void buildHeadTicket(Request.Builder builder) {
//        if (buildHeaderTicket) {
        String ticket = PerfUtil.getString(PerfUtilConstant.KEY_WX_TICKET);
        if (!TextUtils.isEmpty(ticket)) {
            builder.header("ticket", ticket);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "请求ticket:" + ticket);
            }
        }
//        }
    }
     private void buildHeadAccessToken(Request.Builder builder) {
//        if (buildHeaderTicket) {
        String accessToken = PerfUtil.getString(PerfUtilConstant.KEY_ALI_ACCESS_TOKEN);
        if (!TextUtils.isEmpty(accessToken)) {
            builder.header("accessToken", accessToken);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "请求accessToken:" + accessToken);
            }
        }
//        }
    }

    class MyCallBack<T extends BaseResponse> implements Callback {

        private ApiCallBack<T> callBack;
        private Type type;
        private String message = "";

        public MyCallBack(ApiCallBack<T> callBack, Type type) {
            this.callBack = callBack;
            this.type = type;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            String failStr = call.request().toString();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, failStr + "请求onFailure");
                e.printStackTrace();
            }
            callBack.onFailure(e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            int code = 0;
            String responseJson = response.body().string();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "请求onResponse:" + responseJson);
            }
            if (responseJson.contains(HttpConstant.HttpRespKey.KEY_CODE)) {
                code = JsonDecoder.getInt(responseJson, HttpConstant.HttpRespKey.KEY_CODE);
            } else if (responseJson.contains(HttpConstant.HttpRespKey.KEY_STATUS_CODE)) {
                code = JsonDecoder.getInt(responseJson, HttpConstant.HttpRespKey.KEY_STATUS_CODE);
            }
            if (responseJson.contains(HttpConstant.HttpRespKey.KEY_MESSAGE)) {
                message = JsonDecoder.getString(responseJson, HttpConstant.HttpRespKey.KEY_MESSAGE);
            }
            if (handleCodeHere(code, responseJson)) {
                return;
            }
            //是否自己处理
//            if (resolveBySelf) {
//                requestCallback(responseJson);
//            } else {
            requestCallback(responseJson, type);
//            }
        }

        private boolean handleCodeHere(int code, String responseJson) {
            boolean handleHere = false;
            switch (code) {
                case HttpConstant.HttpCode.CODE_2://token失效
                case HttpConstant.HttpCode.CODE_10002://token失效
                case HttpConstant.HttpCode.CODE_10030://token失效
                    ToastUtils.dismissProcessDialog();
                    ToastUtils.dismissProcessBar();
                    IntentUtils.entryTokenNotValide(FeiKongBaoApplication.instance);
                    handleHere = true;
                    break;
//                case HttpConstant.HttpCode.CODE_500:
//                    ToastUtils.showToast(message);
//                    callBack.onFailure(message);
//                    handleHere = true;
//                    break;
                case HttpConstant.HttpCode.CODE_10007: //ticket失效 (只有微信登录相关的接口会返回这个状态码，其他接口header里传了也没事)
                case HttpConstant.HttpCode.CODE_10008:
                    WxUtils.requestWXCode(FeiKongBaoApplication.instance);
                    handleHere = true;
                    break;
                case HttpConstant.HttpCode.CODE_10021:  //跳转到登录注册界面
                    PerfUtil.setUserInfo(null);
                    IntentUtils.entryLogin2(FeiKongBaoApplication.instance);
                    handleHere = true;
                    break;
            }
            return handleHere;
        }

        private void requestCallback(String response) {
//            callBack.onSuccess(response);
//            return;
        }

        private void requestCallback(String responseJson, Type type) {
            T t;
            try {
                t = gson.fromJson(responseJson, type);
            } catch (Exception e) {
                t = null;
                e.printStackTrace();
            }
            if (t == null) {
                callBack.onFailure(message);  //解析失败，是否要显示message错误信息
            } else if (t.getCode() == HttpConstant.HttpCode.CODE_1 ||
                    t.getCode() == HttpConstant.HttpCode.CODE_10000 ||
                    t.getStatusCode() == HttpConstant.HttpCode.CODE_10000 ||
                    t.getCode() == HttpConstant.HttpCode.CODE_10000 ||
//                    t.getCode() == HttpConstant.HttpCode.CODE_12002 ||
//                    t.getCode() == HttpConstant.HttpCode.CODE_10006 ||
//                    t.getCode() == HttpConstant.HttpCode.CODE_10007 ||
//                    t.getCode() == HttpConstant.HttpCode.CODE_10018 ||
//                    t.getCode() == HttpConstant.HttpCode.CODE_10022 ||
//                    t.getCode() == HttpConstant.HttpCode.CODE_10026 ||  //需要在后面处理的时候，先看看之前有没有
                    t.getCode() == HttpConstant.HttpCode.CODE_10101 ||
                    t.getCode() == HttpConstant.HttpCode.CODE_10300 ||
                    t.getCode() == HttpConstant.HttpCode.CODE_50000 ||
                    t.getCode() == HttpConstant.HttpCode.CODE_60000 /*||
                    t.getStatusCode() == HttpConstant.HttpCode.CODE_90000*/
                    ) {
                if (t.getData() == null)
                    callBack.onFailure(getMessage(t));  //解析成功要显示message(不能只这样，还有需要自己回调处理的)，可以让data返回的统一值，null、空串、什么的
                else callBack.onSuccess(t);
            } else {
                if (!TextUtils.isEmpty(t.getMessage())) {
                    ToastUtils.showToast(FeiKongBaoApplication.instance,getMessage(t));
                }
                callBack.onFailure(getMessage(t));
            }
        }

        private String getMessage(T t) {
            if (TextUtils.isEmpty(t.getMessage())) {
                if (TextUtils.isEmpty(t.getMsg())) {
                    return "";
                } else {
                    return t.getMsg();
                }
            } else {
                return t.getMessage();
            }
        }
    }

    public void cancleAll(Object tag) {
        Dispatcher dispatcher = okHttpsClient.dispatcher();
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


    public boolean isBuildHeaderTicket() {
        return buildHeaderTicket;
    }

    public void setBuildHeaderTicket(boolean buildHeaderTicket) {
        this.buildHeaderTicket = buildHeaderTicket;
    }

    public void useHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }

    public void resolveBySelf(boolean resolveBySelf) {
        this.resolveBySelf = resolveBySelf;
    }
}
