package com.gather.land.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gather.land.R;
import com.gather.land.enums.StorageFolder;
import com.gather.land.interfaces.DownloadImageCallback;
import com.gather.land.interfaces.ICallBackCommentAdapter;
import com.gather.land.models.Comment;
import com.gather.land.models.User;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.utilities.ConverterImage;
import com.gather.land.utilities.Utils;

import java.io.File;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.AdapterFeedViewHolder> {
    private final Context context;
    private final List<Comment> comments;
    private final RepositoryApp repositoryApp;
    private String myEmail;
    private final ICallBackCommentAdapter callBackCommentAdapter;

    public CommentsAdapter(List<Comment> commentList, Context context, ICallBackCommentAdapter callBackCommentAdapter) {
        this.comments = commentList;
        this.context = context;
        this.callBackCommentAdapter = callBackCommentAdapter;
        this.repositoryApp = RepositoryApp.getInstance(context);

        User user = repositoryApp.getMyUser();
        if (user != null)
            this.myEmail = user.getImgUrl();
    }


    @NonNull
    @Override
    public AdapterFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterFeedViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeedViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.llContainerMyPost.setVisibility(comment.getUserKey().equals(myEmail) ? View.VISIBLE : View.GONE);
        holder.txtBody.setText(comment.getBody());
        holder.txtTime.setText(Utils.getTimeAsStringFormat(comment.getTimeStampCreated()));
        holder.txtUserName.setText(comment.getUserName());


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackCommentAdapter.onShowEditCommentDialog(comment);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repositoryApp.deleteComment(comment);
                callBackCommentAdapter.onRefresh();
            }
        });


        if (comment.getImageProfileRAW() != null) {
            Bitmap bitmap = ConverterImage.getBitmapImage(comment.getImageProfileRAW());
            Glide.with(context).load(bitmap).into(holder.profileImage);
        } else {
            holder.profileImage.setImageBitmap(null);
            repositoryApp.downloadImage(comment.getUserKey(), StorageFolder.PROFILE, new DownloadImageCallback() {
                @Override
                public void onImageDownloaded(File file) {
                    if (file != null) {
                        Glide.with(context).load(file).into(holder.profileImage);
                        repositoryApp.storeProfileImageToComment(file, comment);
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    class AdapterFeedViewHolder extends RecyclerView.ViewHolder {
        TextView txtBody;
        TextView txtTime;
        TextView txtUserName;
        ImageView profileImage;
        ImageView imgDelete;
        ImageView imgEdit;
        LinearLayout llContainerMyPost;

        public AdapterFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            llContainerMyPost = itemView.findViewById(R.id.llContainerMyPost);
            imgDelete = itemView.findViewById(R.id.imgDeletePost);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            txtBody = itemView.findViewById(R.id.txtBody);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtUserName = itemView.findViewById(R.id.txtName);
            profileImage = itemView.findViewById(R.id.imgComment);


        }
    }
}
