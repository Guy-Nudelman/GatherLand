package com.gather.land.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gather.land.R;
import com.gather.land.activities.MainActivity;
import com.gather.land.activities.SplashActivity;
import com.gather.land.adapters.AdapterFeed;
import com.gather.land.adapters.CommentsAdapter;
import com.gather.land.interfaces.ICallBackCommentAdapter;
import com.gather.land.interfaces.ICallBackFeedAdapter;
import com.gather.land.models.Comment;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.view_models.ProfileViewModel;
import java.util.List;

public class ProfileFragment extends BaseFragment  implements ICallBackFeedAdapter {

    private ProfileViewModel mViewModel;
    private TextView txtUserName;
    private Button btnLogOut;
    private RecyclerView recyclerViewPosts;
    private RecyclerView recyclerViewComments;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtUserName = view.findViewById(R.id.txtProfileName);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        txtUserName=view.findViewById(R.id.txtProfileName);
        txtUserName.setText(mViewModel.getUserName());
//        txtUserName.setVisibility(View.GONE);
        recyclerViewPosts = view.findViewById(R.id.recyclerViewProfilePosts);
        recyclerViewComments = view.findViewById(R.id.recyclerViewProfileComments);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewPosts.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        recyclerViewComments.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        RepositoryApp.getInstance(getContext()).getAllMyPosts().observe(getViewLifecycleOwner(), new Observer<List<StandardPost>>() {
            @Override
            public void onChanged(List<StandardPost> postList) {
                    initListPost(postList);

            }
        });
        RepositoryApp.getInstance(getContext()).getAllMyComments().observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> commentList) {
                loadRecyclerList(commentList);
            }
        });



        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.logOut();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mListener.showActivity(SplashActivity.class);
                    }
                },2000);

            }
        });



    }
    private void initListPost(List<StandardPost> postList) {
        AdapterFeed adapterFeed = new AdapterFeed(postList, getContext(), this);
        recyclerViewPosts.setAdapter(adapterFeed);
    }

    private void loadRecyclerList(List<Comment> commentList) {
        CommentsAdapter commentsAdapter = new CommentsAdapter(commentList, getContext(), new ICallBackCommentAdapter() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onShowEditCommentDialog(Comment comment) {
                showNewCommentDialog(comment);
            }
        });
        recyclerViewComments.setAdapter(commentsAdapter);
    }


    @Override
    public void onItemClickListener(StandardPost post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommentFragment.POST_KEY, post);
        mListener.showFragment(R.id.navigation_comments, bundle);
    }

    @Override
    public void onRefresh() {
        RepositoryApp.getInstance(getContext()).getAllPostFeedLiveData();
    }

    private void showNewCommentDialog(Comment comment) {
        String post=comment.getPostKey();
        EditText editText = new EditText(getContext());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().isEmpty()) {
                    return;
                }
                if (comment == null) {
                    Comment comment = new Comment(post, System.currentTimeMillis(), editText.getText().toString());
                    mViewModel.insertComment(comment);
                } else {
                    comment.setBody(editText.getText().toString());
                    comment.setTimeStampCreated(System.currentTimeMillis());
                    mViewModel.updateComment(comment);
                }
            }
        });
        dialog.setTitle("New comment");
        dialog.setView(editText);
        if (comment != null)
            editText.setText(comment.getBody());
        dialog.show();
    }
}