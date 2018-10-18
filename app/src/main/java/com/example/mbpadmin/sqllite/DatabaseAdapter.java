package com.example.mbpadmin.sqllite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbpadmin.sqllite.Item.Foto;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.MyViewHolder> {

    static List<Foto> dbList;
    static Context context;

    DatabaseAdapter(Context context,List<Foto> dbList2)
    {
        this.dbList = new ArrayList<Foto>();
        this.context = context;
        this.dbList = dbList2;

    }

    @NonNull
    @Override
    public DatabaseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_list_item_db, parent, false);

        // create ViewHolder

        MyViewHolder myViewHolder=new MyViewHolder(itemLayoutView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseAdapter.MyViewHolder holder, int position) {
        holder.judul.setText(dbList.get(position).getJudul());
        holder.kecepatan.setText(dbList.get(position).getWaktu()+" s");
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul,kecepatan;
        public MyViewHolder(View itemView) {
            super(itemView);
            judul=(TextView)itemView.findViewById(R.id.text_judul_db);
            kecepatan=(TextView)itemView.findViewById(R.id.text_kecepatan_db);
        }
    }
}
