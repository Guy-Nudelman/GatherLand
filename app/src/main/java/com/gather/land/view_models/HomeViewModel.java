package com.gather.land.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gather.land.reposetories.RepositoryApp;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    RepositoryApp repositoryApp;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        repositoryApp=RepositoryApp.getInstance(getApplication());
    }

    public String getUserName() {
        return repositoryApp.getMyUser().getFirstName();
    }

}