package com.example.torddis.adapterRcVw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.torddis.R;
import com.example.torddis.models.Objeto;
import com.example.torddis.models.Supervisado;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterSupervisado extends RecyclerView.Adapter<AdapterSupervisado.ViewHolder>  {

    private List<Supervisado> ltSupervisados;
    private Context Ctx;

    public AdapterSupervisado(Context mCtx, List<Supervisado> supervisados) {
        this.ltSupervisados = supervisados;
        Ctx=mCtx;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.card_supervisados_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Supervisado unSupervisado = ltSupervisados.get(position);
        holder.item_supervisado.setText("Nombres : "+unSupervisado.getPersona__nombres()+" "+unSupervisado.getPersona__apellidos());
        holder.item_edad_sup.setText("Edad : "+unSupervisado.getPersona__edad());
        Glide.with(Ctx).load(unSupervisado.getPersona__foto_perfil()).into(holder.item_image_Supervisado);
    }

    @Override
    public int getItemCount() {
        return ltSupervisados.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView item_image_Supervisado;
        TextView item_supervisado;
        TextView item_edad_sup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image_Supervisado=itemView.findViewById(R.id.item_image_Supervisado);
            item_supervisado=itemView.findViewById(R.id.item_supervisado);
            item_edad_sup=itemView.findViewById(R.id.item_edad_sup);
        }
    }
}
