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
import android.widget.TextView;
import android.widget.Toast;

import com.gather.land.R;
import com.gather.land.activities.MainActivity;
import com.gather.land.fragments.BaseFragment;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.view_models.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends BaseFragment {

    private LoginViewModel mViewModel;
   // private TextView txtHeader;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   txtHeader=view.findViewById(R.id.txt_header);
        edtEmail=view.findViewById(R.id.edt_email);
        edtPassword=view.findViewById(R.id.edt_password);
        btnLogin=view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edtEmail.getText().toString();
                String password=edtPassword.getText().toString();
                if(!isValid()){
                    return;
                }
                login(email,password);
            }
        });

    }

    private boolean isValid() {
        String email = edtEmail.getText().toString();
        String password= edtPassword.getText().toString();
        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }else  if (password.length()<6){
            Toast.makeText(getContext(), "Password too shorts", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void login(String email, String password) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    RepositoryApp.getInstance(getContext()).loadMyUser(email);
                    mListener.showActivity(MainActivity.class);
                }else{
                    Toast.makeText(getContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

}