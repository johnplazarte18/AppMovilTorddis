package com.example.torddis;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.torddis.models.Tutor;
import com.google.android.material.navigation.NavigationView;

public class Menu_opciones extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        getSupportActionBar().hide();
        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
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
    public void ocCamaras(View view){
        Intent intent = new Intent(getApplicationContext(), ActCamaras.class);
        startActivity(intent);
    }
    public void ocHistorial(View view){
        Intent intent = new Intent(getApplicationContext(), ActHistorial.class);
        startActivity(intent);
    }
    public void ocReportes(View view){
        Intent intent = new Intent(getApplicationContext(), ActReportes.class);
        startActivity(intent);
    }
    public void onOptionsItemSelected(View view) {
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            if(drawerLayout.isOpen()){
                drawerLayout.close();
                return true;
            }else{
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}