package com.example.mbpadmin.sqllite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.mbpadmin.sqllite.Item.Foto;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Foto> dbList;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWriteStoragePermission();
        setContentView (R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer);

        DatabaseHelper helper = new DatabaseHelper(this);

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

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(this, dbList));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
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
        if (requestCode == 2) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED)
                    finish();
            }
        }
    }
}
