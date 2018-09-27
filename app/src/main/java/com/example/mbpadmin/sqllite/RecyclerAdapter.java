package com.example.mbpadmin.sqllite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbpadmin.sqllite.Item.Foto;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    static List<Foto> dbList;
    static Context context;
    RecyclerAdapter(Context context, List<Foto> dbList ){
        this.dbList = new ArrayList<Foto>();
        this.context = context;
        this.dbList = dbList;
       // Log.d("Adapter",dbList.toString());
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_list_item, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
//        Log.d("Foto",dbList.get(position).getFoto());
        if(dbList.get(position).getFoto()==null)
        {
            holder.judul.setText(dbList.get(position).getJudul()+" (Belum)");
        }
        else
        {
            holder.judul.setText(dbList.get(position).getJudul()+" (Sudah)");
        }
        holder.kecepatan.setText(dbList.get(position).getWaktu()+" s");

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView judul,kecepatan;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            judul=(TextView)itemLayoutView.findViewById(R.id.text_judul);
            kecepatan=(TextView)itemLayoutView.findViewById(R.id.text_kecepatan);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,CameraActivity.class);

            Bundle extras = new Bundle();
            extras.putInt("position",getAdapterPosition());
            intent.putExtras(extras);

            int i=getAdapterPosition();
            intent.putExtra("position", getAdapterPosition());
            //Log.d("Judul",dbList.get(getAdapterPosition()).getJudul());
            intent.putExtra("Judul",dbList.get(getAdapterPosition()).getJudul());
            context.startActivity(intent);
            Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }
    }
}
