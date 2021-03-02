package com.gather.land.reposetories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;
import com.gather.land.models.User;

import java.util.ArrayList;

public class RepositoryApp {

    private static RepositoryApp instance;
    private final NetworkCore networkCore;

    public static RepositoryApp getInstance(Context context) {
        if (instance == null)
            instance = new RepositoryApp(context);
        return instance;
    }

    private RepositoryApp(Context context) {
        networkCore = new NetworkCore();
    }

    public LiveData<ArrayList<StandardPost>> getLiveDataStandartPost() {
        return networkCore.getLiveDataStandartPost();
    }

    public void insertPost(Post post) {
        networkCore.insertPost(post);
        //TODO insert to cache local storage DB
    }

    public void insertUser(User user) {
        networkCore.insertUser(user);
    }

}
