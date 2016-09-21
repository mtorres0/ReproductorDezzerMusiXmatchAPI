package com.telstock.tmanager.proyectocursoandroid;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.telstock.tmanager.proyectocursoandroid.fragments.AlbumesFavoritosFragment;
import com.telstock.tmanager.proyectocursoandroid.fragments.ArtistasFavoritosFragment;
import com.telstock.tmanager.proyectocursoandroid.fragments.CancionesFavoritasFragment;
import com.telstock.tmanager.proyectocursoandroid.fragments.Home;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    private Context context;

    // Elementos de la interfaz de usuario.
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    //Adaptador para la lista de canciones.
    private RecyclerView.Adapter mAdapter;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_main);

        context = this;

        //Se hace la referencia de la iu.
        ButterKnife.bind(this);

        //Se agrega la toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Log.d(TAG,"subscribe to news topic");
        Log.d(TAG,"Instance ID token: " + FirebaseInstanceId.getInstance().getToken());

        //Se inicializa el Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Se agrega el escuchador para las acciones de cada opción dentro del Navigation Drawer.
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager fragmentAjustes = getFragmentManager();
            fragmentAjustes.beginTransaction()
                    .replace(R.id.content_frame, new Home())
                    .commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.buscar_musica));
        } else if (id == R.id.nav_cancion) {
            FragmentManager fragmentAjustes = getFragmentManager();
            fragmentAjustes.beginTransaction()
                    .replace(R.id.content_frame, new CancionesFavoritasFragment())
                    .commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.canciones_favoritas));
        } else if (id == R.id.nav_artista) {
            FragmentManager fragmentAjustes = getFragmentManager();
            fragmentAjustes.beginTransaction()
                    .replace(R.id.content_frame, new ArtistasFavoritosFragment())
                    .commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.artistas_favoritos));
        } else if (id == R.id.nav_album) {
            FragmentManager fragmentAjustes = getFragmentManager();
            fragmentAjustes.beginTransaction()
                    .replace(R.id.content_frame, new AlbumesFavoritosFragment())
                    .commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.albumes_favoritos));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
