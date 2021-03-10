package com.gather.land.fragments.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.gather.land.view_models.LoginViewModel;

public class LoginFragment extends BaseFragment {

    private LoginViewModel mViewModel;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private ProgressBar progressBarLoad;


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
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        btnLogin = view.findViewById(R.id.btn_login);
        progressBarLoad = view.findViewById(R.id.progressBarHome);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                LoginViewModel.ERREOR_INPUT input = mViewModel.isValid(email, password);
                switch (input) {
                    case VALID:
                        progressBarLoad.setVisibility(View.VISIBLE);
                        mViewModel.login(email, password);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mViewModel.getMutableLiveDataCreateUserResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBarLoad.setVisibility(View.GONE);
                if (s.equals("VALID")) {
                    mListener.showActivity(MainActivity.class);
                } else {
                    Toast.makeText(getContext(), s.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}