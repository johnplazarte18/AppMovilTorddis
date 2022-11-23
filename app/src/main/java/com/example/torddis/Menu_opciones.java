package com.example.torddis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.torddis.models.Tutor;

public class Menu_opciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        getSupportActionBar().hide();
    }
    public void ocSupervisados(View view){
        Intent intent = new Intent(getApplicationContext(), ActSupervisados.class);
        startActivity(intent);
    }
    public void ocObjeto(View view){
        Intent intent = new Intent(getApplicationContext(), ActObjetos.class);
        startActivity(intent);
    }
    public void ocMonitoreo(View view){
        Intent intent = new Intent(getApplicationContext(), ActMonitoreo.class);
        startActivity(intent);
    }
}