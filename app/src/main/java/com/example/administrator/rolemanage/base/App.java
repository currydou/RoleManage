package com.example.administrator.rolemanage.base;


import com.example.administrator.rolemanage.utils.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by lib on 2017/7/25.
 */

public interface App {
    RxErrorHandler getRxErrorHandler();

    ImageLoader getImageLoader();
}
