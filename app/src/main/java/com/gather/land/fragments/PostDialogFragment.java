package com.gather.land.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.gather.land.R;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.view_models.HomeViewModel;
import com.gather.land.view_models.PostDialogViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;

public class PostDialogFragment extends AppCompatDialogFragment {
    private PostDialogViewModel mViewModel;
    private EditText postTitle;
    private EditText postBody;
    private ImageView imgViewPost;
    private Uri imagePostUri;
    private static final int IMAGE_GALLERY_PROFILE_REQUEST = 1;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostDialogViewModel.class);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_post_dialog, null);
        postTitle = view.findViewById(R.id.edtPostTitle);
        postBody = view.findViewById(R.id.edtPostBody);
        imgViewPost = view.findViewById(R.id.imgPost);
        imgViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
        dialog.setView(view).setTitle("New Post")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title;
                        String body;
                        title = postTitle.getText().toString();
                        body = postBody.getText().toString();
                        if (postTitle.length() > 3 && postBody.length() > 4) {
                            StandardPost standardPost = new StandardPost("ACTION", System.currentTimeMillis(), title, body);
                            mViewModel.insertPost(standardPost,imagePostUri);
                        } else {
                            Toast.makeText(getContext(), "We cant post such short posts", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();

                    }
                });
        return dialog.create();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_GALLERY_PROFILE_REQUEST && resultCode == Activity.RESULT_OK) {
            imagePostUri = data.getData();
            Glide.with(getContext()).load(imagePostUri).into(imgViewPost);
        }
    }

    private void getImage() {
        ImagePicker.Companion.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .galleryOnly()
                .start(IMAGE_GALLERY_PROFILE_REQUEST);
    }
}
