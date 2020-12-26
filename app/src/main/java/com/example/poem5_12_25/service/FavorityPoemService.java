package com.example.poem5_12_25.service;

import android.content.Context;

import com.example.poem5_12_25.dao.FavorityPoemDao;
import com.example.poem5_12_25.dao.PoemDao;
import com.example.poem5_12_25.dao.UserDao;
import com.example.poem5_12_25.database.Database1;
import com.example.poem5_12_25.entity.FavorityPoem;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname FavorityPoemDao
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 18:30
 * @Version 1.0
 */
public class FavorityPoemService {

    private Context context;

    public FavorityPoemService(Context context) {
        this.context = context;
    }

    /**
     * 拿到本机登录用户喜欢的所有诗
     * 默认本地数据库的User表中只保留用户自己的信息
     * @return
     */
    public ArrayList<Poem> selectUserAllFavorityPoem(){
        FavorityPoemDao favorityPoemDao = Database1.getInstance(context).FavorityPoemDao();
        UserDao userDao = Database1.getInstance(context).UserDao();
        PoemDao poemDao = Database1.getInstance(context).PoemDao();
        List<User> users = userDao.selectAllUser();
        ArrayList<Poem> arrayList = new ArrayList<>();
        if (!users.isEmpty()){
            User user = users.get(0);
            List<FavorityPoem> favorityPoems = favorityPoemDao.selectAllFavorityPoemByUid(user.getId());

            for (FavorityPoem favorityPoem : favorityPoems) {
                arrayList.add(poemDao.selectPoemById(favorityPoem.getPid()));
            }
        }
        return arrayList;
    }
}
