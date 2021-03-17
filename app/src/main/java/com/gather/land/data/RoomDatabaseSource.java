package com.gather.land.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gather.land.models.Comment;
import com.gather.land.models.GameRequestsPost;
import com.gather.land.models.StandardPost;


@Database(entities = {StandardPost.class,GameRequestsPost.class,Comment.class},version =8,exportSchema = false)
public abstract class RoomDatabaseSource extends RoomDatabase {

    private static RoomDatabaseSource roomDatabase;
    private static final String DATABASE_NAME = "GatherLandDatabase";

    public abstract ICommentDao getCommentDao();
    public abstract IStandardPostDao getStandardPostDao();
    public abstract IGameRequestPostDao geIGameRequestPostDao();


    public static RoomDatabaseSource getInstance(Context context) {
        if (roomDatabase == null) {
            roomDatabase = Room
                    .databaseBuilder(context, RoomDatabaseSource.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomDatabase;
    }

}
