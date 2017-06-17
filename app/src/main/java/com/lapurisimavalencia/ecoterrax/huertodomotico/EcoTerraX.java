package com.lapurisimavalencia.ecoterrax.huertodomotico;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lapurisimavalencia.ecoterrax.huertodomotico.Modelo.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class EcoTerraX extends AppCompatActivity {

    private String TAG = EcoTerraX.class.getSimpleName(); // Para identificar los errores en el LOG
    private ProgressDialog pDialog;
    private TextView tvIdHuertoValor;
    private TextView tvNombreValor;
    private TextView tvLocalizacionValor;
    private TextView tvTempAmbValor;
    private TextView tvHumAmbValor;
    private TextView tvHumHuertoValor;
    private TextView tvTotalRiegosValor;

    // URL to get contacts JSON
    // private static String url = "http://api.androidhive.info/contacts/";
    private static String url = "http://192.168.1.36/ecoterrax/modelo/obtenerDetalleHuerto.php?idHuerto=2";
    ArrayList<HashMap<String, String>> listaHuertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_terra_x);

        tvIdHuertoValor = (TextView)findViewById(R.id.tvIdHuertoValor);
        tvNombreValor = (TextView)findViewById(R.id.tvNombreValor);
        tvLocalizacionValor = (TextView)findViewById(R.id.tvLocalizacionValor);
        tvTempAmbValor = (TextView)findViewById(R.id.tvTempAmbValor);
        tvHumAmbValor = (TextView)findViewById(R.id.tvHumAmbValor);
        tvHumHuertoValor = (TextView)findViewById(R.id.tvHumHuertoValor);
        tvTotalRiegosValor = (TextView)findViewById(R.id.tvTotalRiegosValor);
        GetHuertos getHuertos = new GetHuertos();
        getHuertos.execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetHuertos extends AsyncTask<Void, Void, Void> {

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

                        //idHuerto = h.getString("idHuerto");

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
            tvTempAmbValor.setText(tempAmbiente);
            tvHumAmbValor.setText(humAmbiente);
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
}
