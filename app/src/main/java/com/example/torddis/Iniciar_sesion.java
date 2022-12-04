package com.example.torddis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.torddis.interfaces.APIBase;
import com.example.torddis.models.Tutor;
import com.example.torddis.models.UsuarioLogeado;
import com.example.torddis.webService.Asynchtask;
import com.example.torddis.webService.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class Iniciar_sesion extends AppCompatActivity implements Asynchtask {

    JSONObject json_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//mostrar flecha atras
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void ocIniciarSesion(View view) throws JSONException {
        /*TextInputEditText txtUsuario=(findViewById(R.id.txtUsuario));
        TextInputEditText txtClave=(findViewById(R.id.txtClave));

        */
        json_data = new JSONObject();
        json_data.put("usuario", "calmeidad");
        json_data.put("clave", "123456");
        WebService ws= new WebService(Iniciar_sesion.this,"POST",APIBase.URLBASE+"persona/autenticacion/",json_data.toString(),Iniciar_sesion.this);
        ws.execute();
    }

    @Override
    public void processFinish(String result) throws JSONException {
        json_data = new JSONObject(result);
        Tutor unTutor=new Tutor();
        unTutor.setId(json_data.getInt("id"));
        unTutor.setUsuario(json_data.getString("usuario"));
        unTutor.setCorreo(json_data.getString("correo"));
        unTutor.setFoto_perfil(json_data.getString("foto_perfil"));
        unTutor.setPersona__nombres(json_data.getString("persona__nombres"));
        unTutor.setPersona__apellidos(json_data.getString("persona__apellidos"));
        unTutor.setPersona__fecha_nacimiento(json_data.getString("persona__fecha_nacimiento"));
        UsuarioLogeado.unTutor=unTutor;
        Intent intent = new Intent(getApplicationContext(), Menu_opciones.class);
        startActivity(intent);
    }
}