package com.tequila.testapp.models;

public class ItemList {

    private String artist;
    private String title;
    private String coverUrl;

    public ItemList(String artist, String title, String coverUrl) {
        this.artist = artist;
        this.title = title;
        this.coverUrl = coverUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}
