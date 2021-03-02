package com.gather.land.interfaces;

import androidx.appcompat.app.AppCompatActivity;

public interface CallBackFragment {
    void showFragment(int fragmentId);
    void showActivity(Class<? extends AppCompatActivity> activity);
}
