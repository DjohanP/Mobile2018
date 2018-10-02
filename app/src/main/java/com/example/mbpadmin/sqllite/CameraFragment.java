package com.example.mbpadmin.sqllite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CameraFragment extends Fragment {
    //ThingsAda pter

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_camera,container,false );
        String []menuItems={"1. Camera","2. Camera2","3. Camera"};
        ListView listView=(ListView ) view.findViewById(R.id.list_camera);

        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        ) ;

        listView.setAdapter(listViewAdapter);
        return view;
    }
}
