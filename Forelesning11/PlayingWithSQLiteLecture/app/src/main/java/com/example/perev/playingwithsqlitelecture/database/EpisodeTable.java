package com.example.perev.playingwithsqlitelecture.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by perev on 17/02/2016.
 */
public class EpisodeTable {
    public static final String TABLE_EPISODE = "episode";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SHOW_ID = "show_id";
    public static final String COLUMN_TITLE = "name";
    public static final String COLUMN_EPISODE = "episode";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_OVERVIEW = "overview";

    public static final String DATABASE_CREATE_EPISODE =
            "create table " + TABLE_EPISODE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_SHOW_ID + " integer not null, " +
                    COLUMN_TITLE + " text not null, " +
                    COLUMN_EPISODE + " integer not null, " +
                    COLUMN_SEASON + " integer not null, " +
                    COLUMN_OVERVIEW + " text" + ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE_EPISODE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EPISODE);
        onCreate(database);
    }
}











