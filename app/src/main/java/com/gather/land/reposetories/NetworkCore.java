package com.gather.land.reposetories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gather.land.enums.FirebaseTable;
import com.gather.land.models.Post;
import com.gather.land.models.StandardPost;
import com.gather.land.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NetworkCore {
    private final DatabaseReference mRef;
    private MutableLiveData<ArrayList<StandardPost>>  mutableLiveDataStandartPost=new MutableLiveData<>();
    public NetworkCore() {
        mRef = FirebaseDatabase.getInstance().getReference();
        startListenerToFeed();
    }

    private void startListenerToFeed() {
        mRef.child(FirebaseTable.FEED.name()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<StandardPost> standardPosts=new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    StandardPost post=snapshot.getValue(StandardPost.class);
                    standardPosts.add(post);
                }
                mutableLiveDataStandartPost.postValue(standardPosts);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<ArrayList<StandardPost>> getLiveDataStandartPost() {
        return mutableLiveDataStandartPost;
    }

    public void insertUser(User user) {
        mRef.child(FirebaseTable.USERS.name()).child(getEmailKey(user.getEmail())).setValue(user);
    }

    public void insertPost(Post post) {
        String key = mRef.child(FirebaseTable.FEED.name()).push().getKey();
        post.setKey(key);
        mRef.child(FirebaseTable.FEED.name()).child(key).setValue(post);
    }

    private String getEmailKey(String email) {
        return email.replaceAll("@", "_").replaceAll("\\.", "_").replaceAll("\\$", "_").replaceAll("\\+", "_").replaceAll(" ", "_");
    }
}
