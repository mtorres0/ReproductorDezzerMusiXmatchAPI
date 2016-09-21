package com.telstock.tmanager.proyectocursoandroid.servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.telstock.tmanager.proyectocursoandroid.LetraCancionActivity;
import com.telstock.tmanager.proyectocursoandroid.R;
import com.telstock.tmanager.proyectocursoandroid.objetos.Cancion;
import com.telstock.tmanager.proyectocursoandroid.webservice.ApiUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by usr_micro9 on 2/08/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public final static String TAG = "FirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        Log.d(TAG,"From: "+ remoteMessage.getFrom());
        Log.d(TAG,"Notificacion Message Body: " + remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage){
        Map<String,String> map = remoteMessage.getData();

        String id = map.get("id");
        if(id != null && !id.isEmpty()) {
            obtenerLetraCancion(id);
        }
    }

    public void makeNotificacion(Cancion cancion){
        Intent intent = new Intent(this, LetraCancionActivity.class);
        intent.putExtra(LetraCancionActivity.EXTRA_BUNDLE,cancion);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /*Request Code*/,
                intent, PendingIntent.FLAG_ONE_SHOT);

        //Se obtiene el tono de notificaciones por defecto.
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Se crea la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Canción")
                .setContentText(cancion.getTitle())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        //Se llama al servicio de las notificaciones
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /*id notificación*/,builder.build());
    }

    public void obtenerLetraCancion(String id) {
        String url = ApiUrl.API_URL + ApiUrl.obtenerCancion + id;

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA_VOLLEY", response);

                        try {
                            Type typeResponse = new TypeToken<Cancion>() {
                            }.getType();

                            Gson gson = new Gson();

                            final Cancion cancion = gson.fromJson(response, typeResponse);
                            if(cancion != null){
                                makeNotificacion(cancion);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_VOLLEY", error.toString());
                if(error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(), R.string.no_connection_error, Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), R.string.time_out_error, Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjRequest);
    }
}

