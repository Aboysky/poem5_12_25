package com.example.poem5_12_25.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.poem5_12_25.MainApp;
import com.example.poem5_12_25.entity.Poem;


public class LastPoemCache {

    private LastPoemCache() {
    }

    /**
     * 获取缓存的上一首古诗，没有的话返回null
     * @return
     */
    public static Poem getLastPoem() {
        // 保存上一首诗词到文件中
        SharedPreferences preferences = MainApp.getInstance().getSharedPreferences("lastPoemCache", Context.MODE_PRIVATE);
        if (preferences.contains("data")) {
            return Poem.parseJsonString(preferences.getString("data", ""));
        }else
            return null;
    }

    /**
     * 将诗词保存到文件中持久化
     * @param poem
     */
    public static void setLastPoem(Poem poem) {
        SharedPreferences preferences = MainApp.getInstance().getSharedPreferences("lastPoemCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data", poem.toJsonString());
        editor.apply();
    }
}
