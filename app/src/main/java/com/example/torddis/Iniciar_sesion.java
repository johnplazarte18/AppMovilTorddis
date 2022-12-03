package com.example.torddis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Iniciar_sesion extends AppCompatActivity {

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
    public void ocIniciarSesion(View view){
        /*TextInputEditText txtUsuario=(findViewById(R.id.txtUsuario));
        TextInputEditText txtClave=(findViewById(R.id.txtClave));

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("usuario", txtUsuario.getText().toString());
            paramObject.put("clave", txtClave.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TutorAPI tutorAPI = retrofit.create(TutorAPI.class);
        Call<Tutor> call = tutorAPI.iniciarSesion(paramObject.toString());
        call.enqueue(new Callback<Tutor>() {
            Tutor tutor=new Tutor();
            @Override
            public void onResponse(Call<Tutor> call, Response<Tutor> response) {
                if(response.isSuccessful()){
                    if (response.body().getId() != null) {
                        tutor=response.body();
                        Intent intent = new Intent(getApplicationContext(), Menu_opciones.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Credenciales incorrectas",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Tutor> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Credenciales incorrectas",Toast.LENGTH_LONG).show();
            }
        });*/

        Intent intent = new Intent(getApplicationContext(), Menu_opciones.class);
        startActivity(intent);
    }
}