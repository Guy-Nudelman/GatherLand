package com.gather.land.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gather.land.models.Comment;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;

import java.util.List;

@Dao
public interface IStandardPostDao {
    @Query("select * from standardPost where `userKey`=:userKey")
    LiveData<List<StandardPost>> getAllPostsByUser(String userKey);

    @Query("select * from standardPost where `gameTag`=:gameTag")
    LiveData<List<StandardPost>> getAllPostsByGameTag(String gameTag);


    @Insert()
    void insertNewStandardPost(StandardPost post);

}
