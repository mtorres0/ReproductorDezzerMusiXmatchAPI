package com.telstock.tmanager.proyectocursoandroid.objetos;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by usr_micro9 on 1/08/16.
 */
@Table
public class Album implements Serializable {

    @com.google.gson.annotations.SerializedName("id")
    private Long id;
    @com.google.gson.annotations.SerializedName("title")
    private String title;
    @com.google.gson.annotations.SerializedName("cover")
    private String cover;
    @com.google.gson.annotations.SerializedName("cover_small")
    private String coverSmall;
    @com.google.gson.annotations.SerializedName("cover_medium")
    private String coverMedium;
    @com.google.gson.annotations.SerializedName("cover_big")
    private String coverBig;
    @com.google.gson.annotations.SerializedName("cover_xl")
    private String coverXl;
    @com.google.gson.annotations.SerializedName("tracklist")
    private String tracklist;
    @com.google.gson.annotations.SerializedName("type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public String getCoverXl() {
        return coverXl;
    }

    public void setCoverXl(String coverXl) {
        this.coverXl = coverXl;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    List<Cancion> getCanciones() {
        return SugarRecord.find(Cancion.class, "album = ?", String.valueOf(getId()));
    }

}
