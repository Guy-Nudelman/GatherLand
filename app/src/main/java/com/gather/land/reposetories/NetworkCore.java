package com.gather.land.reposetories;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gather.land.enums.FirebaseTable;
import com.gather.land.enums.StorageFolder;
import com.gather.land.interfaces.DownloadImageCallback;
import com.gather.land.interfaces.UploadImageCallback;
import com.gather.land.models.Comment;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;
import com.gather.land.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class NetworkCore {
    private final DatabaseReference mRef;
    private final StorageReference mStorage;

    public NetworkCore() {
        mRef = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    public void startListenerToFeed(ChildEventListener callback) {
        //TODO listener from last timestamp updated
        mRef.child(FirebaseTable.FEED.name()).addChildEventListener(callback);
    }


    public User insertUser(User user, Uri imageProfile, UploadImageCallback callback) {
        String emailKey = getEmailKey(user.getEmail());
        user.setImgUrl(emailKey);
        mRef.child(FirebaseTable.USERS.name()).child(emailKey).setValue(user);

        if (imageProfile != null) {
            uploadImage(imageProfile, emailKey, StorageFolder.PROFILE, callback);
        } else {
            callback.onFinish(false);
        }

        return user;
    }

    public void downloadImage(String imageName, StorageFolder folder, DownloadImageCallback callback) {
        File file = null;

        try {
            file = File.createTempFile("image", ".jpg");
        } catch (Exception e) {
            e.getStackTrace();
        }

        File finalFile = file;
        mStorage.child(folder.name()).child(imageName).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                if (callback != null)
                    callback.onImageDownloaded(finalFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (callback != null)
                    callback.onImageDownloaded(null);
            }
        });

    }

    private void uploadImage(Uri imageUri, String imageName, StorageFolder folder, UploadImageCallback callbackResult) {
        mStorage.child(folder.name()).child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (callbackResult != null)
                    callbackResult.onFinish(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (callbackResult != null)
                    callbackResult.onFinish(false);
            }
        });
    }

    public void insertPost(Post post, Uri imagePostUri) {

        String key = mRef.child(FirebaseTable.FEED.name()).push().getKey();
        post.setKey(key);
        post.setUserKey(getEmailKey(post.getUserKey()));

        if (imagePostUri != null) {
            uploadImage(imagePostUri, key, StorageFolder.FEED, new UploadImageCallback() {
                @Override
                public void onFinish(boolean isSuccess) {
                    mRef.child(FirebaseTable.FEED.name()).child(key).setValue(post);
                }
            });
        } else {
            mRef.child(FirebaseTable.FEED.name()).child(key).setValue(post);
        }
    }

    private String getEmailKey(String email) {
        return email.toLowerCase().replaceAll("@", "_").replaceAll("\\.", "_").replaceAll("\\$", "_").replaceAll("\\+", "_").replaceAll(" ", "_");
    }

    public void loadMyUser(String email, ValueEventListener callback) {
        mRef.child(FirebaseTable.USERS.name()).child(getEmailKey(email)).addListenerForSingleValueEvent(callback);
    }

    public void insertComment(Comment comment) {
        String commentKey = mRef.child(FirebaseTable.COMMENT.name()).child(comment.getPostKey()).push().getKey();
        comment.setKey(commentKey);
        mRef.child(FirebaseTable.COMMENT.name()).child(comment.getPostKey()).child(commentKey).setValue(comment);
    }

    public void startListenerCommentsPost(String postKey, ChildEventListener callback) {
        mRef.child(FirebaseTable.COMMENT.name()).child(postKey).addChildEventListener(callback);
    }

    public void deleteComment(Comment comment) {
        mRef.child(FirebaseTable.COMMENT.name()).child(comment.getPostKey()).child(comment.getKey()).removeValue();
    }

    public void updateComment(Comment comment) {
        mRef.child(FirebaseTable.COMMENT.name()).child(comment.getPostKey()).child(comment.getKey()).setValue(comment);
    }

    public void deletePost(StandardPost post) {
        mRef.child(FirebaseTable.FEED.name()).child(post.getKey()).removeValue();
        mRef.child(FirebaseTable.POST_DELETED.name()).child(post.getKey()).setValue(post);
    }

    public void deletePostComments(StandardPost post) {
        mRef.child(FirebaseTable.COMMENT.name()).child(post.getKey()).removeValue();
    }

    public void checkAllPostDeleted(ValueEventListener valueEventListener) {
        mRef.child(FirebaseTable.POST_DELETED.name()).addListenerForSingleValueEvent(valueEventListener);
    }
}
