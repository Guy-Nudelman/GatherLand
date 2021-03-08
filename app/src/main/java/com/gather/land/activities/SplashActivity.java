package com.gather.land.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gather.land.R;
import com.gather.land.reposetories.RepositoryApp;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    //  RepositoryApp.getInstance(this).signOut();
        showScreen();
    }

    private void showScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    showActivity(AuthActivity.class);
                } else {
                    showActivity(MainActivity.class);
                }

            }
        }, 3000);
    }

    private void showActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }


}