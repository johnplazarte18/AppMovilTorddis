package com.example.torddis.adapterRcVw;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.torddis.ActObjetos;
import com.example.torddis.R;
import com.example.torddis.models.Objeto;

import java.util.List;

public class AdapterObjeto extends RecyclerView.Adapter<AdapterObjeto.ViewHolder>  {

    private List<Objeto> ltObjetos;
    private Context Ctx;

    public AdapterObjeto(Context mCtx,List<Objeto> objetos) {
        this.ltObjetos = objetos;
        Ctx=mCtx;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.card_objetos_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Objeto unObjeto = ltObjetos.get(position);
        holder.txtObjeto.setText(unObjeto.getNombre());
        Glide.with(Ctx).load(unObjeto.getFoto_objeto()).into(holder.imvObjeto);
    }

    @Override
    public int getItemCount() {
        return ltObjetos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtObjeto;
        ImageView imvObjeto;
        Switch swtObjeto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtObjeto=itemView.findViewById(R.id.item_objeto);
            imvObjeto=itemView.findViewById(R.id.item_image_objeto);
            swtObjeto=itemView.findViewById(R.id.item_objeto_habilitado);
        }
    }
}
