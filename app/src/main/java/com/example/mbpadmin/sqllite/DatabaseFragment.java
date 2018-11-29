package com.example.mbpadmin.sqllite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mbpadmin.sqllite.Item.Foto;
import com.example.mbpadmin.sqllite.db.FotooEntity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFragment extends Fragment {
    //ThingsAda pter
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Foto> dbList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle("Database");
        NavigationView nvDrawer=(NavigationView) getActivity().findViewById(R.id.nvView);
        nvDrawer.setCheckedItem(R.id.nav_database);
        View view=inflater.inflate(R.layout.fragment_database,container,false );
        List<FotooEntity> fotooEntityList=MainActivity2.appDatabase.fotooDao().getAll();
        dbList= new ArrayList<>();
        for(FotooEntity fotoo:fotooEntityList)
        {
            Foto foto=new Foto();
            foto.setId(fotoo.getId());
            foto.setJudul(fotoo.getJudul());
            foto.setWaktu(fotoo.getWaktu());
            foto.setFoto("0");
            dbList.add(foto);
        }

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerviewdb);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new DatabaseAdapter(getContext(), dbList));


        return view;
    }
}
