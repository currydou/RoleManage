package com.example.administrator.rolemanage.utils.organized;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import com.example.administrator.rolemanage.constant.GlobalConstantConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureUtil {
    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    // 将压缩的bitmap保存到sdcard卡临时文件夹img_interim，用于上传
    public static File saveMyBitmap(String filename, byte[] bytes)
            throws IOException, IllegalArgumentException {
        if (bytes == null)
            throw new IllegalArgumentException();

        File f = new File(GlobalConstantConfig.getCacheDir(), filename);
        if (f.isFile()) {
            f.delete();
        }

        f.createNewFile();

        FileOutputStream fos;

        fos = new FileOutputStream(f);
        fos.write(bytes);
        fos.flush();
        fos.close();

        return f;

    }


    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     */
    public static void galleryAddPic(Context context, String path) {
        File f = new File(path);

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, f.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取保存图片的目录 （SD卡目录）
     *
     * @return
     */
    public static File getAlbumDir() {
        File dir = new File(Environment.getExternalStorageDirectory(), getAlbumName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public static String getAlbumName() {
//        return FeiKongBaoApplication.instance.getString(R.string.cache_dir);
        return GlobalConstantConfig.CACHE_FOLDER;
    }

    /**
     * 根据图片的bitmap对象进行压缩处理
     *
     * @param image
     * @return
     */
    public static byte[] comp(Bitmap image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        } catch (Exception e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// 这里压缩50%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        }

    }

    /**
     * lb 压缩方法
     *
     * @param bitmap
     * @return
     */
    /**
     * 根据图片的bitmap对象进行压缩处理
     *
     * @param image
     * @return
     */
    public static byte[] compressImage(Bitmap image) {
        return compressImage(image, 480, 800, 85);
    }

    public static byte[] compressImage(Bitmap image, float targetw, float targeth, int compRatio) {
        try {
            File tmpFile = new File(GlobalConstantConfig.getCacheDir(), "" + System.currentTimeMillis() + ".tmp");
            FileOutputStream fos = new FileOutputStream(tmpFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            byte[] b = getScaledImageDataFromFile(tmpFile.getAbsolutePath(), targetw, targeth, compRatio);
            tmpFile.delete();
            return b;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static byte[] getScaledImageDataFromFile(String srcPath, float targetw, float targeth, int compRatio) {
        try {
            if (srcPath == null)
                return null;

            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(srcPath, newOpts);

            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (newOpts.outWidth > newOpts.outHeight) {
                if (newOpts.outWidth > targetw) {
                    be = (int) (newOpts.outWidth / targetw);
                }
            } else {
                if (newOpts.outHeight > targeth) {
                    be = (int) (newOpts.outHeight / targeth);
                }
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            newOpts.inJustDecodeBounds = false;
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            Bitmap image = BitmapFactory.decodeFile(srcPath, newOpts);
            if (image == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, compRatio, baos);
            image.recycle();
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }

    }

    public static Bitmap strToBitmap(String str) { //可能包含前缀信息：data:image/jpeg;base64,
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.contains(",")) {
            str = str.substring(str.indexOf(",") + 1);
        }
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将图片的旋转角度置为0
     *
     * @param path
     * @return void
     * @Title: setPictureDegreeZero
     * @date 2012-12-10 上午10:54:46
     */
    public static void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            double width = exifInterface.getAttributeDouble(ExifInterface.TAG_IMAGE_WIDTH, 0);
            double height = exifInterface.getAttributeDouble(ExifInterface.TAG_IMAGE_LENGTH, 0);
            if (width > height) {
                //修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
                //例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
                exifInterface.saveAttributes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于ocr识别压缩（样率压缩）
     *  压缩方式：原图片的宽高的短边>1200  --》压缩
     * @param filePath  图片路径
     * @return 返回压缩后的路径
     */
    public static Bitmap compressScale(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 1200);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * @description 计算图片的压缩比率
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int target) {
        // 源图片的高度和宽度
        int sourceHeight = options.outHeight;
        int sourceWidth = options.outWidth;
        //短边长度
        int minSourceEdge = Math.min(sourceHeight,sourceWidth);
        int inSampleSize = 1;
        if (minSourceEdge > target) {
            inSampleSize = minSourceEdge / target;
        }
        return inSampleSize;
    }

}
