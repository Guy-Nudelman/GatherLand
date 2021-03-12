package com.gather.land.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gather.land.R;
import com.gather.land.adapters.AdapterFeed;
import com.gather.land.interfaces.ICallBackFeedAdapter;
import com.gather.land.models.StandardPost;
import com.gather.land.reposetories.RepositoryApp;
import com.gather.land.view_models.HomeViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class HomeFragment extends BaseFragment implements ICallBackFeedAdapter {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewFeed;
    Button addPostBtn;
    RepositoryApp repositoryApp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        repositoryApp = RepositoryApp.getInstance(getContext());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewFeed = view.findViewById(R.id.recyclerViewProfileComments);
        recyclerViewFeed.setHasFixedSize(true);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewFeed.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));


        repositoryApp.getLiveDataRefresh().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                loadRecyclerList();
            }
        });

        loadRecyclerList();
        addPostBtn = view.findViewById(R.id.btnHomeAddPostBtn);
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDialogFragment dialogFragment = new PostDialogFragment();
                Toast.makeText(getContext(), "Make sure to keep our rules. Keep it cleam!", Toast.LENGTH_SHORT).show();
                dialogFragment.show(getFragmentManager(), "Post");
            }

        });
    }

    private void loadRecyclerList() {
        repositoryApp.getAllPostFeedLiveData().observe(getViewLifecycleOwner(), new Observer<List<StandardPost>>() {
            @Override
            public void onChanged(List<StandardPost> postList) {
                initListPost(postList);


            }

        });
    }

    private void initListPost(List<StandardPost> postList) {
        AdapterFeed adapterFeed = new AdapterFeed(postList, getContext(), this);
        recyclerViewFeed.setAdapter(adapterFeed);
    }

    @Override
    public void onItemClickListener(StandardPost post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommentFragment.POST_KEY, post);
        mListener.showFragment(R.id.navigation_comments, bundle);
    }

    @Override
    public void onRefresh() {
        repositoryApp.getAllPostFeedLiveData();
    }
}