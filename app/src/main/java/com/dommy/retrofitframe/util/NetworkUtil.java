package com.dommy.retrofitframe.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.dommy.retrofitframe.R;

/**
 * 手机网络监测工具
 */
public class NetworkUtil {

    /**
     * 检测网络连接状态
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 网络是否未连接
     * <p>未连接时给出提示</p>
     *
     * @param context
     * @return boolean 是否连接网络
     */
    public static boolean isNetDisconnected(Context context) {
        if (!isNetworkConnected(context)) {
            Toast.makeText(context, R.string.net_not_connected, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否连接在wifi下
     *
     * @param context
     * @return
     */
    public static boolean isConnectedByWifi(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.getType() == ConnectivityManager.TYPE_WIFI ? true : false;
        }
        return false;
    }
}
