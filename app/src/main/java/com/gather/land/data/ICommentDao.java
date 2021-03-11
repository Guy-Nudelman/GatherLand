package com.gather.land.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gather.land.models.Comment;
import com.gather.land.models.StandardPost;

import java.util.List;

@Dao
public interface ICommentDao {

    @Query("select * from Comment where postKey= :postKey order by timeStampCreated desc")
    LiveData<List<Comment>> getAllCommentByPost(String postKey);

    @Query("select * from Comment where `userKey`=:userKey order by timeStampCreated desc")
    LiveData<List<Comment>> getAllCommentsByUser(String userKey);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewComment(Comment comment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewComments(List<Comment> comments);

    @Query("delete from Comment where `key`= :keyToDelete ")
    void deleteComment(String keyToDelete);

    @Query("delete from Comment where postKey= :postKey ")
    void deleteAllCommentsPost(String postKey);

    @Query("UPDATE Comment SET body = :body, timeStampCreated = :timeStampCreated WHERE postKey = :postKey")
    void updateComment(
            String postKey,
            long timeStampCreated,
            String body
            );

    @Query("delete from Comment")
    void deleteAll();
}
