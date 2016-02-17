package com.example.perev.playingwithsqlitelecture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.perev.playingwithsqlitelecture.Episode;
import com.example.perev.playingwithsqlitelecture.Show;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by perev on 17/02/2016.
 */
public class ShowDataSource {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    private String[] allShowColumns = {
            ShowTable.COLUMN_ID,
            ShowTable.COLUMN_TITLE,
            ShowTable.COLUMN_YEAR,
            ShowTable.COLUMN_IMDB_ID
    };

    private String[] allEpisodeColumns = {
            EpisodeTable.COLUMN_ID,
            EpisodeTable.COLUMN_SHOW_ID,
            EpisodeTable.COLUMN_TITLE,
            EpisodeTable.COLUMN_EPISODE,
            EpisodeTable.COLUMN_SEASON,
            EpisodeTable.COLUMN_OVERVIEW
    };

    public ShowDataSource(Context context){
        dbHelper = new SQLiteHelper(context);
    }

    public Show createShow(String title, int year, String imdbId){
        ContentValues values = new ContentValues();
        values.put(ShowTable.COLUMN_TITLE, title);
        values.put(ShowTable.COLUMN_YEAR, year);
        values.put(ShowTable.COLUMN_IMDB_ID, imdbId);

        long insertId = database.insert(ShowTable.TABLE_SHOW, null, values);

        return getShow(insertId);
    }

    private Show cursorToShow(Cursor cursor){
        Show show = new Show();
        show.setId(cursor.getInt(0));
        show.setTitle(cursor.getString(1));
        show.setYear(cursor.getInt(2));
        show.setImdbId(cursor.getString(3));

        return show;
    }

    public Show getShow(long id){
        Cursor cursor = database.query(ShowTable.TABLE_SHOW,
                allShowColumns,
                ShowTable.COLUMN_ID + " = " + id,
                null, null, null, null
        );

        cursor.moveToFirst();
        Show show = cursorToShow(cursor);
        cursor.close();

        return show;
    }

    public List<Show> getAllShows(){
        List<Show> shows = new ArrayList<>();

        Cursor cursor = database.query(ShowTable.TABLE_SHOW,
                allShowColumns,
                null, null, null, null, null
        );

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Show show = cursorToShow(cursor);
            shows.add(show);
            cursor.moveToNext();
        }

        cursor.close();

        return shows;
    }

    private Episode cursorToEpisode(Cursor cursor){
        Episode episode = new Episode();
        episode.setId(cursor.getInt(0));
        episode.setShowId(cursor.getInt(1));
        episode.setTitle(cursor.getString(2));
        episode.setEpisode(cursor.getInt(3));
        episode.setSeason(cursor.getInt(4));
        episode.setOverview(cursor.getString(5));

        return episode;
    }

    public Episode createEpisode(long showId, String title, int episode, int season, String overview){
        ContentValues values = new ContentValues();
        values.put(EpisodeTable.COLUMN_SHOW_ID, showId);
        values.put(EpisodeTable.COLUMN_TITLE, title);
        values.put(EpisodeTable.COLUMN_EPISODE, episode);
        values.put(EpisodeTable.COLUMN_SEASON, season);
        values.put(EpisodeTable.COLUMN_OVERVIEW, overview);

        long insertId = database.insert(EpisodeTable.TABLE_EPISODE, null, values);

        Cursor cursor = database.query(EpisodeTable.TABLE_EPISODE,
                allEpisodeColumns,
                EpisodeTable.COLUMN_ID + " = " + insertId,
                null, null, null, null
        );
        cursor.moveToFirst();
        Episode newEpisode = cursorToEpisode(cursor);
        cursor.close();

        return newEpisode;
    }

    public List<Episode> getAllEpisodes(int showId){
        List<Episode> episodes = new ArrayList<>();

        Cursor cursor = database.query(EpisodeTable.TABLE_EPISODE,
                allEpisodeColumns,
                EpisodeTable.COLUMN_SHOW_ID + " = " + showId,
                null, null, null, null
        );

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Episode episode = cursorToEpisode(cursor);
            episodes.add(episode);
            cursor.moveToNext();
        }
        cursor.close();

        return episodes;
    }

    public void deleteEpisode(Episode episode){
        long id = episode.getId();
        database.delete(EpisodeTable.TABLE_EPISODE, EpisodeTable.COLUMN_ID + " = " + id, null);
    }

    public void deleteShow(Show show){
        int id = show.getId();

        List<Episode> episodes = getAllEpisodes(show.getId());

        for (Episode episode : episodes){
            deleteEpisode(episode);
        }

        database.delete(ShowTable.TABLE_SHOW, ShowTable.COLUMN_ID + " = " + id, null);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }
}









