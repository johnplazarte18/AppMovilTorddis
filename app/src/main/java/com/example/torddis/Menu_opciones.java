package com.example.torddis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.torddis.models.Tutor;

public class Menu_opciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        getSupportActionBar().hide();
    }
}