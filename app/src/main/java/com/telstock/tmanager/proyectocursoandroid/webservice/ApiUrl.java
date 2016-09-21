package com.telstock.tmanager.proyectocursoandroid.webservice;

/**
 * Created by usr_micro9 on 1/08/16.
 */
public class ApiUrl {

    public static final String API_URL = "https://deezerdevs-deezer.p.mashape.com/";

    public static final String API_URL_MUSIXMATCH = "https://musixmatchcom-musixmatch.p.mashape.com/wsr/1.1/";
    /**
     * Métodos
     */

    public static final String busqueda = "search?q=";
    public static final String obtenerAlbum = "album/";
    public static final String obtenerArtista = "artist/";
    public static final String obtenerCancion = "track/";

    public static final String obtenerLetra = "matcher.lyrics.get";
    public static final String paramArtist = "?q_artist=";
    public static final String paramCancion = "&q_track=";
}
