package com.example.torddis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.torddis.models.UsuarioLogeado;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Menu_opciones extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navView;
    TextView txtUsuarioLog;
    CircleImageView imgUsuarioLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        getSupportActionBar().hide();
        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        navView.getHeaderView(0).findViewById(R.id.txtUsuarioLog);
        txtUsuarioLog = navView.getHeaderView(0).findViewById(R.id.txtUsuarioLog);
        imgUsuarioLog = navView.getHeaderView(0).findViewById(R.id.imgUsuarioLog);
        txtUsuarioLog.setText(UsuarioLogeado.unTutor.getPersona__nombres() + " " + UsuarioLogeado.unTutor.getPersona__apellidos());
        Glide.with(this.getApplicationContext()).load(UsuarioLogeado.unTutor.getFoto_perfil()).into(imgUsuarioLog);
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}