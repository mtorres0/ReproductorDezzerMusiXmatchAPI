package com.telstock.tmanager.proyectocursoandroid.objetos;

import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by usr_micro9 on 1/08/16.
 */
@Table
public class Cancion implements Serializable{

    @com.google.gson.annotations.SerializedName("id")
    private Long id;
    @com.google.gson.annotations.SerializedName("readable")
    private boolean readable;
    @com.google.gson.annotations.SerializedName("title")
    private String title;
    @com.google.gson.annotations.SerializedName("title_short")
    private String titleShort;
    @com.google.gson.annotations.SerializedName("title_version")
    private String titleVersion;
    @com.google.gson.annotations.SerializedName("link")
    private String link;
    @com.google.gson.annotations.SerializedName("duration")
    private int duration;
    @com.google.gson.annotations.SerializedName("rank")
    private int rank;
    @com.google.gson.annotations.SerializedName("explicit_lyrics")
    private boolean explicitLyrics;
    @com.google.gson.annotations.SerializedName("preview")
    private String preview;
    @com.google.gson.annotations.SerializedName("artist")
    private Artist artist;
    @com.google.gson.annotations.SerializedName("album")
    private Album album;
    @com.google.gson.annotations.SerializedName("type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public String getTitleVersion() {
        return titleVersion;
    }

    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
