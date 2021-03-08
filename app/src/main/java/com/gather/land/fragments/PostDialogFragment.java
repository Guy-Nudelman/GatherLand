package com.gather.land.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.gather.land.R;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;

public class PostDialogFragment extends AppCompatDialogFragment {
    EditText postTitle;
    EditText postBody;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_post_dialog, null);
        postTitle = view.findViewById(R.id.edtPostTitle);
        postBody = view.findViewById(R.id.edtPostBody);
        dialog.setView(view).setTitle("New Post")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title;
                        String body;
                        title = postTitle.getText().toString();
                        body = postBody.getText().toString();
                        if (postTitle.length() >3 && postBody.length() > 10) {
                            StandardPost standardPost = new StandardPost("ACTION", System.currentTimeMillis(), title, body);
                            RepositoryApp.getInstance(getContext()).insertPost(standardPost);
                        }else{
                            Toast.makeText(getContext(),"We cant post such short posts",Toast.LENGTH_LONG).show();
                        }

                    }
                });


        return dialog.create();

    }
}
