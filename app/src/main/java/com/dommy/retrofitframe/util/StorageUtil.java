package com.dommy.retrofitframe.util;

import android.os.Environment;

import java.io.File;

/**
 * 存储器工具类
 */
public class StorageUtil {

    // app在外部存储器上的根目录
    private static final String PATH_APP = "com.dommy.retrofitframe/";

    private static String PATH_ROOT = Environment.getExternalStorageDirectory()
            .getPath() + "/" + PATH_APP;

    // 下载路径
    private static final String PATH_APP_DOWNLOAD = PATH_ROOT + "download/";

    /**
     * 建立app目录
     */
    static {
        String path = PATH_ROOT;
        File dir = new File(path);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
    }

    /**
     * 获取app下载存储路径
     *
     * @return
     */
    public static String getDownloadPath() {
        String path = PATH_APP_DOWNLOAD;
        File dir = new File(path);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        return path;
    }

}
