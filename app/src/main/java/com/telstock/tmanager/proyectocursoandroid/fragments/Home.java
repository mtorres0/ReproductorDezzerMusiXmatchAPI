package com.telstock.tmanager.proyectocursoandroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SugarRecord;
import com.telstock.tmanager.proyectocursoandroid.LetraCancionActivity;
import com.telstock.tmanager.proyectocursoandroid.R;
import com.telstock.tmanager.proyectocursoandroid.adaptadores.CancionesRecyclerViewAdapter;
import com.telstock.tmanager.proyectocursoandroid.adaptadores.RecyclerViewOnItemClickListener;
import com.telstock.tmanager.proyectocursoandroid.objetos.Album;
import com.telstock.tmanager.proyectocursoandroid.objetos.Artist;
import com.telstock.tmanager.proyectocursoandroid.objetos.Cancion;
import com.telstock.tmanager.proyectocursoandroid.objetos.Data;
import com.telstock.tmanager.proyectocursoandroid.webservice.ApiUrl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usr_micro9 on 3/08/16.
 */
public class Home extends Fragment implements RecyclerViewOnItemClickListener{

    public static final String TAG = "MainActivity";

    private Context context;

    // Elementos de la interfaz de usuario.
    @BindView(R.id.rv_lista_canciones)
    RecyclerView rvListaCanciones;

    //Adaptador para la lista de canciones.
    private RecyclerView.Adapter mAdapter;

    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main,container, false);

        ButterKnife.bind(this,rootView);

        context = getActivity();

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Log.d(TAG,"subscribe to news topic");
        Log.d(TAG,"Instance ID token: " + FirebaseInstanceId.getInstance().getToken());

        List<Cancion> canciones = new ArrayList<>();
        mAdapter = new CancionesRecyclerViewAdapter(context, canciones, this);
        rvListaCanciones.setAdapter(mAdapter);
        rvListaCanciones.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListaCanciones.setHasFixedSize(true);

        obtenerResultadoBusqueda("the strokes",Home.this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                searchItem.collapseActionView();
                if( query.length() > 0 ){
                    obtenerResultadoBusqueda(query,Home.this);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void obtenerResultadoBusqueda(String busqueda, final RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        String url = ApiUrl.API_URL + ApiUrl.busqueda + busqueda.replace(" ","+");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA_VOLLEY", response);

                        try {
                            Type listofResponses = new TypeToken<Data>() {
                            }.getType();

                            Gson gson = new Gson();

                            final Data data = gson.fromJson(response, listofResponses);

                            mAdapter = new CancionesRecyclerViewAdapter(context,data.getCancion(), recyclerViewOnItemClickListener);
                            rvListaCanciones.setAdapter(mAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_VOLLEY", error.toString());
                if(error instanceof NoConnectionError){
                    Snackbar.make(getView(), R.string.no_connection_error, Snackbar.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar.make(getView(), R.string.time_out_error, Snackbar.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-Mashape-Key", "CSKjoBa8EYmshpjeibFZ8KnW0ESBp171epQjsnK9BC0NsjZMx6");
                headers.put("Accept", "text/plain");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };

        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjRequest.setShouldCache(false);
        Volley.newRequestQueue(context).add(jsonObjRequest);
    }

    @Override
    public void onItemClick(View v, Cancion cancion) {
        Intent intent = new Intent(context, LetraCancionActivity.class);
        intent.putExtra(LetraCancionActivity.EXTRA_BUNDLE, cancion);
        startActivity(intent);
    }

    @Override
    public void onItemAlbumClick(View v, Album album) {

    }

    @Override
    public void onItemArtistClick(View v, Artist artist) {

    }
}
