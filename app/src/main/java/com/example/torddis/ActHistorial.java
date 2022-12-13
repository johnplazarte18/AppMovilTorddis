package com.example.torddis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.torddis.adapterRcVw.AdapterHistorial;
import com.example.torddis.adapterRcVw.AdapterObjeto;
import com.example.torddis.interfaces.APIBase;
import com.example.torddis.models.Historial;
import com.example.torddis.models.Objeto;
import com.example.torddis.models.Supervisado;
import com.example.torddis.models.UsuarioLogeado;
import com.example.torddis.webService.Asynchtask;
import com.example.torddis.webService.WebService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActHistorial extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Asynchtask {
    TextInputEditText txtFechaHistorial;
    List<Supervisado> ltSupervisados;
    int supervisadoId=0;
    AdapterHistorial adapterHistorial;
    RecyclerView rcvHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_historial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//mostrar flecha atras
        getSupportActionBar().setTitle("Historial");
        txtFechaHistorial=findViewById(R.id.txtFechaHistorial);
        findViewById(R.id.txtFechaHistorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        rcvHistorial = (RecyclerView) findViewById(R.id.rcvHistorial);
        rcvHistorial.setHasFixedSize(true);
        rcvHistorial.setLayoutManager(new LinearLayoutManager(this));
        rcvHistorial.setItemAnimator(new DefaultItemAnimator());
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.datePickerDialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
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
    public void ocListarSupervisados(View view){
        WebService ws= new WebService(ActHistorial.this,"GET", APIBase.URLBASE+"persona/supervisado/?tutor_id="+UsuarioLogeado.unTutor.getId(),this);
        ws.execute();
    }
    public void ocConsultarHistorial(View view){
        if(txtFechaHistorial.getText().toString().equals("") || supervisadoId==0){
            Toast.makeText(this,"Faltan datos por seleccionar",Toast.LENGTH_SHORT).show();
        }else {
            JSONObject json_data = new JSONObject();
            WebService ws = new WebService(ActHistorial.this, "GET", APIBase.URLBASE + "monitoreo/historial/?supervisado_id="+supervisadoId+"&fecha="+txtFechaHistorial.getText().toString(), this);
            ws.execute();
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year+"-"+month+"-"+dayOfMonth;
        txtFechaHistorial.setText(date);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        if(supervisadoId==0){
            listarSupervisados(result);
        }else{
            ArrayList<Historial> lsHistorial= new ArrayList<Historial>();
            JSONArray JSONlista =  new JSONArray(result);
            for(int i=0; i< JSONlista.length();i++){
                JSONObject jsonObjecto=  JSONlista.getJSONObject(i);
                Historial unaHistoria=new Historial();
                unaHistoria.setObservacion(jsonObjecto.getString("observacion"));
                unaHistoria.setImagen_evidencia(jsonObjecto.getString("imagen_evidencia"));
                unaHistoria.setFecha_hora(jsonObjecto.getString("fecha_hora"));
                unaHistoria.setTipo_distraccion__nombre(jsonObjecto.getString("tipo_distraccion__nombre"));
                lsHistorial.add(unaHistoria);
            }
            adapterHistorial=new AdapterHistorial(this,lsHistorial);
            rcvHistorial.setAdapter(adapterHistorial);
        }
    }
    public void listarSupervisados(String result) throws JSONException {
        ArrayList<Objeto> lsSuper= new ArrayList<Objeto>();
        JSONArray JSONlista =  new JSONArray(result);
        ltSupervisados=new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActHistorial.this, android.R.layout.simple_selectable_list_item);
        for(int i=0; i< JSONlista.length();i++){
            JSONObject jsonObjecto=  JSONlista.getJSONObject(i);
            Supervisado supervisado=new Supervisado();
            supervisado.setId(jsonObjecto.getInt("id"));
            supervisado.setPersona__nombres(jsonObjecto.getString("persona__nombres")+" "+jsonObjecto.getString("persona__apellidos"));
            ltSupervisados.add(supervisado);
            arrayAdapter.add(jsonObjecto.getString("persona__nombres")+" "+jsonObjecto.getString("persona__apellidos"));
        }

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActHistorial.this);
        builderSingle.setIcon(R.drawable.ic_person);
        builderSingle.setTitle("Selecciona un supervisado");

        builderSingle.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ActHistorial.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Ha seleccionado a:");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        for (Supervisado s:ltSupervisados){
                            if (s.getPersona__nombres().equals(strName)){
                                supervisadoId=s.getId();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }
}