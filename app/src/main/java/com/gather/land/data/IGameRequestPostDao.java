package com.gather.land.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gather.land.models.GameRequestsPost;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;

import java.util.List;

@Dao
public interface IGameRequestPostDao {

    @Query("select * from gameRequestsPost where `userKey`=:userKey order by timeStampCreated desc")
    LiveData<List<GameRequestsPost>> getAllMyPosts(String userKey);

    @Query("select * from gameRequestsPost where `gameTag`=:gameTag order by timeStampCreated desc")
    LiveData<List<GameRequestsPost>> getAllPostsByGameTag(String gameTag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewGameRequestPost(GameRequestsPost post);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewGameRequestPost(List<GameRequestsPost> gameRequestsPostList);

    @Query("delete from gameRequestsPost where `key`= :key ")
    void deleteGameRequest(String key);


    @Query("UPDATE gameRequestsPost SET timeStampExpired = :timeStampExpired, timeStampCreated = :timeStampCreated, title = :title, usersMissing = :usersMissing WHERE `key` = :postKey")
    void updateGameRequest(
            String postKey,
            long timeStampCreated,
            String title,
            long timeStampExpired,
            int usersMissing);


    @Query("DELETE from gameRequestsPost")
    void deleteAll();
}
