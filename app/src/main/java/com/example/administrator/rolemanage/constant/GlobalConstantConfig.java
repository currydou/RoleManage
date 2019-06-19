package com.example.administrator.rolemanage.constant;

import android.text.TextUtils;

import com.example.administrator.rolemanage.utils.organized.FileUtils;

import java.io.File;

public class GlobalConstantConfig {

    public static final String CACHE_FOLDER = "wbz_cache";
    public static final String PDF_FOLDER_NAME = "/pdf";
    public static final String PDF_PHOTO_NAME = "/photo";
    public static final String TEMP_HEAD_NAME = "temp_head.png";




    //url为空时，占位图不显示
    public static String getUrlNotEmpty(String url) {
        if (TextUtils.isEmpty(url)) {
            return "1";
        } else {
            return url;
        }
    }


    /**
     * 获取保存图片的目录 （SD卡目录）
     *
     * @return
     */
    public static File getCacheDir() {
        File dir = new File(FileUtils.getSDPath(), CACHE_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getPdfCacheDir() {
        File dir = new File(FileUtils.getSDPath(), CACHE_FOLDER + PDF_FOLDER_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getPhotoCacheDir() {
        File dir = new File(FileUtils.getSDPath(), CACHE_FOLDER + PDF_PHOTO_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}
