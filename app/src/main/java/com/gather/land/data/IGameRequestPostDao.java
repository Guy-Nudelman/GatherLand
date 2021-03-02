package com.gather.land.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gather.land.models.GameRequestsPost;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;

import java.util.List;

@Dao
public interface IGameRequestPostDao {
    @Query("select * from standardPost where `userKey`=:userKey")
    LiveData<List<GameRequestsPost>> getAllPostsByUser(String userKey);

    @Query("select * from standardPost where `gameTag`=:gameTag")
    LiveData<List<GameRequestsPost>> getAllPostsByGameTag(String gameTag);


    @Insert()
    void insertNewGameRequestPost(GameRequestsPost post);
}
