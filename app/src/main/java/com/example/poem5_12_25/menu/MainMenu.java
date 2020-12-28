package com.example.poem5_12_25.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.poem5_12_25.MainApp;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.activity.FavoriteActivity;
import com.example.poem5_12_25.activity.SettingActivity;
import com.example.poem5_12_25.activity.UserActivity;


public class MainMenu {
    private static final String TAG = "MainMenu";
    private Context mContext;

    public MainMenu(Context context) {
        this.mContext = context;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                mContext.startActivity(new Intent(mContext, UserActivity.class));
                return true;
            // 我的收藏
            case R.id.menu_favorite:
                mContext.startActivity(new Intent(mContext, FavoriteActivity.class));
                return true;
            // 夜间模式
            case R.id.menu_night_mode:
                MainApp.getInstance().setNightMode(true);
                ((AppCompatActivity) mContext).recreate();
                return true;
            // 白天模式
            case R.id.menu_daylight_mode:
                MainApp.getInstance().setNightMode(false);
                ((AppCompatActivity) mContext).recreate();
                return true;
            case R.id.menu_system_setting:
                mContext.startActivity(new Intent(mContext,SettingActivity.class));
                return true;

            // 「关于」菜单
            case R.id.menu_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(String.format("关于「%s」", mContext.getString(R.string.app_name)));

                builder.setMessage(String.format("\n        简单 是一种生活的态度\n           而诗 是一种有态度的生活!\n\n\n版本号：%s\n Designed by huan \n © 2020 Aboysky. All Rights Reserved.",mContext.getResources().getString(R.string.version)));
                builder.setPositiveButton("我知道了", (dialog, which) -> {

                });
                AlertDialog dialog = builder.create();

                // 适配夜间模式和白天模式
                if (MainApp.getInstance().getNightMode())
                    dialog.setOnShowListener(dialog1 -> {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    });
                else
                    dialog.setOnShowListener(dialogInterface -> {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.black));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    });
                dialog.show();
                return true;
            default:
                return false;
        }
    }
}