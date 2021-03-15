package com.gather.land.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gather.land.R;

public class AboutFragment extends Fragment {


    TextView txtTitle;
    Button emailBtn;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //txtTitle=view.findViewById(R.id.txtAboutTitle);
        emailBtn=view.findViewById(R.id.btnAboutEmail);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "guy.nudelman@aol.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact GatherLand");
                intent.putExtra(Intent.EXTRA_TEXT, "Wanted to ...");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}