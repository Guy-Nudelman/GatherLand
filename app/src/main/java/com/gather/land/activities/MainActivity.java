package com.gather.land.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gather.land.R;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;
import com.gather.land.models.User;
import com.gather.land.reposetories.RepositoryApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this,navController);

        User user=new User("te22s@gmail.com","moti","last","empty",System.currentTimeMillis());
        RepositoryApp.getInstance(this).insertUser(user);


        StandardPost standardPost=new StandardPost("ACTION",user.getEmail(),System.currentTimeMillis(),"Worms","Hello!!!");


        RepositoryApp.getInstance(this).insertPost(standardPost);


        RepositoryApp.getInstance(this).getLiveDataStandartPost().observe(this, new Observer<ArrayList<StandardPost>>() {
            @Override
            public void onChanged(ArrayList<StandardPost> standardPosts) {
                Log.d("WOWOWOOWOW",standardPosts.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            navController.navigateUp();
            return true;
        }
        return NavigationUI.onNavDestinationSelected(item,navController);
    }
}