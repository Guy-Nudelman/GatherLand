package com.gather.land.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gather.land.models.Comment;

import java.util.List;

@Dao
public interface ICommentDao {


    @Query("select * from Comment where `postKey`=:postKey")
    LiveData<List<Comment>> getAllCommentByPost(String postKey);

    @Insert()
    void insertNewComment(Comment comment);

}
