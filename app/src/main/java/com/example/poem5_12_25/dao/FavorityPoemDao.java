package com.example.poem5_12_25.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.poem5_12_25.entity.FavorityPoem;
import com.example.poem5_12_25.entity.Poem;

import java.util.List;

/**
 * @Classname FavorityPoemDao
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 14:36
 * @Version 1.0
 */
@Dao
public interface FavorityPoemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertFavorityPoem(FavorityPoem favorityPoem);

    @Delete
    public int deleteFavorityPoem(FavorityPoem poem);

    @Query("select * from favoritypoem where uid = :uid")
    public List<FavorityPoem> selectAllFavorityPoemByUid(long uid);

}
