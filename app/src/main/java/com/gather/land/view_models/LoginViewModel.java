package com.gather.land.view_models;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gather.land.activities.MainActivity;
import com.gather.land.reposetories.RepositoryApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends AndroidViewModel {

    MutableLiveData<String> mutableLiveDataCreateUserResponse = new MutableLiveData<>();
    RepositoryApp repositoryApp;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repositoryApp = RepositoryApp.getInstance(application);
    }

    public LoginViewModel.ERREOR_INPUT isValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return LoginViewModel.ERREOR_INPUT.MISS_FIELDS;
        } else if (password.length() < 6) {
            return LoginViewModel.ERREOR_INPUT.PASS_SHORT;
        }
        return LoginViewModel.ERREOR_INPUT.VALID;
    }

    public LiveData<String> getMutableLiveDataCreateUserResponse() {
        return mutableLiveDataCreateUserResponse;

    }

    public void login(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RepositoryApp.getInstance(getApplication()).loadMyUser(email);
                    mutableLiveDataCreateUserResponse.postValue("VALID");
                } else {
                    mutableLiveDataCreateUserResponse.postValue(task.getException().getMessage().toString());

                }
            }
        });

    }

    public void listenerFeed() {
        repositoryApp.listenerFeed();
    }

    public enum ERREOR_INPUT {
        MISS_FIELDS,
        PASS_SHORT,
        NAME_SHORT,
        VALID
    }
}