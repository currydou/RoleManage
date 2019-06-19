package com.example.administrator.rolemanage.okhttp.V2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.yodoo.atinvoice.constant.Constant.URL_UPLOAD_OCR_PIC;

/**
 * 1、支持 JSON、KEY_VALUE、FORM_DATA 格式，默认JSON ；
 * JSON、 KEY_VALUE 格式可以直接传model类，或者一个一个参数添加。(优先用model类里的参数,不可同时使用)
 * FORM_DATA 格式可以在上传文件的同时，添加参数
 * <p>
 * 2、可以动态修改Header ,HttpUtil可以统一添加固定header
 *
 * @see HttpUtil
 * 3、动态添加tag，可以用于取消请求
 * Created by next on 2018/6/8.
 */

public class Demo {

    public Demo() {
        RequestParams requestParams = new RequestParams();

        // JSON
        requestParams.setMediaType(HttpUtil.JSON);
        requestParams.addParams(new Demo());
        requestParams.addHeader("token", "033517cd1fa24ae29a205b42bd2dd879");
//        ApiManager.addQuickAccount(requestParams, new BaseCallback());

        // KEY_VALUE
        requestParams.setMediaType(HttpUtil.KEY_VALUE);
        requestParams.addParams("imageETag", "12345");
//        ApiManager.addQuickAccount(requestParams, new BaseCallback());

        //FORM_DATA
        requestParams.setMediaType(HttpUtil.FORM_DATA);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(""));
        requestParams.addParams("file", fileList);
        requestParams.setRequestTag(URL_UPLOAD_OCR_PIC);
        requestParams.addParams("imageETag", "12345");
//        ApiManager.addQuickAccount(requestParams, new BaseCallback());

        //传list，["406","406"]   。直接传list对象
        requestParams.addParams(new ArrayList<>());
//        ApiManager.addQuickAccount(requestParams, new BaseCallback());

        //包含list，{"uIds":["406","406"],"token":"4f861e70ce674dd9964c39f623a56366"}。 将list放在对象A里，直接传对象A
        ArrayList<String> list = new ArrayList<>();
        Demo demo = new Demo();
        demo.setList(list);
        requestParams.addParams(demo);
//        ApiManager.addQuickAccount(requestParams, new BaseCallback());

    }


    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
