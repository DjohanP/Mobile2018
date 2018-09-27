package com.example.mbpadmin.sqllite;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
//import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mbpadmin.sqllite.Item.Foto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper helper;
    List<Foto> dbList;
    private DrawerLayout mDrawerLayout;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        helper = new DatabaseHelper(this);
        requestWriteStoragePermission();

        if (helper.getRow()!="30")
        {
            helper.deleteAllRow();
            helper.insertIntoDB("1. Normal Tegak Lurus Kamera");
            helper.insertIntoDB("2. Normal tegak tegak lurus kamera tidak berkacamata");
            helper.insertIntoDB("3. Tersenyum tegak lurus kamera");
            helper.insertIntoDB("4. Sedih tegak lurus kamera");
            helper.insertIntoDB("5. Mengantuk tegak lurus kamera");
            helper.insertIntoDB("6. Normal menoleh ke kanan 30 derajat");
            helper.insertIntoDB("7. Normal menoleh ke kanan 30 derajat tidak berkacamata");
            helper.insertIntoDB("8. Tersenyum menoleh ke kanan 30 derajat");
            helper.insertIntoDB("9. Sedih menoleh ke kanan 30 derajat");
            helper.insertIntoDB("10. Mengantuk menoleh ke kanan 30 derajat");
            helper.insertIntoDB("11. Normal menoleh ke kiri 30 derajat");
            helper.insertIntoDB("12. Normal menoleh ke kiri 30 derajat tidak berkacamata");
            helper.insertIntoDB("13. Tersenyum menoleh ke kiri 30 derajat");
            helper.insertIntoDB("14. Sedih menoleh ke kiri 30 derajat");
            helper.insertIntoDB("15. Mengantuk menoleh ke kiri 30 derajat");
            helper.insertIntoDB("16. Normal tegak lurus kamera muka basah");
            helper.insertIntoDB("17. Normal tegak lurus kamera tidak berkacamata muka basah");
            helper.insertIntoDB("18. Tersenyum tegak lurus kamera muka basah");
            helper.insertIntoDB("19. Sedih tegak lurus kamera muka basah.");
            helper.insertIntoDB("20. Mengantuk tegak lurus kamera muka basah");
            helper.insertIntoDB("21. Normal menoleh ke kanan 30 derajat muka basah");
            helper.insertIntoDB("22. Normal menoleh ke kanan 30 derajat tidak berkacamata muka basah");
            helper.insertIntoDB("23. Tersenyum menoleh ke kanan 30 derajat muka basah");
            helper.insertIntoDB("24. Sedih menoleh ke kanan 30 derajat muka basah");
            helper.insertIntoDB("25. Mengantuk menoleh ke kanan 30 derajat muka basah");
            helper.insertIntoDB("26. Normal menoleh ke kiri 30 derajat muka basah");
            helper.insertIntoDB("27. Normal menoleh ke kiri 30 derajat tidak berkacamata muka basah");
            helper.insertIntoDB("28. Tersenyum menoleh ke kiri 30 derajat muka basah");
            helper.insertIntoDB("29. Sedih menoleh ke kiri 30 derajat muka basah");
            helper.insertIntoDB("30. Mengantuk menoleh ke kiri 30 derajat muka basah");
        }

        dbList= new ArrayList<>();
        dbList = helper.getDataFromDB();

//        recyclerView=findViewById(R.id.recycleview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerViewAdapter=new RecyclerAdapter(this,dbList);
//        recyclerView.setAdapter(mRecyclerViewAdapter);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.djohan:
                        Toast.makeText(getBaseContext(), "k", Toast.LENGTH_LONG).show ();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestWriteStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
            };
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}