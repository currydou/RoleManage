/*
 * Copyright (C) 2015 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.administrator.rolemanage.okhttp.V2;

import android.text.TextUtils;

import com.example.administrator.rolemanage.constant.PerfUtilConstant;
import com.example.administrator.rolemanage.utils.organized.PerfUtil;
import com.example.administrator.rolemanage.utils.organized.StringUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * 用法
 *
 * @see Demo
 */
public class RequestParams {


    private Gson gson = new Gson();

    protected final Headers.Builder headersBuilder = new Headers.Builder();
    private boolean urlEncoder;//是否进行URL编码

    private int mediaType = HttpUtil.JSON;
    private List<Part> paramsList = new ArrayList<>();
    private final List<Part> files = new ArrayList<>();
    private Object objectParams;
    private Object requestTag;


    /**
     * URL编码，只对GET,DELETE,HEAD有效
     */
    public void urlEncoder() {
        urlEncoder = true;
    }

    public boolean isUrlEncoder() {
        return urlEncoder;
    }


    public RequestParams() {
        init();
    }

    private void init() {

        /*
        //公共参数
        List<Part> commonParams = OkHttpFinal.getInstance().getCommonParams();
        if (commonParams != null && commonParams.size() > 0) {
            params.addAll(commonParams);
        }*/

        //添加公共header
        /*Headers commonHeaders = OkHttpFinal.getInstance().getCommonHeaders();
        if (commonHeaders != null && commonHeaders.size() > 0) {
            for (int i = 0; i < commonHeaders.size(); i++) {
                String key = commonHeaders.name(i);
                String value = commonHeaders.value(i);
                headers.add(key, value);
            }
        }*/
        String token = PerfUtil.getUserInfoNotNull().getToken();
        addHeader("token", token);
        addHeader("charset", "UTF-8");
        addHeader("user-agent", HttpUtil.LOGIN_TYPE);
        addHeader("loginType", HttpUtil.LOGIN_TYPE);
        addHeader("version", StringUtils.getVersion());
        String ticket = PerfUtil.getString(PerfUtilConstant.KEY_WX_TICKET);
        if (!TextUtils.isEmpty(ticket)) {
            addHeader("ticket", ticket);
        }
        String accessToken = PerfUtil.getString(PerfUtilConstant.KEY_ALI_ACCESS_TOKEN);
        if (!TextUtils.isEmpty(accessToken)) {
            addHeader("accessToken", accessToken);
        }

    }


    public Object getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(Object requestTag) {
        this.requestTag = requestTag;
    }

    public void setMediaType(@HttpUtil.MediaTypeInt int mediaType) {
        this.mediaType = mediaType;
    }

    public List<Part> getParamsList() {
        return paramsList;
    }

    /**
     * 所有格式下  一个一个添加 参数
     *
     * @param key
     * @param value
     */
    public void addParams(String key, Object value) {
        if (value == null) {
            value = "";
        }
        Part part = new Part(key, String.valueOf(value));
        if (!StringUtils.isEmpty(key) && !paramsList.contains(part)) {
            paramsList.add(part);
        }
    }

    /**
     * KEY_VALUE 、JSON 格式下  可以直接将model类传进来
     *
     * @param objectParams
     */
    public void addParams(Object objectParams) {
        this.objectParams = objectParams;
    }

    /**
     * FORM_DATA 格式 传的文件
     *
     * @param key
     * @param files
     */
    public void addParams(String key, List<File> files) {
        for (File file : files) {
            if (file == null || !file.exists() || file.length() == 0) {
                continue;
            }
            addFormDataPart(key, file);
        }
    }

    /**
     * FORM_DATA 格式 传的文件
     *
     * @param key
     * @param file
     */
    public void addFormDataPart(String key, File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            return;
        }

        boolean isPng = file.getName().lastIndexOf("png") > 0 || file.getName().lastIndexOf("PNG") > 0;
        if (isPng) {
            addFormDataPart(key, file, HttpUtil.CONTENT_TYPE_PNG);
            return;
        }

        boolean isJpg = file.getName().lastIndexOf("jpg") > 0 || file.getName().lastIndexOf("JPG") > 0
                || file.getName().lastIndexOf("jpeg") > 0 || file.getName().lastIndexOf("JPEG") > 0;
        if (isJpg) {
            addFormDataPart(key, file, HttpUtil.CONTENT_TYPE_JPEG);
            return;
        }

        addFormDataPart(key, new FileWrapper(file, null));
    }

    private void addFormDataPart(String key, File file, String contentType) {
        if (file == null || !file.exists() || file.length() == 0) {
            return;
        }
        MediaType mediaType = null;
        try {
            mediaType = MediaType.parse(contentType);
        } catch (Exception e) {
            HttpUtil.log_d(e.getMessage());
        }
        addFormDataPart(key, new FileWrapper(file, mediaType));
    }

    private void addFormDataPart(String key, FileWrapper fileWrapper) {
        if (!StringUtils.isEmpty(key) && fileWrapper != null) {
            File file = fileWrapper.getFile();
            if (file == null || !file.exists() || file.length() == 0) {
                return;
            }
            files.add(new Part(key, fileWrapper));
        }
    }


    //==================================params====================================

    protected RequestBody getRequestBody() {
        RequestBody body = null;
        if (mediaType == HttpUtil.JSON) {
            String json;
            if (objectParams == null) {
                json = getParamsJsonString();
            } else {
                json = gson.toJson(objectParams);
            }
            HttpUtil.log_d("JSON 格式");
            HttpUtil.log_d(json);
            body = RequestBody.create(MediaType.parse(HttpUtil.CONTENT_TYPE_JSON), json);
        } else if (mediaType == HttpUtil.FORM_DATA) {
            boolean hasData = false;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (Part part : paramsList) {
                String key = part.getKey();
                String value = part.getValue();
                builder.addFormDataPart(key, value);
                hasData = true;
            }
            for (Part part : files) {
                String key = part.getKey();
                FileWrapper file = part.getFileWrapper();
                if (file != null) {
                    hasData = true;
                    builder.addFormDataPart(key, file.getFileName(), RequestBody.create(file.getMediaType(), file.getFile()));
                }
            }
            HttpUtil.log_d("FORM_DATA 格式");
            HttpUtil.log_d_part_list(paramsList);
            HttpUtil.log_d_part_list(files);
//            if (hasData) {
            body = builder.build();
//            }
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            if (objectParams == null) {
                for (Part part : paramsList) {
                    String key = part.getKey();
                    String value = part.getValue();
                    builder.add(key, value);
                }
                HttpUtil.log_d_part_list(paramsList);
            } else {
                formatPostForm(builder, objectParams);
            }
            HttpUtil.log_d("KEY_VALUE 格式");
            body = builder.build();
        }
        return body;
    }

    private static void formatPostForm(FormBody.Builder builder, Object obj) {
        if (obj == null) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String varName = fields[i].getName();
            try {
                boolean accessFlag = fields[i].isAccessible();
                fields[i].setAccessible(true);
                Object o = fields[i].get(obj);
                if (o == null) continue;
                builder.add(varName, o == null ? "" : o.toString());
                fields[i].setAccessible(accessFlag);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        HttpUtil.log_d(HttpUtil.gson.toJson(obj));
    }

    private String getParamsJsonString() {
        JSONObject jsonObject = new JSONObject();
        if (paramsList == null || paramsList.size() == 0) {
            return jsonObject.toString();
        }
        try {
            for (Part part : paramsList) {
                jsonObject.put(part.getKey(), part.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();// TODO: 2018/5/16   测试 直接tostring 有没有问题
    }
    // TODO: 2018/7/17  直接传list对象的解析

    //==================================header====================================
   /* private void addHeader(String line) {
        headersBuilder.add(line);
    }*/

    public void addHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
//            if (TextUtils.isEmpty(headersBuilder.get(key))) {
//                headersBuilder.add(key, value);
                headersBuilder.set(key, value);
//            }
        }
    }
}
