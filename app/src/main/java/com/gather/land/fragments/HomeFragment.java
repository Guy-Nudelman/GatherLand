package com.gather.land.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import java.util.List;

public class HomeFragment extends Fragment implements ICallBackFeedAdapter {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewFeed;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFeed=view.findViewById(R.id.recyclerViewFeed);
        recyclerViewFeed.setHasFixedSize(true);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerViewFeed.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));


        RepositoryApp.getInstance(getContext()).getAllPostFeedLiveData().observe(getViewLifecycleOwner(), new Observer<List<StandardPost>>() {
            @Override
            public void onChanged(List<StandardPost> postList) {
                initListPost(postList);
            }
        });

    }

    private void initListPost(List<StandardPost> postList) {
        AdapterFeed adapterFeed=new AdapterFeed(postList,getContext(),this);
        recyclerViewFeed.setAdapter(adapterFeed);
    }

    @Override
    public void onItemClickListener(StandardPost post) {
        Toast.makeText(getContext(), post.getTitle(), Toast.LENGTH_SHORT).show();
    }
}