package com.example.poem5_12_25;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 全局配置管理
 */
public class GlobalConfig {
    private static final String TAG = "ConfigManager";

    private Context context;

    private static final Executor executor = Executors.newFixedThreadPool(3);

    public static Executor getExecutor(){
        return executor;
    }

    public static void submitTask(Runnable runnable){
        executor.execute(runnable);
    }



    public GlobalConfig(Context context) {
        this.context = context;
    }

    public String getHost() {
        return context.getString(R.string.host);
    }

    public int getVersionCode() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return 0;
        }
    }


    public String getVersionName() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return "「获取版本号出错」";
        }
    }

}
