package com.telstock.tmanager.proyectocursoandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.SugarRecord;
import com.telstock.tmanager.proyectocursoandroid.R;
import com.telstock.tmanager.proyectocursoandroid.adaptadores.ArtistaRecyclerViewAdapter;
import com.telstock.tmanager.proyectocursoandroid.adaptadores.RecyclerViewOnItemClickListener;
import com.telstock.tmanager.proyectocursoandroid.objetos.Album;
import com.telstock.tmanager.proyectocursoandroid.objetos.Artist;
import com.telstock.tmanager.proyectocursoandroid.objetos.Cancion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usr_micro9 on 3/08/16.
 */
public class ArtistasFavoritosFragment extends Fragment implements RecyclerViewOnItemClickListener {

    @BindView(R.id.rv_lista)
    RecyclerView rvLista;

    RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lista_recyclerview,container, false);

        ButterKnife.bind(this,rootView);

        List<Artist> artistList = new ArrayList<>();
        mAdapter = new ArtistaRecyclerViewAdapter(getActivity(), artistList, this);
        rvLista.setAdapter(mAdapter);
        rvLista.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLista.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        obtenerCanciones();
    }

    public void obtenerCanciones(){
        List<Artist> artistList = SugarRecord.listAll(Artist.class);
        mAdapter = new ArtistaRecyclerViewAdapter(getActivity(), artistList, this);
        rvLista.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View v, Cancion cancion) {
    }

    @Override
    public void onItemAlbumClick(View v, Album album) {

    }

    @Override
    public void onItemArtistClick(View v, Artist artist) {

    }
}