package com.gather.land.reposetories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gather.land.data.ICommentDao;
import com.gather.land.data.IGameRequestPostDao;
import com.gather.land.data.IStandardPostDao;
import com.gather.land.data.RoomDatabaseSource;
import com.gather.land.models.Comment;
import com.gather.land.models.GameRequestsPost;
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

    public void insertNewStandardPost(StandardPost standardPosts) {
        standardPostDao.insertNewStandardPost(standardPosts);
    }

    public LiveData<List<StandardPost>> getAllPostFeedLiveData() {
        return standardPostDao.getAllPostsList();
    }
    public LiveData<List<StandardPost>> getAllMyPosts(String userEmail) {
        return standardPostDao.getAllPostsByUser(userEmail);
    }
    public LiveData<List<Comment>> getAllMyComments(String userEmail){
        return commentDao.getAllCommentsByUser(userEmail);
    }
    public LiveData<List<GameRequestsPost>> getAllMyPostsGameRequest(String userEmail) {
        return gameRequestPostDao.getAllMyPosts(userEmail);
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
    public void insertNewComments(Comment comment) {
        commentDao.insertNewComment(comment);
    }

    public void deleteComment(Comment comment) {
        commentDao.deleteComment(comment.getKey());
    }

    public void updateComment(Comment comment) {
        commentDao.updateComment(comment.getPostKey(),comment.getTimeStampCreated(),comment.getBody());
    }

    public void deletePost(StandardPost post) {
      int row=  standardPostDao.deletePost(post.getKey());
        Log.d("WOOOWW",row+"");
    }

    public void deletePostComments(StandardPost post) {
        commentDao.deleteAllCommentsPost(post.getKey());
    }
}
