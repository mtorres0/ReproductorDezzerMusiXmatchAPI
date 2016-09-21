package com.telstock.tmanager.proyectocursoandroid.objetos;

/**
 * Created by usr_micro9 on 1/08/16.
 */
public class Letra {

    @com.google.gson.annotations.SerializedName("lyrics_id")
    private int lyricsId;
    @com.google.gson.annotations.SerializedName("can_edit")
    private int canEdit;
    @com.google.gson.annotations.SerializedName("locked")
    private int locked;
    @com.google.gson.annotations.SerializedName("action_requested")
    private String actionRequested;
    @com.google.gson.annotations.SerializedName("verified")
    private int verified;
    @com.google.gson.annotations.SerializedName("restricted")
    private boolean restricted;
    @com.google.gson.annotations.SerializedName("instrumental")
    private boolean instrumental;
    @com.google.gson.annotations.SerializedName("explicit")
    private int explicit;
    @com.google.gson.annotations.SerializedName("lyrics_body")
    private String lyricsBody;
    @com.google.gson.annotations.SerializedName("lyrics_language")
    private String lyricsLanguage;
    @com.google.gson.annotations.SerializedName("lyrics_language_description")
    private String lyricsLanguageDescription;
    @com.google.gson.annotations.SerializedName("script_tracking_url")
    private String scriptTrackingUrl;
    @com.google.gson.annotations.SerializedName("pixel_tracking_url")
    private String pixelTrackingUrl;
    @com.google.gson.annotations.SerializedName("html_tracking_url")
    private String htmlTrackingUrl;
    @com.google.gson.annotations.SerializedName("lyrics_copyright")
    private String lyricsCopyright;

    public int getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(int lyricsId) {
        this.lyricsId = lyricsId;
    }

    public int getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(int canEdit) {
        this.canEdit = canEdit;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getActionRequested() {
        return actionRequested;
    }

    public void setActionRequested(String actionRequested) {
        this.actionRequested = actionRequested;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean isInstrumental() {
        return instrumental;
    }

    public void setInstrumental(boolean instrumental) {
        this.instrumental = instrumental;
    }

    public int getExplicit() {
        return explicit;
    }

    public void setExplicit(int explicit) {
        this.explicit = explicit;
    }

    public String getLyricsBody() {
        return lyricsBody;
    }

    public void setLyricsBody(String lyricsBody) {
        this.lyricsBody = lyricsBody;
    }

    public String getLyricsLanguage() {
        return lyricsLanguage;
    }

    public void setLyricsLanguage(String lyricsLanguage) {
        this.lyricsLanguage = lyricsLanguage;
    }

    public String getLyricsLanguageDescription() {
        return lyricsLanguageDescription;
    }

    public void setLyricsLanguageDescription(String lyricsLanguageDescription) {
        this.lyricsLanguageDescription = lyricsLanguageDescription;
    }

    public String getScriptTrackingUrl() {
        return scriptTrackingUrl;
    }

    public void setScriptTrackingUrl(String scriptTrackingUrl) {
        this.scriptTrackingUrl = scriptTrackingUrl;
    }

    public String getPixelTrackingUrl() {
        return pixelTrackingUrl;
    }

    public void setPixelTrackingUrl(String pixelTrackingUrl) {
        this.pixelTrackingUrl = pixelTrackingUrl;
    }

    public String getHtmlTrackingUrl() {
        return htmlTrackingUrl;
    }

    public void setHtmlTrackingUrl(String htmlTrackingUrl) {
        this.htmlTrackingUrl = htmlTrackingUrl;
    }

    public String getLyricsCopyright() {
        return lyricsCopyright;
    }

    public void setLyricsCopyright(String lyricsCopyright) {
        this.lyricsCopyright = lyricsCopyright;
    }
}
