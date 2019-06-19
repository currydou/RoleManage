package com.example.administrator.rolemanage.okhttp.V2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yodoo.atinvoice.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * Created by next on 2018/5/16.
 */

public class HttpUtil {

    public static final String TAG = "OkHttpRequest";

    public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    public static final String CONTENT_FORM_DATA = "multipart/form-data; charset=utf-8";
    public static final String CONTENT_TYPE_TEXT = "text/plain; charset=utf-8";
    public static final String CONTENT_TYPE_PNG = "image/png; charset=UTF-8";
    public static final String CONTENT_TYPE_JPEG = "image/jpeg; charset=UTF-8";

    public static final String LOGIN_TYPE = "Android";

    public static final int JSON = 1;
    public static final int KEY_VALUE = 2;
    public static final int FORM_DATA = 3;


    @IntDef({JSON, KEY_VALUE, FORM_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaTypeInt {
    }

    public static final int GET = 1;
    public static final int POST = 2;

    @IntDef({GET, POST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestMethod {
    }

    public static Gson gson = new Gson();

    public static String getFullUrl(String url, List<Part> params, boolean urlEncoder) {
        StringBuilder urlFull = new StringBuilder();
        urlFull.append(url);
        if (urlFull.indexOf("?", 0) < 0 && params.size() > 0) {
            urlFull.append("?");
        }
        int flag = 0;
        for (Part part : params) {
            String key = part.getKey();
            String value = part.getValue();
            if (urlEncoder) {//只对key和value编码
                try {
                    key = URLEncoder.encode(key, "UTF-8");
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            urlFull.append(key).append("=").append(value);
            if (++flag != params.size()) {
                urlFull.append("&");
            }
        }

        return urlFull.toString();
    }

    public static void addHeaders(Request.Builder builder, RequestParams params) {

        builder.headers(params.headersBuilder.build());
        lod_d_headers(params.headersBuilder.build());
    }

    public static boolean judgeIfHttps(String url) {
//        if (url.startsWith("https")) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        return !(cwjManager == null || (networkInfo = cwjManager.getActiveNetworkInfo()) == null) && networkInfo.isAvailable();
    }

    public static void log_d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void log_d_part_list(List<Part> partList) {
        List<Map<String, String>> paramsList = new ArrayList<>();
        List<FileWrapper> fileList = new ArrayList<>();
        for (Part part : partList) {
            Map<String, String> map = new HashMap<>();
            map.put(part.getKey(), part.getValue());
            paramsList.add(map);

            if (part.getFileWrapper() != null) {
                fileList.add(part.getFileWrapper());
            }
        }
        if (fileList.size() == 0) {
            log_d("params : " + gson.toJson(paramsList));
        } else {
            for (FileWrapper fileWrapper : fileList) {
                log_d("files : " + gson.toJson(fileWrapper));
            }
        }
    }

    public static void lod_d_headers(Headers headers) {
        List<Map<String, String>> headList = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put(headers.name(i), headers.value(i));
            headList.add(map);
        }
        log_d("headers : " + gson.toJson(headList));
    }

    public static String getUrlEndName(Call call) {
        String url = call.request().url().toString();
        if (TextUtils.isEmpty(url)) {
            return "";
        } else if (url.contains("/")) {
            int lastIndex = url.lastIndexOf("/");
            return url.substring(lastIndex);
        } else {
            return "";
        }
    }
}
