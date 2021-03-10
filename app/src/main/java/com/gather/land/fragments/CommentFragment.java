package com.gather.land.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gather.land.R;
import com.gather.land.adapters.CommentsAdapter;
import com.gather.land.enums.StorageFolder;
import com.gather.land.interfaces.DownloadImageCallback;
import com.gather.land.interfaces.ICallBackCommentAdapter;
import com.gather.land.models.Comment;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.utilities.Utils;

import java.io.File;
import java.util.List;


public class CommentFragment extends Fragment implements ICallBackCommentAdapter {

    private RecyclerView recyclerView;
    public final static String POST_KEY = "POST_KEY";
    private StandardPost post;
    TextView txtBody;
    TextView txtTitle;
    TextView txtTime;
    TextView txtUserName;
    ImageView profileImage;
    ImageView imgAddComment;
    ImageView imageViewPost;
    RepositoryApp repositoryApp;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));

        View viewPost = view.findViewById(R.id.my_post);

        txtBody = viewPost.findViewById(R.id.txtPostBody);
        txtTitle = viewPost.findViewById(R.id.txtPostTitle);
        txtTime = viewPost.findViewById(R.id.txtPostTime);
        txtUserName = viewPost.findViewById(R.id.txtPostUserName);
        profileImage = viewPost.findViewById(R.id.imageViewProfile);
        imageViewPost = viewPost.findViewById(R.id.imgViewPost);
        imgAddComment = view.findViewById(R.id.imgAddComment);

        repositoryApp = RepositoryApp.getInstance(getContext());


        imgAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewCommentDialog(null);
            }

        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            post = (StandardPost) bundle.getSerializable(POST_KEY);
            loadMyPost(post);
            loadAllComments(post);
        }
    }

    private void showNewCommentDialog(Comment comment) {
        EditText editText = new EditText(getContext());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().isEmpty()) {
                    return;
                }
                if (comment==null) {
                    Comment comment = new Comment(post.getKey(), System.currentTimeMillis(), editText.getText().toString());
                    repositoryApp.insertComment(comment);
                }else {
                    comment.setBody(editText.getText().toString());
                    comment.setTimeStampCreated(System.currentTimeMillis());
                    repositoryApp.updateComment(comment);
                    onRefresh();
                }
            }
        });
        dialog.setTitle("New comment");
        dialog.setView(editText);
        if (comment != null)
            editText.setText(comment.getBody());
        dialog.show();
    }

    private void loadAllComments(StandardPost post) {
        repositoryApp.getAllCommentsToPost(post.getKey()).observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> commentList) {
                loadRecyclerList(commentList);
            }
        });
    }

    private void loadRecyclerList(List<Comment> commentList) {
        CommentsAdapter commentsAdapter = new CommentsAdapter(commentList, getContext(), this);
        recyclerView.setAdapter(commentsAdapter);
    }

    private void loadMyPost(StandardPost post) {
        txtTitle.setText(post.getTitle());
        txtBody.setText(post.getBody());
        txtTime.setText(Utils.getTimeAsStringFormat(post.getTimeStampCreated()));
        txtUserName.setText(post.getUserName());
        repositoryApp.downloadImage(post.getUserKey(), StorageFolder.PROFILE, new DownloadImageCallback() {
            @Override
            public void onImageDownloaded(File file) {
                Activity activity = getActivity();
                if (file != null && activity != null && !activity.isDestroyed())
                    Glide.with(activity).load(file).into(profileImage);
            }
        });

        repositoryApp.downloadImage(post.getKey(), StorageFolder.FEED, new DownloadImageCallback() {
            @Override
            public void onImageDownloaded(File file) {
                Activity activity = getActivity();
                if (file != null && activity != null && !activity.isDestroyed())
                    Glide.with(activity).load(file).into(imageViewPost);
            }
        });

    }

    @Override
    public void onRefresh() {
        loadAllComments(post);
    }

    @Override
    public void onShowEditCommentDialog(Comment comment) {
        //TODO show dialog
        showNewCommentDialog(comment);

    }
}