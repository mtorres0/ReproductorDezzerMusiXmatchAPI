package com.telstock.tmanager.proyectocursoandroid.adaptadores;

import android.view.View;

import com.telstock.tmanager.proyectocursoandroid.objetos.Album;
import com.telstock.tmanager.proyectocursoandroid.objetos.Artist;
import com.telstock.tmanager.proyectocursoandroid.objetos.Cancion;

/**
 * Created by usr_micro9 on 8/07/16.
 */
public interface RecyclerViewOnItemClickListener {
    void onItemClick(View v, Cancion cancion);
    void onItemAlbumClick(View v, Album album);
    void onItemArtistClick(View v, Artist artist);
}
