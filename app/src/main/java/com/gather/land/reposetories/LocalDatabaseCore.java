package com.gather.land.reposetories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.gather.land.data.ICommentDao;
import com.gather.land.data.IGameRequestPostDao;
import com.gather.land.data.IStandardPostDao;
import com.gather.land.data.RoomDatabaseSource;
import com.gather.land.models.Comment;
import com.gather.land.models.StandardPost;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseCore {

    private ICommentDao commentDao;
    private IStandardPostDao standardPostDao;
    private IGameRequestPostDao gameRequestPostDao;

    public LocalDatabaseCore(Context context) {
        RoomDatabaseSource databaseSource = RoomDatabaseSource.getInstance(context);
        commentDao = databaseSource.getCommentDao();
        standardPostDao = databaseSource.getStandardPostDao();
        gameRequestPostDao = databaseSource.geIGameRequestPostDao();
    }


    public void insertStandardPost(ArrayList<StandardPost> standardPosts) {
        standardPostDao.insertNewStandardPost(standardPosts);
    }

    public LiveData<List<StandardPost>> getAllPostFeedLiveData() {
        return standardPostDao.getAllPostsList();
    }

    public void signOut() {
        commentDao.deleteAll();
        standardPostDao.deleteAll();
        gameRequestPostDao.deleteAll();
    }

    public LiveData<List<Comment>> getAllCommentsToPostLiveData(String postKey) {
        return commentDao.getAllCommentByPost(postKey);
    }

    public void insertNewCommentsLis(List<Comment> commentArrayList) {
        commentDao.insertNewComments(commentArrayList);
    }
}
