package com.gather.land.activities;

import android.content.Intent;
import android.os.Bundle;

import com.gather.land.R;
import com.gather.land.interfaces.CallBackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements CallBackFragment  {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile)
                .build();
          navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    public void showFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }

    @Override
    public void showFragment(int fragmentId, Bundle bundle) {
        navController.navigate(fragmentId,bundle);

    }

    @Override
    public void showActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this,activity);
        startActivity(intent);
        finish();
    }


}