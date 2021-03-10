package com.gather.land.view_models;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;

public class PostDialogViewModel extends AndroidViewModel {
    public PostDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertPost(StandardPost standardPost, Uri imagePostUri) {
        RepositoryApp.getInstance(getApplication()).insertPost(standardPost,imagePostUri);

    }
}
