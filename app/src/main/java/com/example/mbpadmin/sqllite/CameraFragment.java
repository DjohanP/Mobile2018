package com.example.mbpadmin.sqllite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbpadmin.sqllite.Item.Foto;
import com.example.mbpadmin.sqllite.db.FotooEntity;

import java.util.ArrayList;
import java.util.List;


public class CameraFragment extends Fragment {
    //ThingsAda pter

    //private CameraItemBinding mBinding;
    //private TextView textView;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.camera_itemku,container,false );
        List<Foto> dbList= new ArrayList<>();

        recyclerView=view.findViewById(R.id.recyclerviewku);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] CAPTION = new String[]{"1. Normal Tegak Lurus Kamera", "2. Normal tegak tegak lurus kamera tidak berkacamata", "3. Tersenyum tegak lurus kamera", "4. Sedih tegak lurus kamera", "5. Mengantuk tegak lurus kamera", "6. Normal menoleh ke kanan 30 derajat", "7. Normal menoleh ke kanan 30 derajat tidak berkacamata", "8. Tersenyum menoleh ke kanan 30 derajat", "9. Sedih menoleh ke kanan 30 derajat", "10. Mengantuk menoleh ke kanan 30 derajat", "11. Normal menoleh ke kiri 30 derajat", "12. Normal menoleh ke kiri 30 derajat tidak berkacamata", "13. Tersenyum menoleh ke kiri 30 derajat", "14. Sedih menoleh ke kiri 30 derajat", "15. Mengantuk menoleh ke kiri 30 derajat", "16. Normal tegak lurus kamera muka basah", "17. Normal tegak lurus kamera tidak berkacamata muka basah", "18. Tersenyum tegak lurus kamera muka basah", "19. Sedih tegak lurus kamera muka basah.", "20. Mengantuk tegak lurus kamera muka basah", "21. Normal menoleh ke kanan 30 derajat muka basah", "22. Normal menoleh ke kanan 30 derajat tidak berkacamata muka basah", "23. Tersenyum menoleh ke kanan 30 derajat muka basah", "24. Sedih menoleh ke kanan 30 derajat muka basah", "25. Mengantuk menoleh ke kanan 30 derajat muka basah", "26. Normal menoleh ke kiri 30 derajat muka basah", "27. Normal menoleh ke kiri 30 derajat tidak berkacamata muka basah", "28. Tersenyum menoleh ke kiri 30 derajat muka basah", "29. Sedih menoleh ke kiri 30 derajat muka basah", "30. Mengantuk menoleh ke kiri 30 derajat muka basah"};
        for (int i=0;i<CAPTION.length;i++)
        {
            Foto foto=new Foto();
            foto.setId(i);
            foto.setJudul(CAPTION[i]);
            foto.setWaktu("0");
            foto.setFoto("0");
            dbList.add(foto);
        }
        //Log.d("Foto Baru",dbList.toString());
        recyclerView.setAdapter(new RecyclerAdapter(getContext(), dbList));
        MainActivity2.appDatabase.fotooDao().deleteAll();
        FotooEntity fotooEntity=new FotooEntity();
        fotooEntity.setJudul(CAPTION[0]);
        fotooEntity.setFoto("wkwkwk");
        fotooEntity.setWaktu("500");
        MainActivity2.appDatabase.fotooDao().addFotoo(fotooEntity);


        //mBinding=DataBindingUtil.inflate(inflater,R.layout.camera_item2,container,false);
        /*String []menuItems={"1. Camera","2. Camera2","3. Camera"};
        ListView listView=(ListView ) view.findViewById(R.id.list_camera);

        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        ) ;

        listView.setAdapter(listViewAdapter);*/
        //textView=view.findViewById(R.id.displaynya);
        /*List<FotooEntity> fotooEntityList=MainActivity2.appDatabase.fotooDao().getAll();
        if(fotooEntityList.isEmpty())
        {
            textView.setText("Halo");
        }
        else
        {
            String info="";
            //textView.setText("Halo2");
            MainActivity2.appDatabase.fotooDao().deleteAll();
            String[] CAPTION = new String[]{"1. Normal Tegak Lurus Kamera", "2. Normal tegak tegak lurus kamera tidak berkacamata", "3. Tersenyum tegak lurus kamera", "4. Sedih tegak lurus kamera", "5. Mengantuk tegak lurus kamera", "6. Normal menoleh ke kanan 30 derajat", "7. Normal menoleh ke kanan 30 derajat tidak berkacamata", "8. Tersenyum menoleh ke kanan 30 derajat", "9. Sedih menoleh ke kanan 30 derajat", "10. Mengantuk menoleh ke kanan 30 derajat", "11. Normal menoleh ke kiri 30 derajat", "12. Normal menoleh ke kiri 30 derajat tidak berkacamata", "13. Tersenyum menoleh ke kiri 30 derajat", "14. Sedih menoleh ke kiri 30 derajat", "15. Mengantuk menoleh ke kiri 30 derajat", "16. Normal tegak lurus kamera muka basah", "17. Normal tegak lurus kamera tidak berkacamata muka basah", "18. Tersenyum tegak lurus kamera muka basah", "19. Sedih tegak lurus kamera muka basah.", "20. Mengantuk tegak lurus kamera muka basah", "21. Normal menoleh ke kanan 30 derajat muka basah", "22. Normal menoleh ke kanan 30 derajat tidak berkacamata muka basah", "23. Tersenyum menoleh ke kanan 30 derajat muka basah", "24. Sedih menoleh ke kanan 30 derajat muka basah", "25. Mengantuk menoleh ke kanan 30 derajat muka basah", "26. Normal menoleh ke kiri 30 derajat muka basah", "27. Normal menoleh ke kiri 30 derajat tidak berkacamata muka basah", "28. Tersenyum menoleh ke kiri 30 derajat muka basah", "29. Sedih menoleh ke kiri 30 derajat muka basah", "30. Mengantuk menoleh ke kiri 30 derajat muka basah"};
            for(int i=0;i<CAPTION.length;i++)
            {
                Log.d("caption",CAPTION[i]);
                FotooEntity fotooEntity=new FotooEntity();
                fotooEntity.setJudul(CAPTION[i]);
                fotooEntity.setFoto("0");
                fotooEntity.setWaktu("0");
                MainActivity2.appDatabase.fotooDao().addFotoo(fotooEntity);
            }
            fotooEntityList=MainActivity2.appDatabase.fotooDao().getAll();
            for(FotooEntity fotoo:fotooEntityList)
            {
                String judul=fotoo.getJudul();
                info=info+"\n"+judul;
            }
            textView.setText(info);
        }*/
        return view;
    }
}
