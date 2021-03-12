package com.gather.land.fragments.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gather.land.R;
import com.gather.land.activities.MainActivity;
import com.gather.land.fragments.BaseFragment;
import com.gather.land.interfaces.UploadImageCallback;
import com.gather.land.models.User;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.view_models.RegisterViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends BaseFragment {

    private static final int IMAGE_GALLERY_PROFILE_REQUEST = 1;
    private RegisterViewModel mViewModel;
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnUpload;
    private Button btnRegister;
    private ProgressBar progressBarLoad;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtName = view.findViewById(R.id.edt_name);
        edtLastName = view.findViewById(R.id.edt_lastName);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        btnUpload = view.findViewById(R.id.btn_upload);
        progressBarLoad = view.findViewById(R.id.progressBarLoad);
        btnRegister = view.findViewById(R.id.btn_register);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String name = edtName.getText().toString();
                String lastName = edtLastName.getText().toString();

                RegisterViewModel.ERREOR_INPUT input = mViewModel.isValid(email, password, name, lastName);
                switch (input) {
                    case VALID:
                        progressBarLoad.setVisibility(View.VISIBLE);
                        mViewModel.setUser(new User(email, name, lastName, "empty"));
                        mViewModel.createNewUser(email, password);
                        break;
                    case NAME_SHORT:
                        progressBarLoad.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Name is too short", Toast.LENGTH_SHORT).show();

                        break;

                    case PASS_SHORT:
                        progressBarLoad.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Password is too short", Toast.LENGTH_SHORT).show();

                        break;
                    case MISS_FIELDS:
                        progressBarLoad.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_GALLERY_PROFILE_REQUEST && resultCode == Activity.RESULT_OK) {
            mViewModel.setImageProfile(data.getData());
        }
    }

    private void getImage() {
        ImagePicker.Companion.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .galleryOnly()
                .start(IMAGE_GALLERY_PROFILE_REQUEST);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        mViewModel.getMutableLiveDataCreateUserResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBarLoad.setVisibility(View.GONE);
                if (s.equals("VALID")) {
                    mViewModel.listenerFeed();
                    mListener.showActivity(MainActivity.class);
                } else {
                    Toast.makeText(getContext(), s.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}