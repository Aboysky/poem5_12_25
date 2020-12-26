package com.example.poem5_12_25.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.poem5_12_25.entity.User;

import java.util.List;

/**
 * @Classname UserDao
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 18:23
 * @Version 1.0
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertUser(User user);

    @Update
    public int updateUser(User newUser);

    @Query("select * from user")
    public List<User> selectAllUser();

    @Query("select * from User where id = :id")
    public User selectUserById(int id);

    @Query("select * from User where username = :username")
    public User selectUserByUsername(String username);

    @Query("delete from user where id = :id")
    public int DeleteUserById(long id);

    @Query("delete from user where username = :username")
    public int DeleteUserByUsername(String username);

    @Query("delete from user")
    public int deleteAllUser();


}
