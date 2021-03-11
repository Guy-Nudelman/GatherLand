package com.gather.land.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import com.gather.land.models.Comment;

public interface ICallBackCommentAdapter {
    void onRefresh();

    void onShowEditCommentDialog(Comment comment);

}
