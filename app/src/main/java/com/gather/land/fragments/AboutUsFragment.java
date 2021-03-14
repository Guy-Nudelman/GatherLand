package com.gather.land.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gather.land.R;
import com.gather.land.reposetories.RepositoryApp;


public class AboutUsFragment extends BaseFragment {


    private ScrollView scrollView;
    private TextView textView[];
    RepositoryApp repositoryApp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_about_us, container, false);
        repositoryApp = RepositoryApp.getInstance(getContext());
        textView = new TextView[3];

        String hello = getString(R.string.hello,repositoryApp.getMyUser().getFirstName());
        String data = getString(R.string.data,1,1);


        textView[0] =  root.findViewById(R.id.first_txt_about_us);
        textView[1] =  root.findViewById(R.id.second_txt_about_us);
        textView[2] =  root.findViewById(R.id.third_txt_about_us);
        textView[0].setText(hello);
        textView[1].setText(data);
        scrollView= new ScrollView(getContext());

        scrollView.computeScroll();
        return root;
    }



}