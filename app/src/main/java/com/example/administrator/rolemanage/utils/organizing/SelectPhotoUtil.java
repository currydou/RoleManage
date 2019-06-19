package com.example.administrator.rolemanage.utils.organizing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.administrator.rolemanage.utils.organized.FileProvider7;

import java.io.File;

/**
 * Created by curry on 2017/12/17.
 */

public class SelectPhotoUtil {

    public static final int CODE_OCR_ALBUM = 0x12;


    public SelectPhotoUtil() {
    }

    public static Intent combineCameraIntent(Intent intent, File tempImgFile, Context context) {
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(context, tempImgFile));
        return intent;
    }

    public static Intent combineAlbumIntent(Intent intent) {
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    // TODO2: 2018/8/21  设置参数是否裁剪
    public Intent goCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
//                mIntent.putExtra("outputX", 300);
//                mIntent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        return intent;
    }

}
