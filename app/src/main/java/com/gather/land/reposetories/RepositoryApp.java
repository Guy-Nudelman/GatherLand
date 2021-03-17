package com.gather.land.reposetories;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gather.land.enums.StorageFolder;
import com.gather.land.interfaces.DownloadImageCallback;
import com.gather.land.interfaces.UploadImageCallback;
import com.gather.land.models.Comment;
import com.gather.land.models.GameRequestsPost;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;
import com.gather.land.models.User;
import com.gather.land.utilities.ConverterImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.List;

public class RepositoryApp {

    private static RepositoryApp instance;
    private final NetworkCore networkCore;
    private final LocalDatabaseCore databaseCore;
    private final SharedPrefHelper prefHelper;
    private User myUser;


    private final ChildEventListener mCallbackNewPostFeed = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            StandardPost post = dataSnapshot.getValue(StandardPost.class);
            if (!databaseCore.isPostExist(post.getKey())){
                databaseCore.insertNewStandardPost(post);
            }

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            Log.d("WOOOWW", "remove");
            StandardPost post = dataSnapshot.getValue(StandardPost.class);
            databaseCore.deletePost(post);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefreshList();
                }
            }, 1000);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    private ChildEventListener mListenerCommentsToPost = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Comment comment = dataSnapshot.getValue(Comment.class);
            if (!databaseCore.isCommentExist(comment.getKey()))
                databaseCore.insertNewComments(comment);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Comment comment = dataSnapshot.getValue(Comment.class);
            databaseCore.insertNewComments(comment);
//            onRefreshList();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            Comment comment = dataSnapshot.getValue(Comment.class);
            databaseCore.deleteComment(comment);
            onRefreshList();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public static RepositoryApp getInstance(Context context) {
        if (instance == null)
            instance = new RepositoryApp(context);
        return instance;
    }

    private RepositoryApp(Context context) {
        databaseCore = new LocalDatabaseCore(context);
        networkCore = new NetworkCore();
        prefHelper = SharedPrefHelper.getInstance(context);
        myUser = prefHelper.getUser();
        if (myUser != null)
            listenerFeed();
    }


    private MutableLiveData<Boolean> liveDataRefresh = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLiveDataRefresh() {
        return liveDataRefresh;
    }

    public void onRefreshList() {
        liveDataRefresh.postValue(true);
    }

    public void listenerFeed() {
        networkCore.startListenerToFeed(mCallbackNewPostFeed);
    }


    public void insertPost(Post post, Uri imagePostUri) {
        post.setUserKey(myUser.getImgUrl());
        post.setUserName(myUser.getFirstName() + " " + myUser.getLastName());
        networkCore.insertPost(post, imagePostUri);
    }

    public LiveData<List<Comment>> getAllCommentsToPost(String postKey) {
        networkCore.startListenerCommentsPost(postKey, mListenerCommentsToPost);
        return databaseCore.getAllCommentsToPostLiveData(postKey);
    }

    public void insertComment(Comment comment) {
        comment.setUserKey(myUser.getImgUrl());
        comment.setUserName(myUser.getFirstName());
        networkCore.insertComment(comment);
    }

    public void insertUser(User user, Uri imageProfile, UploadImageCallback callback) {
        user.setEmail(user.getEmail().toLowerCase());
        User myUser = networkCore.insertUser(user, imageProfile, callback);
        this.myUser = myUser;
        prefHelper.storeUser(myUser);
    }


    public void downloadImage(String imageName, StorageFolder folder, DownloadImageCallback callback) {
        networkCore.downloadImage(imageName, folder, callback);
    }


    public User getMyUser() {
        return myUser;
    }

    public void loadMyUser(String email) {
        networkCore.loadMyUser(email, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    myUser = user;
                    prefHelper.storeUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<List<StandardPost>> getAllPostFeedLiveData() {
        return databaseCore.getAllPostFeedLiveData();
    }

    public LiveData<List<GameRequestsPost>> getAllMyPostsGameRequest(String userEmail) {
        return databaseCore.getAllMyPostsGameRequest(userEmail);
    }

    public LiveData<List<StandardPost>> getAllMyPosts() {
        return databaseCore.getAllMyPosts(myUser.getImgUrl());
    }

    public LiveData<List<Comment>> getAllMyComments() {
        return databaseCore.getAllMyComments(myUser.getImgUrl());
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        prefHelper.signOut();
        databaseCore.signOut();
        instance = null;
    }

    public void deleteComment(Comment comment) {
        databaseCore.deleteComment(comment);
        networkCore.deleteComment(comment);
    }

    public void updateComment(Comment comment) {
        databaseCore.updateComment(comment);
        networkCore.updateComment(comment);
    }

    public void deletePost(StandardPost post) {
        databaseCore.deletePost(post);
        networkCore.deletePost(post);
        deleteAllCommentsPost(post);
    }

    public void deleteAllCommentsPost(StandardPost post) {
        databaseCore.deletePostComments(post);
        networkCore.deletePostComments(post);
    }

    public void updatePostWithImage(StandardPost post) {
        databaseCore.insertNewStandardPost(post);
    }

    public void storeProfileImageToComment(File file, Comment comment) {
        comment.setImageProfileRAW(ConverterImage.getBytsFromFile(file));
        databaseCore.insertNewComments(comment);
    }
}
