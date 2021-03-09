package com.gather.land.interfaces;

import com.gather.land.models.Comment;

public interface ICallBackCommentAdapter {
    void onRefresh(   );
    void onShowEditCommentDialog(Comment comment);
}
