package com.example.torddis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.torddis.interfaces.APIBase;
import com.example.torddis.models.UsuarioLogeado;
import com.example.torddis.webService.Asynchtask;
import com.example.torddis.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActEntrenar extends AppCompatActivity implements Asynchtask {
    WebView webvideo;
    Switch swtTransmision;
    ProgressDialog progDailog;
    String direccion_ruta = "";
    int idSupervisado = 0;
    boolean direccion_correcta = true;
    boolean borrarContenidoView = true;
    boolean entrenar = false;
    JSONObject json_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_entrenar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//mostrar flecha atras
        getSupportActionBar().setTitle("Entrenamiento facial");

        swtTransmision = findViewById(R.id.swtTransmisionE);
        webvideo = (WebView) findViewById(R.id.webvideoE);

        webvideo.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progDailog.dismiss();
                if (direccion_correcta) {
                    if (borrarContenidoView) {
                        borrarContenidoView = false;
                        Toast.makeText(ActEntrenar.this, "Dirección de transmisión no encontrada", Toast.LENGTH_LONG).show();
                        swtTransmision.setChecked(false);
                    } else {
                        Toast.makeText(ActEntrenar.this, "Transmisión cargada con exito", Toast.LENGTH_LONG).show();
                    }
                } else {
                    borrarContenidoView = true;
                    webvideo.loadUrl("about:blank");

                }
            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                direccion_correcta = false;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                direccion_correcta = true;
            }
        });
        swtTransmision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ActEntrenar.this.verTransmision();
                } else {
                    ActEntrenar.this.cerrarTransmision();
                }
            }
        });
        idSupervisado = getIntent().getExtras().getInt("idSupervisado");

        this.obtenerCamaras();
    }
    public void verTransmision() {
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webvideo.getSettings().setUserAgentString(newUA);
        webvideo.getSettings().setJavaScriptEnabled(true);
        webvideo.getSettings().setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            webvideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        }
        webvideo.loadUrl("http://" + direccion_ruta + "/stream");
        progDailog = new ProgressDialog(ActEntrenar.this);
        progDailog.setMessage("Cargando transmisión");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    public void cerrarTransmision() {
        webvideo.stopLoading();
    }

    private void obtenerCamaras() {
        entrenar=false;
        WebService ws = new WebService(ActEntrenar.this, "GET", APIBase.URLBASE + "monitoreo/entrenamiento-facial/?tutor_id=" + UsuarioLogeado.unTutor.getId(), this);
        ws.execute();
    }
    public void ocEntrenar(View view) throws JSONException {
        entrenar=true;
        json_data = new JSONObject();
        json_data.put("supervisado_id", idSupervisado);
        WebService ws = new WebService(ActEntrenar.this, "PUT", APIBase.URLBASE + "monitoreo/entrenamiento-facial/?tutor_id=" + UsuarioLogeado.unTutor.getId(),json_data.toString(), this);
        ws.execute();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void processFinish(String result) throws JSONException {
        if (!entrenar) {
            JSONArray JSONlista = new JSONArray(result);
            JSONObject jsonObjecto = JSONlista.getJSONObject(0);
            direccion_ruta = jsonObjecto.getString("direccion_ruta");
        }else{
            JSONObject jsonObjecto = new JSONObject(result);
            Toast.makeText(this,jsonObjecto.getString("entrenamiento_facial"),Toast.LENGTH_SHORT).show();

        }
    }
}