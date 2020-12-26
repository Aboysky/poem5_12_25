package com.example.poem5_12_25.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.poem5_12_25.dao.FavorityPoemDao;
import com.example.poem5_12_25.dao.PoemDao;
import com.example.poem5_12_25.dao.UserDao;
import com.example.poem5_12_25.entity.FavorityPoem;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.entity.User;

/**
 * 测试数据库一
 * @Classname database1
 * @Description TODO
 * @Date 2020/12/23 12:41
 * @Created by Huan
 */
@Database(version = 1,entities = {Poem.class, FavorityPoem.class, User.class},exportSchema = false)
public abstract class Database1 extends RoomDatabase {

    private static Database1 instance;
    private Context context;

    public abstract PoemDao PoemDao();
    public abstract FavorityPoemDao FavorityPoemDao();
    public abstract UserDao UserDao();

    public Database1(Context context) {
        this.context = context;
    }
    public static Database1 getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context, Database1.class,"test_user").allowMainThreadQueries().build();
            return instance;
        }
        return instance;
    }

    public Database1() {
    }
}
