package com.example.administrator.rolemanage.utils.imageloader;

import android.widget.ImageView;

/**
 * 图片加载配置信息的基类,可以定义一些所有图片加载框架都可以用的通用参数
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;
    protected int errorPic;


    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
