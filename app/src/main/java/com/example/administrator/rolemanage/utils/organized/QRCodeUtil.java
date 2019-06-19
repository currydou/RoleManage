/**
 * Copyright © All right reserved by IZHUO.NET.
 */
package com.example.administrator.rolemanage.utils.organized;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Changlei
 *
 *         2015年5月23日
 */
public class QRCodeUtil {

	public static final int QR_WIDTH = 600;
	public static final int QR_HEIGHT = 600;
	public static final int QR_BORDER_WIDTH = 10;
	public static final String CHARSET_NAME = "utf-8";

	/**
	 * 图片宽度的一半
	 */
	private static final int IMAGE_HALFWIDTH = 20;

	public static Bitmap createQRCode(final String content) {
		try {
			// 判断URL合法性
			if (TextUtils.isEmpty(content)) {
				return null;
			}
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, CHARSET_NAME);
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			// 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			bitMatrix = updateBit(bitMatrix, QR_BORDER_WIDTH);

            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] data = new int[w * h];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;
                    else
                        data[y * w + x] = 0xffffffff;
                }
            }

            // 创建一张bitmap图片，采用最高的效果显示
            final Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            // 将上面的二维码颜色数组传入，生成图片颜色
            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static Bitmap createQRCode(final String content, Bitmap avatar) {
		return createQRCodeBitmapWithPortrait(createQRCode(content), avatar);
	}

	private static BitMatrix updateBit(BitMatrix matrix, int margin) {
		int tempM = margin * 2;
		int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
		resMatrix.clear();
		for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
			for (int j = margin; j < resHeight - margin; j++) {
				if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
					resMatrix.set(i, j);
				}
			}
		}
		return resMatrix;
	}

    /**
     * 在二维码上绘制头像
     */
    private static Bitmap createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {

        // 头像图片的大小
        int portrait_W = 160;
        int portrait_H = 160;
        Bitmap bitmapLogo = resizeImage(portrait,portrait_W,portrait_H);
        int bitmapW = qr.getWidth();
        int bitmapH =qr.getHeight();

        // 设置头像要显示的位置，即居中显示
        //int left = (QR_WIDTH - bitmapW) / 2;
        //int top = (QR_HEIGHT - bitmapH) / 2;
        int left = (bitmapW - portrait_W) / 2;
        int top = (bitmapH - portrait_H) / 2;
        int right = left + portrait_W;
        int bottom = top + portrait_H;
        Rect rect1 = new Rect(left, top, right, bottom);

        // 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
        Canvas canvas = new Canvas(qr);
        // 设置我们要绘制的范围大小，也就是头像的大小范围
        Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
        // 开始绘制
        canvas.drawBitmap(bitmapLogo, rect2, rect1, null);
        return qr;
    }

	/**
	 * 生成二维码的方法
	 * 
	 * @param str
	 * @return
	 */
	public static Bitmap createBitmap(String str, Bitmap bitmapAvatar) {
		// 缩放图片
		Matrix matrixAvatar = new Matrix();
		float sx = (float) 2 * IMAGE_HALFWIDTH / bitmapAvatar.getWidth();
		float sy = (float) 2 * IMAGE_HALFWIDTH / bitmapAvatar.getHeight();
		matrixAvatar.setScale(sx, sy);
		// 重新构造一个40*40的图片
		bitmapAvatar = Bitmap.createBitmap(bitmapAvatar, 0, 0,
				bitmapAvatar.getWidth(), bitmapAvatar.getHeight(),
				matrixAvatar, false);
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败

		try {
			str = new String(str.getBytes(), "ISO-8859-1");
			BitMatrix matrix = new MultiFormatWriter().encode(str,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
			int width = matrix.getWidth();
			int height = matrix.getHeight();
			// 二维矩阵转为一维像素数组,也就是一直横着排了
			int halfW = width / 2;
			int halfH = height / 2;
			int[] pixels = new int[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x > halfW - IMAGE_HALFWIDTH
							&& x < halfW + IMAGE_HALFWIDTH
							&& y > halfH - IMAGE_HALFWIDTH
							&& y < halfH + IMAGE_HALFWIDTH) {
						pixels[y * width + x] = bitmapAvatar.getPixel(x - halfW
								+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
					} else {
						if (matrix.get(x, y)) {
							pixels[y * width + x] = 0xff000000;
						}
					}

                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 生成二维码
     * @param string  二维码中包含的文本信息
     * @param mBitmap logo图片
     * @return Bitmap 位图
     * @throws WriterException
     */
    public static Bitmap createCode(String string, Bitmap mBitmap){
        try {
            Matrix m = new Matrix();
            float sx = (float) 2*IMAGE_HALFWIDTH / mBitmap.getWidth();
            float sy = (float) 2*IMAGE_HALFWIDTH/ mBitmap.getHeight();
            m.postScale(sx, sy);//设置缩放信息
            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), m, false);
            MultiFormatWriter writer = new MultiFormatWriter();
            Hashtable<EncodeHintType, Object> hst = new Hashtable<EncodeHintType, Object>();
            hst.put(EncodeHintType.CHARACTER_SET, CHARSET_NAME);//设置字符编码
            hst.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix matrix = writer.encode(string, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hst);//生成二维码矩阵信息
            matrix = updateBit(matrix, QR_BORDER_WIDTH);
            int width = matrix.getWidth();//矩阵高度
            int height = matrix.getHeight();//矩阵宽度
            int halfW = width / 2;
            int halfH = height / 2;
            int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
            for (int y = 0; y < height; y++) {//从行开始迭代矩阵
                for (int x = 0; x < width; x++) {//迭代列
                    if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                            && y > halfH - IMAGE_HALFWIDTH
                            && y < halfH + IMAGE_HALFWIDTH) {//该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                    } else {
                        if (matrix.get(x, y)) {//如果有黑块点，记录信息
                            pixels[y * width + x] = 0xff000000;//记录黑块信息
                        }else {
                            pixels[y * width + x] = 0xffffffff;
                        }

                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h){
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }

}
