package com.lapurisimavalencia.ecoterrax.huertodomotico;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lapurisimavalencia.ecoterrax.huertodomotico.Modelo.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


public class EcoTerraX extends AppCompatActivity {

    private String TAG = EcoTerraX.class.getSimpleName(); // Para identificar los errores en el LOG
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private ProgressDialog pDialog;
    private Button btConectar;
    private EditText etServidor;
    private TextView tvIdHuertoValor;
    private TextView tvNombreValor;
    private TextView tvLocalizacionValor;
    private TextView tvTempAmbValor;
    private TextView tvHumAmbValor;
    private TextView tvHumHuertoValor;
    private TextView tvEstadoHuertoValor;
    private TextView tvTotalRiegosValor;
    AsyncTaskGetHuertos jsonTask;
    Timer timer;
    TimerTask task;

    // URL to get contacts JSON
    // private static String url = "http://api.androidhive.info/contacts/";
    // URL wifi de casa
    //private static String url = "http://192.168.1.36/ecoterrax/modelo/obtenerDetalleHuerto.php?idHuerto=2";

    // URL wifi de Colegio La Purísima Valencia
    private static String url;

    ArrayList<HashMap<String, String>> listaHuertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_terra_x);

        btConectar = (Button) findViewById(R.id.btConectarServidor);
        etServidor = (EditText) findViewById(R.id.etIpServidorValor);
        tvIdHuertoValor = (TextView) findViewById(R.id.tvIdHuertoValor);
        tvNombreValor = (TextView) findViewById(R.id.tvNombreValor);
        tvLocalizacionValor = (TextView) findViewById(R.id.tvLocalizacionValor);
        tvTempAmbValor = (TextView) findViewById(R.id.tvTempAmbValor);
        tvHumAmbValor = (TextView) findViewById(R.id.tvHumAmbValor);
        tvHumHuertoValor = (TextView) findViewById(R.id.tvHumHuertoValor);
        tvEstadoHuertoValor = (TextView) findViewById(R.id.tvEstadoHuertoValor);
        tvTotalRiegosValor = (TextView) findViewById(R.id.tvTotalRiegosValor);

        btConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etServidor.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Error al conectar con el servidor",Toast.LENGTH_LONG).show();
                else{
                    etServidor.clearFocus();
                    String ip = etServidor.getText().toString();
                    if(EcoTerraX.validate(ip)) {
                        url = "http://" + ip + "/ecoterrax/modelo/obtenerDetalleHuerto.php?idHuerto=2";
                        setRepeatingAsyncTask();
                    }else
                        Toast.makeText(getApplicationContext(), "ERROR: Dirección IP del servidor incorrecta.",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    private void setRepeatingAsyncTask() {

        final Handler handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            jsonTask = new AsyncTaskGetHuertos();
                            jsonTask.execute();
                        } catch (Exception e) {
                            // error, do something
                            Toast.makeText(getApplicationContext(), "ERROR al conectar con el servidor: "+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        // Actualizamos cada minuto ya que el tiempo mínimo de refresco del script en python es cada 60 segundos
        timer.schedule(task, 0, 60*1000);  // interval of 60 seconds

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class AsyncTaskGetHuertos extends AsyncTask<Void, Void, Void> {

        String idHuerto;
        String nombre;
        String localizacion;
        String descripcion;
        String tempAmbiente;
        String humAmbiente;
        String humHuerto;
        String totalRiegos;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(EcoTerraX.this);
            pDialog.setMessage("Espere por favor...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String huertoJson = jsonObj.getString("huerto");
                    // Getting JSON Array node. Por si tenemos varios huertos.
                    //JSONArray huertos = jsonObj.getJSONArray("huerto");
                    //Log.e(TAG, " AQUI:"+huertos);
                    // looping through All Contacts
                    //for (int i = 0; i < huertos.length(); i++) {

                        JSONObject h = new JSONObject(huertoJson);

                        idHuerto = h.getString("idHuerto");

                        //Log.e(TAG, " AQUI:"+huer);
                        nombre = h.getString("nombre");
                        localizacion = h.getString("localizacion");
                        descripcion = h.getString("descripcion");
                        tempAmbiente = h.getString("temperatura_ambiente");
                        humAmbiente = h.getString("humedad_ambiente");
                        humHuerto = h.getString("humedad_huerto");
                        totalRiegos = h.getString("total_riegos");

                        // tmp hash map for single contact
                        HashMap<String, String> huerto = new HashMap<>();

                        // adding each child node to HashMap key => value
                        huerto.put("id", idHuerto);
                        huerto.put("nombre", nombre);
                        huerto.put("localizacion", localizacion);
                        huerto.put("descripcion", descripcion);
                        huerto.put("tempAmbiente", tempAmbiente);
                        huerto.put("humAmbiente", humAmbiente);
                        huerto.put("humHuerto", humHuerto);
                        huerto.put("totalRiegos", totalRiegos);

                        // adding contact to contact list
                        //listaHuertos.add(huerto);

                    //}
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            // PONER LOS DATOS LEIDOS DEL JSON EN SU TEXTVIEW CORRESPONDIENTE
            tvIdHuertoValor.setText(idHuerto);
            tvNombreValor.setText(nombre);
            tvLocalizacionValor.setText(localizacion);
            tvTempAmbValor.setText(tempAmbiente+"ºC");
            tvHumAmbValor.setText(humAmbiente+"%");

            if(Integer.parseInt(humHuerto)<=250) {
                tvHumHuertoValor.setBackgroundColor(Color.RED);
                tvEstadoHuertoValor.setBackgroundColor(Color.RED);
                tvEstadoHuertoValor.setText("Me ahogo!!!");
            }else if(Integer.parseInt(humHuerto)>600){
                tvHumHuertoValor.setBackgroundColor(Color.RED);
                tvEstadoHuertoValor.setBackgroundColor(Color.RED);
                tvEstadoHuertoValor.setText("Necesito agua!!!");
            }else {
                tvHumHuertoValor.setBackgroundColor(Color.GREEN);
                tvEstadoHuertoValor.setBackgroundColor(Color.GREEN);
                tvEstadoHuertoValor.setText("Estoy bien!!!");
            }
            tvHumHuertoValor.setText(humHuerto);
            tvTotalRiegosValor.setText(totalRiegos);

            /*
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[]{"name", "email",
                    "mobile"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});

            lv.setAdapter(adapter);
            */
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // Finalizamos todas las tareas en segundo plano.
        if(jsonTask!=null)
            jsonTask.cancel(true);
        if(timer!=null)
            timer.cancel();
        if(task!=null)
            task.cancel();
        this.finish();
    }
}
