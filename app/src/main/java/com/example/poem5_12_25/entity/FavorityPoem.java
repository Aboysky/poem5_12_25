package com.example.poem5_12_25.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Classname FavorityPoem
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 14:35
 * @Version 1.0
 */
@Entity
public class FavorityPoem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    /**
     * 喜欢的诗的id
     */
    private long pid;

    /**
     * 用户id
     */
    private long uid;

    public FavorityPoem() {
    }

    @Ignore
    public FavorityPoem(long pid, long uid) {
        this.pid = pid;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "FavorityPoem{" +
                "id=" + id +
                ", pid=" + pid +
                ", uid=" + uid +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
