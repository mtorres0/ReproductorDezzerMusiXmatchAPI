package com.telstock.tmanager.proyectocursoandroid;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SugarRecord;
import com.telstock.tmanager.proyectocursoandroid.adaptadores.CancionesRecyclerViewAdapter;
import com.telstock.tmanager.proyectocursoandroid.adaptadores.RecyclerViewOnItemClickListener;
import com.telstock.tmanager.proyectocursoandroid.objetos.Artist;
import com.telstock.tmanager.proyectocursoandroid.objetos.Cancion;
import com.telstock.tmanager.proyectocursoandroid.objetos.Data;
import com.telstock.tmanager.proyectocursoandroid.objetos.Letra;
import com.telstock.tmanager.proyectocursoandroid.webservice.ApiUrl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LetraCancionActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, MediaPlayer.OnCompletionListener {

    public static final String EXTRA_BUNDLE = "Cancion";

    private Context context;

    //Elementos Interfaz de usuario.
    @BindView(R.id.toolbar_letra)
    Toolbar toolbar;

    @BindView(R.id.iv_artista)
    ImageView ivArtista;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.tv_letra)
    TextView tvLetra;

    @BindView(R.id.cordi_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.btn_reproducir)
    Button btnReproducir;

    @BindView(R.id.btn_adelantar)
    Button btnAdelantar;

    @BindView(R.id.btn_pausar)
    Button btnPausar;

    @BindView(R.id.btn_retroceder)
    Button btnRetroceder;

    @BindView(R.id.seekbar)
    SeekBar seekBar;

    @BindView(R.id.txtinfo1)
    TextView tvInfo1;
    @BindView(R.id.txtinfo2)
    TextView tvInfo2;
    @BindView(R.id.txtinfo3)
    TextView tvInfo3;

    @BindView(R.id.btn_favorito)
    FloatingActionButton btnFav;

    MediaPlayer mediaPlayer;
    double startTime = 0;
    double finalTime = 0;
    private int forwardTime = 5000;
    private  int backwardTieme =  5000;
    private Handler myHandler = new Handler();

    //Comportamiento de la vista inferior
    private BottomSheetBehavior<View> behavior;
    private BottomSheetDialog dialog;

    Cancion cancion;

    private Boolean bFavorito = false;

    private Animation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_letra_cancion);

        context = this;

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initBottomSheet();

        collapsingToolbarLayout.setContentScrimColor(Color.BLUE);
        collapsingToolbarLayout.setStatusBarScrimColor(Color.BLUE);

        //Establecer la animación de rotar;
        rotate = AnimationUtils.loadAnimation(context, R.anim.fab_rotate);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            cancion = (Cancion) bundle.getSerializable(EXTRA_BUNDLE);
            if(cancion != null){
                collapsingToolbarLayout.setTitle(cancion.getTitle());
                Glide.with(context).load(cancion.getAlbum().getCoverBig())
                        .centerCrop()
                        .fitCenter()
                        .into(ivArtista);
                obtenerLetraCancion(cancion.getArtist().getName(), cancion.getTitle());

                Cancion cancion1 = SugarRecord.findById(Cancion.class, cancion.getId());
                if(cancion1 != null){
                    btnFav.setImageResource(R.drawable.ic_star_white_48dp);
                    bFavorito = true;
                } else {
                    btnFav.setImageResource(R.drawable.ic_star_border_white_48dp);
                    bFavorito = false;
                }
            }
        }

        btnPausar.setOnClickListener(this);
        btnReproducir.setOnClickListener(this);
        btnRetroceder.setOnClickListener(this);
        btnAdelantar.setOnClickListener(this);
        btnFav.setOnClickListener(this);

        seekBar.setOnTouchListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    private void initBottomSheet() {
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_EXPANDED:
                        btnFav.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        btnFav.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        btnFav.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
    }


    public void obtenerLetraCancion(String artista, String cancion) {
        String url = ApiUrl.API_URL_MUSIXMATCH + ApiUrl.obtenerLetra
                + ApiUrl.paramArtist + artista.replace(" ","+")
                + ApiUrl.paramCancion + cancion.replace(" ","+");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA_VOLLEY", response);

                        try {
                            Type typeResponse = new TypeToken<Letra>() {
                            }.getType();

                            Gson gson = new Gson();

                            final Letra letra = gson.fromJson(response, typeResponse);
                            tvLetra.setText(letra.getLyricsBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_VOLLEY", error.toString());
                if(error instanceof NoConnectionError){
                    Snackbar.make(collapsingToolbarLayout, R.string.no_connection_error, Snackbar.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar.make(collapsingToolbarLayout, R.string.time_out_error, Snackbar.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-Mashape-Key", "CSKjoBa8EYmshpjeibFZ8KnW0ESBp171epQjsnK9BC0NsjZMx6");
                headers.put("Accept", "application/json");
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
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btn_adelantar:
                    int temp = (int) startTime;
                    if ((temp + forwardTime) <= finalTime) {
                        startTime = startTime + forwardTime;
                        mediaPlayer.seekTo((int) startTime);
                    }

                    break;
                case R.id.btn_pausar:
                    mediaPlayer.pause();
                    btnPausar.setEnabled(true);
                    break;
                case R.id.btn_reproducir:

                    if (cancion.getPreview() != null && !cancion.getPreview().isEmpty()) {
                        try {
                            mediaPlayer.setDataSource(cancion.getPreview());
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {

                        }

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();
                        seekBar.setMax((int) finalTime);

                        if (!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                        }

                        btnPausar.setEnabled(true);
                        btnReproducir.setEnabled(false);
                        tvInfo3.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                        );

                        tvInfo1.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                        );

                        seekBar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateSongTime, 100);
                    }

                    break;
                case R.id.btn_retroceder:

                    int temp2 = (int) startTime;
                    if ((temp2 - backwardTieme) > 0) {
                        startTime = startTime - backwardTieme;
                        mediaPlayer.seekTo((int) startTime);
                    }

                    break;
                case R.id.btn_favorito:
                    btnFav.startAnimation(rotate);
                    if (!bFavorito) {
                        guardar(cancion);
                        btnFav.setImageResource(R.drawable.ic_star_white_48dp);
                        bFavorito = true;
                        Snackbar.make(collapsingToolbarLayout, "Se agregó a tu lista de favoritos.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        eliminar(cancion);
                        btnFav.setImageResource(R.drawable.ic_star_border_white_48dp);
                        bFavorito = false;
                        Snackbar.make(collapsingToolbarLayout, "Se eliminó de tu lista de favoritos.", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }catch (Exception e){
            Log.e("",e.toString());
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tvInfo1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this,100);
        }
    };

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        btnReproducir.setEnabled(true);
        btnPausar.setEnabled(false);
        seekBar.setProgress(0);
        tvInfo1.setText("");
        tvInfo3.setText("");

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.seekbar){
            if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar) view;
                mediaPlayer.seekTo(sb.getProgress());
            }
        }
        return false;
    }

    public void guardar(Cancion cancion){
        SugarRecord.save(cancion);
        SugarRecord.save(cancion.getAlbum());
        SugarRecord.save(cancion.getArtist());
    }

    public void eliminar(Cancion cancion){
        SugarRecord.delete(cancion);
    }
}
