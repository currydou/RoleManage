/**
 * Copyright © All right reserved by IZHUO.NET.
 */
package com.example.administrator.rolemanage.utils.organized;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;

/**
 * @author Box
 * @version v1.0
 * @date 2015年10月15日 上午11:51:36
 * @Description 根据uri获取文件路径
 * @最后修改日期 2015年10月15日 上午11:51:36
 * @修改人 Box
 * @since Jdk1.6 或 Jdk1.7
 */
@SuppressWarnings({"JavaDoc", "unused"})
public class FileUtils {

    public static boolean isExistSD() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getSDPath() {
        File sdDir;
        if (FileUtils.isExistSD()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        } else {
            sdDir = Environment.getRootDirectory();
        }
        return sdDir.toString();
    }


    /*--------------------------------------------------------------------*/

    public static File getFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdir();
        }
        return new File(filePath, fileName);
    }

    public static boolean saveFile(File file, String filePath, String fileName) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            InputStream is;
            OutputStream os;
            try {
                File toFile = new File(filePath);
                if (!toFile.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    toFile.mkdir();
                }
                is = new FileInputStream(file);
                os = new FileOutputStream(filePath + "/" + fileName, true);
                byte[] buffer = new byte[8192];
                int count;
                while ((count = is.read(buffer)) >= 0) {
                    os.write(buffer, 0, count);
                }
                os.close();
                is.close();
                return true;
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public static String getUrlWithQueryString(boolean shouldEncodeUrl, String url) {
        if (url == null) {
            return null;
        } else {
            String paramString;
            if (shouldEncodeUrl) {
                try {
                    paramString =url ;//URLDecoder.decode(url, "UTF-8");//OSS url不解码 2017-05-11
                    URL _url = new URL(paramString);
                    URI _uri = new URI(_url.getProtocol(), _url.getUserInfo(), _url.getHost(), _url.getPort(), _url.getPath(), _url.getQuery(), _url.getRef());
                    url = _uri.toASCIIString();
                } catch (Exception var6) {
                    var6.printStackTrace();
                }
            }

            return url;
        }
    }

    public static long getFileSize(String urlpath) {
        try {
            URL u = new URL(getUrlWithQueryString(true, urlpath));
            HttpURLConnection urlcon = (HttpURLConnection) u.openConnection();
            urlcon.setConnectTimeout(5000);
            urlcon.setRequestMethod("GET");
            urlcon.setRequestProperty("Accept-Encoding", "identity");
            urlcon.setRequestProperty("Referer", urlpath);
            urlcon.setRequestProperty("Charset", "UTF-8");
            urlcon.setRequestProperty("Connection", "Keep-Alive");
            urlcon.connect();
            return urlcon.getContentLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getFileType(String path) {
        int lastDot = path.lastIndexOf(".");
        if (lastDot < 0) {
            return null;
        }
        return path.substring(lastDot + 1).toUpperCase(Locale.getDefault());
    }

    public static String getFileName(String path) {
        int lastIndexOf = path.lastIndexOf("/");
        if (lastIndexOf < 0) {
            return null;
        }
        return path.substring(lastIndexOf + 1);
    }

    public static boolean cleanCaches(Context context) {
        return cleanFiles(context) && cleanInternalCache(context);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static boolean cleanFiles(Context context) {
        return deleteDir(context.getFilesDir());
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static boolean cleanInternalCache(Context context) {
        return deleteDir(context.getCacheDir());
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                //noinspection ResultOfMethodCallIgnored
                item.delete();
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir != null && dir.delete();
    }

    public static String getName(String path) {
        int separatorIndex = path.lastIndexOf(File.separator);
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

}
