package com.gather.land.interfaces;

import com.gather.land.models.StandardPost;

public interface ICallBackFeedAdapter {
    void onItemClickListener(StandardPost post);
    void onRefresh(StandardPost post);

}
