package com.gather.land.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;

import com.gather.land.R;
import com.gather.land.interfaces.CallBackFragment;

public class AuthActivity extends AppCompatActivity implements CallBackFragment {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public void showFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }

    @Override
    public void showActivity(Class<? extends AppCompatActivity> activity) {

        Intent intent = new Intent(this,activity);
        startActivity(intent);
        finish();
    }
}