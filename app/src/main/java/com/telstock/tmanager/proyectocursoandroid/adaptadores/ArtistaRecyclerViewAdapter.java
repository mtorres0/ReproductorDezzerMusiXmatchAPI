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
import com.telstock.tmanager.proyectocursoandroid.objetos.Artist;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usr_micro9 on 8/07/16.
 */
public class ArtistaRecyclerViewAdapter extends RecyclerView.Adapter<ArtistaRecyclerViewAdapter.ViewHolder>{

    Context context;

    public List<Artist> artistasList;
    private static RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public ArtistaRecyclerViewAdapter(Context context, List<Artist> artistasList, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.artistasList = artistasList;
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
                    Artist artist = (Artist) v.getTag(R.id.cardview_vista);
                    recyclerViewOnItemClickListener.onItemArtistClick(v,artist);
                    break;
            }
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArtistaRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cancion_cardview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombreCancion.setText(artistasList.get(position).getName());
        holder.nombreArtista.setText(artistasList.get(position).getType());
        holder.nombreAlbum.setText(artistasList.get(position).getLink());
        Glide.with(context).load(artistasList.get(position).getPictureBig()).into(holder.ivImagenCancion);
        holder.cardViewCancion.setTag(R.id.cardview_vista,artistasList.get(position));
    }

    @Override
    public int getItemCount() {
        return artistasList.size();
    }
}
