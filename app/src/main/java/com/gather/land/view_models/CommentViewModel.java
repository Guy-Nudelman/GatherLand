package com.gather.land.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;

import com.gather.land.models.Comment;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;

public class CommentViewModel extends AndroidViewModel {
    RepositoryApp repositoryApp;
    public CommentViewModel(@NonNull Application application) {
        super(application);
        repositoryApp=RepositoryApp.getInstance(getApplication());

    }




    public void insertComment(Comment comment) {
        repositoryApp.insertComment(comment);
    }

    public void deletePost(StandardPost post) {
        repositoryApp.deletePost(post);
    }
}
