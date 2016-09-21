package com.telstock.tmanager.proyectocursoandroid.adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.telstock.tmanager.proyectocursoandroid.R;
import com.telstock.tmanager.proyectocursoandroid.objetos.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usr_micro9 on 8/07/16.
 */
public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>{

    Context context;

    public List<Album> albumesList;
    private static RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AlbumRecyclerViewAdapter(Context context, List<Album> albumesList, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.albumesList = albumesList;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        @BindView(R.id.cancion_imagen) public ImageView ivImagenCancion;
        @BindView(R.id.cancion_nombre) public TextView nombreCancion;
        @BindView(R.id.cancion_artista) public TextView nombreArtista;
        @BindView(R.id.cancion_album) public TextView nombreAlbum;
        @BindView(R.id.cardview_vista) public CardView cardViewCancion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardViewCancion.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cardview_vista:
                    Album album = (Album) v.getTag(R.id.cardview_vista);
                    recyclerViewOnItemClickListener.onItemAlbumClick(v, album);
                    break;
            }
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cancion_cardview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombreCancion.setText(albumesList.get(position).getTitle());
        holder.nombreArtista.setText(albumesList.get(position).getTracklist());
        holder.nombreAlbum.setText(albumesList.get(position).getType());
        Glide.with(context).load(albumesList.get(position).getCoverBig()).into(holder.ivImagenCancion);
        holder.cardViewCancion.setTag(R.id.cardview_vista,albumesList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumesList.size();
    }
}
