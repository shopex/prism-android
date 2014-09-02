
package com.shopex.android.prism.utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


public class DeviceUtil {

    /**
     * 获取设备MAC Permission: android.permission.ACCESS_WIFI_STATE
     * 
     * @param context
     * @return
     */
    public static String getMAC(Context context) {
        WifiManager wManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wManager.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取设备ID
     * 
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 获取当前的imei, 可能为空 Permission: android.permission.READ_PHONE_STATE
     */
    public static String getImei(Context context) {
        TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tManager.getDeviceId();
        return imei;
    }

    /**
     * 获取当前的imsi, 可能为空 Permission: android.permission.READ_PHONE_STATE
     */
    public static String getImsi(Context context) {
        TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tManager.getSubscriberId();
        return imsi;
    }

    /**
     * 是否是电信卡 Permission: android.permission.READ_PHONE_STATE
     * 
     * @param context
     * @return
     */
    public static boolean isCTC(Context context) {
        String imsi = getImei(context);
        return isCTC(imsi);
    }

    /**
     * 是否是电信卡 Permission: android.permission.READ_PHONE_STATE
     * 
     * @param imsi
     * @return
     */
    public static boolean isCTC(String imsi) {
        return StringUtil.isNotBlank(imsi) && imsi.startsWith("46003");
    }

    /**
     * 是否是移动卡 Permission: android.permission.READ_PHONE_STATE
     * 
     * @param context
     * @return
     */
    public static boolean isCMCC(Context context) {
        String imsi = getImei(context);
        return isCMCC(imsi);
    }

    /**
     * @param imsi
     * @return
     */
    public static boolean isCMCC(String imsi) {
        return StringUtil.isNotBlank(imsi)
                && (imsi.startsWith("46000")
                || imsi.startsWith("46002"));
    }

    /**
     * 是否是联通卡 Permission: android.permission.READ_PHONE_STATE
     * 
     * @param context
     * @return
     */
    public static boolean isCUC(Context context) {
        String imsi = getImei(context);
        return isCUC(imsi);
    }

    /**
     * @param imsi
     * @return
     */
    public static boolean isCUC(String imsi) {
        return StringUtil.isNotBlank(imsi) && imsi.startsWith("46001");
    }

    /**
     * 判断是否是平板
     * 
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK)
        >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取手机屏幕设备相关的信息
     * 
     * @param context
     * @return
     */
    public static Device getDevice(Context context) {
        return new Device(context);
    }

    /**
     * 手机屏幕设备的相关数据，包括宽、高、密度、dpi、状态栏高度
     * 
     * @author dell
     */
    public static class Device {
        private Context mContext;
        private DisplayMetrics mDm;

        public Device(Context context) {
            if (context != null) {
                mContext = context;
                mDm = context.getResources().getDisplayMetrics();
            }
        }

        /**
         * 获取屏幕的宽度
         * 
         * @param context
         * @return
         */
        public int getWidth() {
            return mDm != null ? mDm.widthPixels : 0;
        }

        /**
         * 获取屏幕的高度
         * 
         * @param context
         * @return
         */
        public int getHeight() {
            return mDm != null ? mDm.heightPixels : 0;
        }

        /**
         * 获取屏幕的密度
         * 
         * @param context
         * @return
         */
        public float getDensity() {
            return mDm != null ? mDm.density : 0;
        }

        /**
         * 获取屏幕的dpi
         * 
         * @param context
         * @return
         */
        public float getDensityDpi() {
            return mDm != null ? mDm.densityDpi : 0;
        }

        /**
         * 获取状态栏的高度，系统默认高度为25dp
         * 
         * @param context
         * @return
         */
        public int getStatusBarHeight() {
            if (mContext == null)
                return 0;
            int height = (int) (25 * getDensity());

            try {
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
                Object obj = cls.newInstance();
                Field field = cls.getField("status_bar_height");
                height = mContext.getResources().getDimensionPixelSize(
                        Integer.parseInt(field.get(obj).toString()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return height;
        }
    }

}
