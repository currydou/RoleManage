package com.example.administrator.rolemanage.utils.organizing;

import android.graphics.Bitmap;

import com.example.administrator.rolemanage.utils.organized.BitMapUtil;
import com.example.administrator.rolemanage.utils.organized.PictureUtil;
import com.example.administrator.rolemanage.utils.organized.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * OCR识别图片特殊压缩处理
 */
public class YaSuo {

    public static File compressImage(File file) {
        String tmpPath = file.getPath() + "tmp";
        StringUtils.d("BitmapFactory.decodeStream样率压缩前的file大小", String.valueOf(BitMapUtil.getFileSize(file)));
        Bitmap bitmap = PictureUtil.compressScale(file.getPath());
        StringUtils.d("BitmapFactory.decodeStream样率压缩后的bitMap大小", String.valueOf(BitMapUtil.getBitmapSize(bitmap)));

        saveBitmap(file.getPath(), bitmap, 100);// 2019/2/26  100不压缩，测试大小


//        File tmpFile = saveBitmap(tmpPath, bitmap,99);
        File tmpFile = saveBitmap2(tmpPath, bitmap);


        if (tmpFile != null && tmpFile.exists() && tmpFile.length() > 0) {
            tmpFile.renameTo(file);
        }
        return file;
    }

    /**
     *
     * 质量压缩测试数据，不是按照压缩质量压缩的？
     *
     * quality 原图file大小 压缩后的file大小
     * 50	1860653		  74275
     * 80	1864156		 240802
     * 90	1842682		 494766
     * 95	1886467		 968907
     * 99	1936827		1753797
     * 100	18			18
     *
     * @param path
     * @param bitmap
     * @param quality
     * @return
     */
    public static File saveBitmap(String path, Bitmap bitmap, int quality) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            StringUtils.d("bitmap.compress质量压缩前的bitmap大小", String.valueOf(BitMapUtil.getBitmapSize(bitmap)));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            StringUtils.d("bitmap.compress质量压缩后的file大小", String.valueOf(BitMapUtil.getFileSize(f)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // 2019/2/27 0027  测试阿里云上传图片

    /**
     * 第二种每次压缩一点点，循环，
     * @param path
     * @param bitmap
     * @return
     */
    public static File saveBitmap2(String path, Bitmap bitmap) {//  2019/2/27 0027  大小可以后，看bitmap内存的问题
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 98;
        while (baos.size() / 1024 > 1024) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 1;// 每次都减少10
        }
        return streamWriteToFile(new File(path), baos);
    }

    public static File streamWriteToFile(File file, ByteArrayOutputStream stream) {
        byte[] bitmapData = stream.toByteArray();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return file;
    }

}
