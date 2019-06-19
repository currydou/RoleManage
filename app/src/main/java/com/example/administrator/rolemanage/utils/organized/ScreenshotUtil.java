package com.example.administrator.rolemanage.utils.organized;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

public class ScreenshotUtil {

    public static Bitmap getScreenShot(Activity activity) {
        View viewScreen = activity.getWindow().getDecorView();
        viewScreen.setDrawingCacheEnabled(true);
        viewScreen.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(viewScreen.getDrawingCache(), 0, 0, DensityUtil.getScreenWidth(activity), DensityUtil.getScreenHeight(activity));
        viewScreen.destroyDrawingCache();
        return bitmap;
    }

    public static Bitmap getViewScreenShot(View viewScreen) {
        viewScreen.setDrawingCacheEnabled(true);
        viewScreen.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(viewScreen.getDrawingCache(), 0, 0,viewScreen.getMeasuredWidth(), viewScreen.getMeasuredHeight());
        viewScreen.destroyDrawingCache();
        return bitmap;
    }

}
