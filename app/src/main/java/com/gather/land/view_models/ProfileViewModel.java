package com.gather.land.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gather.land.models.Comment;
import com.gather.land.reposetories.RepositoryApp;
public class ProfileViewModel extends AndroidViewModel {
    RepositoryApp repositoryApp;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repositoryApp=RepositoryApp.getInstance(getApplication());
    }
//    public String getUserFirstName(){
//        return repositoryApp.getMyUser().getFirstName().toString();
//    }
//    public String getUserLastName(){
//        return repositoryApp.getMyUser().getLastName().toString();
//    }

    public void logOut() {
        repositoryApp.signOut();
    }

    public void insertComment(Comment comment) {
        repositoryApp.insertComment(comment);

    }

    public void updateComment(Comment comment) {
        repositoryApp.updateComment(comment);
    }

    public String getUserName() {
        return repositoryApp.getMyUser().getFirstName()+" "+repositoryApp.getMyUser().getLastName();
    }
}
