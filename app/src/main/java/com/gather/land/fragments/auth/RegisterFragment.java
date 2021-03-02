package com.gather.land.fragments.auth;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gather.land.R;
import com.gather.land.activities.MainActivity;
import com.gather.land.fragments.BaseFragment;
import com.gather.land.view_models.RegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends BaseFragment {

    private RegisterViewModel mViewModel;
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnUpload;
    private Button btnRegister;

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
        edtName=view.findViewById(R.id.edt_name);
        edtLastName=view.findViewById(R.id.edt_lastName);
        edtEmail=view.findViewById(R.id.edt_email);
        edtPassword=view.findViewById(R.id.edt_password);
        btnUpload=view.findViewById(R.id.btn_upload);
        btnRegister=view.findViewById(R.id.btn_register);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String password= edtPassword.getText().toString();
                if(!isValid())
                    return;
                createNewUser(email,password);
            }
        });


    }

    private boolean isValid() {
        String email = edtEmail.getText().toString();
        String password= edtPassword.getText().toString();
        String name= edtName.getText().toString();
        String lastName= edtLastName.getText().toString();


        if (email.isEmpty()||password.isEmpty()||name.isEmpty()||lastName.isEmpty()){
            Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }else  if (password.length()<6){
            Toast.makeText(getContext(), "Password is too short", Toast.LENGTH_SHORT).show();
            return false;
        }else if (name.length()<3 || lastName.length()<3){
            Toast.makeText(getContext(), "name is too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createNewUser(String email, String password) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //TODO : store locally user details
                    //TODO : insert to firebase database user details
                    //TODO : show name activity
                    mListener.showActivity(MainActivity.class);
                }else {
                    Toast.makeText(getContext(), "Email already taken or is not a valid email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

}