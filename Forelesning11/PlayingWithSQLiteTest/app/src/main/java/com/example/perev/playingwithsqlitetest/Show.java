package com.example.perev.playingwithsqlitetest;

/**
 * Created by perev on 15/02/2016.
 */
public class Show {
    private int id;
    private String title;
    private int year;
    private String imdbId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public String toString() {
        return title + " - " + year;
    }
}
