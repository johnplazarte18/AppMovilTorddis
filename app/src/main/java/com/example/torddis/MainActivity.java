package com.example.torddis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
     public void ocIniciarSesion(View view){
         Intent intent = new Intent(this, Iniciar_sesion.class);
         startActivity(intent);
     }
     public void ocRegistrarTutor(View view){
        Intent intent = new Intent(this, Registrar_tutor.class);
        startActivity(intent);
    }
}