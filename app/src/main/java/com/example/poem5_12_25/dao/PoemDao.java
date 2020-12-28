package com.example.poem5_12_25.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.poem5_12_25.entity.Poem;

import java.util.List;

/**
 * @Classname PoemDao
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 14:28
 * @Version 1.0
 */
@Dao
public interface PoemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertPoem(Poem poem);

    @Update
    public int updatePoem(Poem poem);

    @Query("select * from poem")
    public List<Poem> selectAllPoem();

    @Query("select * from Poem where id = :id")
    public Poem selectPoemById(long id);

    @Delete
    public int deletePoem(Poem poem);

    @Query("delete from Poem")
    public int deleteAllPoem();
}
