package com.example.mbpadmin.sqllite.Item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mbpadmin.sqllite.R;

public class SetVieweHolder extends RecyclerView.ViewHolder {
    public TextView txjudul;
    public TextView txkecepatan;


    public SetVieweHolder(View itemView) {
        super(itemView);
        txjudul=(TextView)itemView.findViewById(R.id.text_judul);
        txkecepatan=(TextView)itemView.findViewById(R.id.text_kecepatan);
    }
}
