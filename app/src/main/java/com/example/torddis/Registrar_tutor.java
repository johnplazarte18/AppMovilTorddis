package com.example.torddis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.torddis.interfaces.APIBase;
import com.example.torddis.models.UsuarioLogeado;
import com.example.torddis.webService.Asynchtask;
import com.example.torddis.webService.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registrar_tutor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Asynchtask {
    TextInputEditText txtFechaNace;
    CircleImageView imgTutorReg;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    JSONObject json_data;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_tutor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//mostrar flecha atras

        imgTutorReg = (CircleImageView)findViewById(R.id.imgTutorReg);
        txtFechaNace=findViewById(R.id.txtFechaNace);
        findViewById(R.id.txtFechaNace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year+"-"+month+"-"+dayOfMonth;
        txtFechaNace.setText(date);
    }
    public void ocAbrirGaleria(View view){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imgTutorReg.setImageURI(imageUri);
        }
    }

    public void ocRegistrarTutor(View view){

        TextInputLayout tilNombre = findViewById(R.id.tilNombre);
        TextInputLayout tilApellidos = findViewById(R.id.tilApellidos);
        TextInputLayout tilCorreo = findViewById(R.id.tilCorreo);
        TextInputLayout tilFechaNace = findViewById(R.id.tilFechaNace);
        TextInputLayout tilUsuarioTutor = findViewById(R.id.tilUsuarioTutor);
        TextInputLayout tilContrasena = findViewById(R.id.tilContrasena);
        TextInputLayout tilContrasenaRep = findViewById(R.id.tilContrasenaRep);

        if(tilContrasena.getEditText().getText().toString().equals(tilContrasenaRep.getEditText().getText().toString())){
            json_data = new JSONObject();
            try {
                json_data.put("usuario", tilUsuarioTutor.getEditText().getText().toString());
                json_data.put("clave", tilContrasena.getEditText().getText().toString());
                json_data.put("correo", tilCorreo.getEditText().getText().toString());
                json_data.put("persona__nombres", tilNombre.getEditText().getText().toString());
                json_data.put("persona__apellidos", tilApellidos.getEditText().getText().toString());
                json_data.put("persona__fecha_nacimiento", tilFechaNace.getEditText().getText().toString());
                String fotoEnBase64=this.bitmapBase(((BitmapDrawable)imgTutorReg.getDrawable()).getBitmap());
                if (!fotoEnBase64.equals("")){
                    json_data.put("persona__foto_perfil", "data:image/PNG;base64,"+fotoEnBase64);
                }else{
                    json_data.put("persona__foto_perfil", "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            WebService ws= new WebService(Registrar_tutor.this,"POST", APIBase.URLBASE+"persona/tutor/",json_data.toString(),Registrar_tutor.this);
            ws.execute();
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

    }
    public String bitmapBase(Bitmap bitmap){
        String fotoEnBase64="";
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return fotoEnBase64;
    }

    @Override
    public void processFinish(String result) throws JSONException {
        json_data = new JSONObject(result);

        String mensaje=json_data.getString("tutores");

        if(mensaje.equals("usuario repetido")){
            Toast.makeText(this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
        }else if(mensaje.equals("error")){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }else if (mensaje.equals("guardado")){
            builder=new AlertDialog.Builder(this);
            builder.setTitle("Mensaje de confirmación")
                    .setMessage("Tutor registrado")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }
    }
}