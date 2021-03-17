package com.gather.land.view_models;

import android.app.Application;
import android.os.FileUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.util.FileUtil;

import com.gather.land.models.Comment;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.utilities.ConverterImage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommentViewModel extends AndroidViewModel {
    RepositoryApp repositoryApp;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        repositoryApp = RepositoryApp.getInstance(getApplication());
    }


    public void insertComment(Comment comment) {
        repositoryApp.insertComment(comment);
    }

    public void deletePost(StandardPost post) {
        repositoryApp.deletePost(post);
    }

    public void insertImagePostToDB(File file, StandardPost post) {
        if (post.getImagePostRAW() != null)
            return;

        post.setImagePostRAW(ConverterImage.getBytsFromFile(file));
        RepositoryApp.getInstance(getApplication()).updatePostWithImage(post);
    }


    public void insertImageProfileToDB(File file, StandardPost post) {
        if (post.getImageProfileRAW() != null)
            return;

        post.setImageProfileRAW(ConverterImage.getBytsFromFile(file));
        RepositoryApp.getInstance(getApplication()).updatePostWithImage(post);

    }
}
