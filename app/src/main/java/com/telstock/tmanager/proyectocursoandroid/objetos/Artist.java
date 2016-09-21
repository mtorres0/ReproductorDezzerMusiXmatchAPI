package com.telstock.tmanager.proyectocursoandroid.objetos;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by usr_micro9 on 1/08/16.
 */
@Table
public class Artist implements Serializable{
    @com.google.gson.annotations.SerializedName("id")
    private Long id;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("link")
    private String link;
    @com.google.gson.annotations.SerializedName("picture")
    private String picture;
    @com.google.gson.annotations.SerializedName("picture_small")
    private String pictureSmall;
    @com.google.gson.annotations.SerializedName("picture_medium")
    private String pictureMedium;
    @com.google.gson.annotations.SerializedName("picture_big")
    private String pictureBig;
    @com.google.gson.annotations.SerializedName("picture_xl")
    private String pictureXl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public String getPictureBig() {
        return pictureBig;
    }

    public void setPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
    }

    public String getPictureXl() {
        return pictureXl;
    }

    public void setPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
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
        return SugarRecord.find(Cancion.class, "artist = ?", String.valueOf(getId()));
    }
}
