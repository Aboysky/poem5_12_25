package com.example.poem5_12_25;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

// 管理全局 Application 对象
public class MainApp extends Application {
    private static MainApp mApp;

    public static MainApp getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public void setNightMode(boolean isEnabled) {
        if (isEnabled)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public boolean getNightMode() {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        switch (nightMode) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                return true;
            case AppCompatDelegate.MODE_NIGHT_NO:
                return false;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                return false;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                return false;
            default:
                return false;
        }
    }

}
