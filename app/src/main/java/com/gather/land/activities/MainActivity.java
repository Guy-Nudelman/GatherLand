package com.gather.land.activities;

import android.os.Bundle;
import android.os.Handler;

import com.gather.land.R;
import com.gather.land.interfaces.CallBackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements CallBackFragment {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile)
                .build();
          navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



//        StandardPost standardPost = new StandardPost("ACTION", System.currentTimeMillis(), "Worms", "Hello!!!");
//
//        RepositoryApp.getInstance(this).insertPost(standardPost);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                RepositoryApp.getInstance(MainActivity.this).loadAllCommentsToPost(standardPost.getKey()).observe(MainActivity.this, new Observer<List<Comment>>() {
//                    @Override
//                    public void onChanged(List<Comment> comments) {
//                        Log.d("Deadasdasd",comments.toString());
//                    }
//                });
//
//                Comment comment = new Comment(standardPost.getKey(), System.currentTimeMillis(), "WOWOWOOWWOWOOW!");
//                RepositoryApp.getInstance(MainActivity.this).insertComment(comment);
            }
        }, 700);



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

    }
}