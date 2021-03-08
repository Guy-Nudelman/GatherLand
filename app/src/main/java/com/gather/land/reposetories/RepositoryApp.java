package com.gather.land.reposetories;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.gather.land.enums.StorageFolder;
import com.gather.land.interfaces.DownloadImageCallback;
import com.gather.land.interfaces.UploadImageCallback;
import com.gather.land.models.Comment;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;
import com.gather.land.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RepositoryApp {

    private static RepositoryApp instance;
    private final NetworkCore networkCore;
    private final LocalDatabaseCore databaseCore;
    private final SharedPrefHelper prefHelper;
    private User myUser;


    private final ValueEventListener mCallbackNewPostFeed = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<StandardPost> standardPosts = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                StandardPost post = snapshot.getValue(StandardPost.class);
                standardPosts.add(post);
            }
            databaseCore.insertStandardPost(standardPosts);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    private ValueEventListener mListenerCommentsToPost = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<Comment> commentArrayList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Comment comment = snapshot.getValue(Comment.class);
                commentArrayList.add(comment);
            }
            databaseCore.insertNewCommentsLis(commentArrayList);
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
        networkCore.startListenerToFeed(mCallbackNewPostFeed);
        myUser = prefHelper.getUser();
    }


    public void insertPost(Post post) {
        post.setUserKey(myUser.getImgUrl());
        post.setUserName(myUser.getFirstName()+" "+myUser.getLastName());
        networkCore.insertPost(post);
    }

    public LiveData<List<Comment>> loadAllCommentsToPost(String postKey) {
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

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        prefHelper.signOut();
        databaseCore.signOut();
    }
}
