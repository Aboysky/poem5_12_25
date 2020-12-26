package com.example.poem5_12_25.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Classname User
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 18:22
 * @Version 1.0
 */
@Entity
public class User {

    /**
     * 注意: 这个id和服务器存储的id并不相同
     */
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User() {
    }

    @Ignore
    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
