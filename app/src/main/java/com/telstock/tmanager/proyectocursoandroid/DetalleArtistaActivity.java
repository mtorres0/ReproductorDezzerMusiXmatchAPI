package com.telstock.tmanager.proyectocursoandroid;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.telstock.tmanager.proyectocursoandroid.objetos.Artist;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleArtistaActivity extends AppCompatActivity {

    public static final String EXTRA_BUNDLE = "Artista";

    private Context context;

    //Elementos Interfaz de usuario.
    @BindView(R.id.toolbar_artista)
    Toolbar toolbar;

    @BindView(R.id.iv_artista)
    ImageView ivArtista;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_artista);

        context = this;

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setContentScrimColor(Color.BLUE);
        collapsingToolbarLayout.setStatusBarScrimColor(Color.BLUE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            artist = (Artist) bundle.getSerializable(EXTRA_BUNDLE);
            if(artist != null){
                collapsingToolbarLayout.setTitle(artist.getName());
                Glide.with(context).load(artist.getPictureBig())
                        .centerCrop()
                        .fitCenter()
                        .into(ivArtista);
            }
        }

    }
}
