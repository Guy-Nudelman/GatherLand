package com.gather.land.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gather.land.models.Comment;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;

import java.util.List;

@Dao
public interface IStandardPostDao {
    @Query("select * from standardPost where `userKey`=:userKey order by timeStampCreated desc")
    LiveData<List<StandardPost>> getAllPostsByUser(String userKey);

    @Query("select * from standardPost where `gameTag`=:gameTag order by timeStampCreated desc")
    LiveData<List<StandardPost>> getAllPostsByGameTag(String gameTag);

    @Query("select * from standardPost order by timeStampCreated desc")
    LiveData<List<StandardPost>> getAllPostsList();


    //insert image to room with blob// upgrade new database version

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewStandardPost(List<StandardPost> postList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewStandardPost(StandardPost post);

    @Query("UPDATE standardPost SET body = :body, timeStampCreated = :timeStampCreated, title = :title WHERE `key` = :postKey")
    void updatePost(
            String postKey,
            long timeStampCreated,
            String body,
            String title
    );


    @Query("DELETE from standardPost")
    void deleteAll();

    @Query("delete from standardPost where `key`=:postKey")
    int deletePost(String postKey);
}
