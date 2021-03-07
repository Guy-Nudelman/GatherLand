package com.gather.land.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gather.land.R;
import com.gather.land.enums.StorageFolder;
import com.gather.land.interfaces.DownloadImageCallback;
import com.gather.land.interfaces.ICallBackFeedAdapter;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.AdapterFeedViewHolder> {
    private Context context;
    private List<StandardPost> standardPosts;
    private ICallBackFeedAdapter callback;
    private SimpleDateFormat simpleDateFormat;
    private RepositoryApp repositoryApp;

    public AdapterFeed(List<StandardPost> standardPosts, Context context, ICallBackFeedAdapter callback) {
        this.standardPosts = standardPosts;
        this.callback = callback;
        this.context = context;
        this.simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.repositoryApp=RepositoryApp.getInstance(context);
    }


    @NonNull
    @Override
    public AdapterFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterFeedViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feed, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeedViewHolder holder, int position) {
        StandardPost post= standardPosts.get(position);
        holder.txtTitle.setText(post.getTitle());
        holder.txtBody.setText(post.getBody());
        holder.txtTime.setText(getTimeAsStringFormat(post.getTimeStampCreated()));
        holder.txtUserName.setText(post.getUserName());
        repositoryApp.downloadImage(post.getUserKey(), StorageFolder.PROFILE, new DownloadImageCallback() {
            @Override
            public void onImageDownloaded(File file) {
                Glide.with(context).load(file).into(holder.profileImage);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.onItemClickListener(post);
                }
            }
        });
    }

    private String getTimeAsStringFormat(long timeStampCreated) {
        String timeFormat=simpleDateFormat.format(new Date(timeStampCreated));
        return timeFormat;
    }

    @Override
    public int getItemCount() {
        return standardPosts.size();
    }

    class AdapterFeedViewHolder extends RecyclerView.ViewHolder {
        TextView txtBody;
        TextView txtTitle;
        TextView txtTime;
        TextView txtUserName;
        ImageView profileImage;

        public AdapterFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBody = itemView.findViewById(R.id.txtPostBody);
            txtTitle = itemView.findViewById(R.id.txtPostTitle);
            txtTime = itemView.findViewById(R.id.txtPostTime);
            txtUserName = itemView.findViewById(R.id.txtPostUserName);
            profileImage = itemView.findViewById(R.id.imageViewProfile);


        }
    }
}
