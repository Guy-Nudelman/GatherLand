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

import com.bumptech.glide.Glide;
import com.gather.land.R;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;

public class PostDialogFragment extends AppCompatDialogFragment {
    private EditText postTitle;
    private EditText postBody;
    private ImageView imgViewPost;
    private Uri imagePostUri;
    private static final int IMAGE_GALLERY_PROFILE_REQUEST = 1;
    private static final int IMAGE_FROM_CAMERA_REQUEST = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 3;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_post_dialog, null);
        postTitle = view.findViewById(R.id.edtPostTitle);
        postBody = view.findViewById(R.id.edtPostBody);
        imgViewPost = view.findViewById(R.id.imgPost);
        imgViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog imgDialog = new AlertDialog.Builder(getContext()).create();
                imgDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgDialog.dismiss();
//                        if (!hasPVameraermission)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (getContext().checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                requestPermissions(new String[]{ Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST);
                            }else{
                                askImageFromCamera();

                            }
                        }else {
                            askImageFromCamera();
                        }
                    }
                });
                imgDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgDialog.dismiss();
                        getImage();
                    }
                });
                imgDialog.setTitle("Choose from:");
                imgDialog.show();
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
                        if (postTitle.length() > 3 && postBody.length() > 10) {
                            StandardPost standardPost = new StandardPost("ACTION", System.currentTimeMillis(), title, body);
                            RepositoryApp.getInstance(getContext()).insertPost(standardPost,imagePostUri);
                        } else {
                            Toast.makeText(getContext(), "We cant post such short posts", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();

                    }
                });


        return dialog.create();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isPermissionAllowed=true;
        if (requestCode==CAMERA_PERMISSION_REQUEST){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    //TODO denied
                    isPermissionAllowed=false;
                }
            }

            if (isPermissionAllowed){
                askImageFromCamera();
            }
        }
    }

    private void askImageFromCamera() {

//        Intent intent=
//                new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = null;
//        File storageDir=getContext().getExternalCacheDir();
//        try {
//            file=File.createTempFile(
//                    "camera",
//                    ".jpg",
//                    storageDir
//            );
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
//        Uri photo= FileProvider.getUriForFile(getContext(),getContext().getPackageName()+".fileprovider".toLowerCase(),file);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,photo);
//        startActivityForResult(intent,IMAGE_FROM_CAMERA_REQUEST);

    }
    Uri photo;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_GALLERY_PROFILE_REQUEST && resultCode == Activity.RESULT_OK) {
            imagePostUri = data.getData();
            Glide.with(getContext()).load(imagePostUri).into(imgViewPost);
        } else if (requestCode == IMAGE_FROM_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
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
