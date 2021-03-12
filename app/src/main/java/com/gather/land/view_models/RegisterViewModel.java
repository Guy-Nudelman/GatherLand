package com.gather.land.view_models;

import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gather.land.activities.MainActivity;
import com.gather.land.interfaces.UploadImageCallback;
import com.gather.land.models.User;
import com.gather.land.reposetories.RepositoryApp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends AndroidViewModel {
    private Uri imageProfile;
    private User user;
    private RepositoryApp repositoryApp;
    private static final int IMAGE_GALLERY_PROFILE_REQUEST = 1;


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setImageProfile(Uri imageProfile) {
        this.imageProfile = imageProfile;
    }

    public Uri getImageProfile() {
        return imageProfile;
    }

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repositoryApp = RepositoryApp.getInstance(application);
    }

    MutableLiveData<String> mutableLiveDataCreateUserResponse = new MutableLiveData<>();

    public LiveData<String> getMutableLiveDataCreateUserResponse() {
        return mutableLiveDataCreateUserResponse;
    }

    public void createNewUser(String email, String password) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    RepositoryApp.getInstance(getApplication()).insertUser(getUser(), getImageProfile(), new UploadImageCallback() {
                        @Override
                        public void onFinish(boolean isSuccess) {
                            mutableLiveDataCreateUserResponse.postValue("VALID");

                        }
                    });
                } else {
                    mutableLiveDataCreateUserResponse.postValue("NO_VALID");

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableLiveDataCreateUserResponse.postValue(e.getMessage().toString());


            }
        });


    }

    public void listenerFeed() {
        repositoryApp.listenerFeed();
    }


    public static enum ERREOR_INPUT {
        MISS_FIELDS,
        PASS_SHORT,
        NAME_SHORT,
        VALID
    }

    public ERREOR_INPUT isValid(String email,
                                String password,
                                String name,
                                String lastName) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastName.isEmpty()) {
            return ERREOR_INPUT.MISS_FIELDS;
        } else if (password.length() < 6) {
            return ERREOR_INPUT.PASS_SHORT;
        } else if (name.length() < 3 || lastName.length() < 3) {
            return ERREOR_INPUT.NAME_SHORT;
        }

        return ERREOR_INPUT.VALID;
    }
}