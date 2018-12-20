package com.example.mbpadmin.sqllite;

import android.Manifest;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mbpadmin.sqllite.db.AppDatabase;
import com.example.mbpadmin.sqllite.db.FotooDao;


public class MainActivity2 extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    public static AppDatabase appDatabase;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        appDatabase= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"mobile2018").allowMainThreadQueries().build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar=(Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle=setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer=(NavigationView)findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);
        requestWriteStoragePermission();
        Class fragmentClass;
        Fragment fragment = null;
        fragmentClass = CameraFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        setTitle("Mobile 2018");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);

    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    selectDrawerItem(menuItem);
                    return true;
                }
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        //Toast.makeText(this,"Camera",Toast.LENGTH_LONG).show();
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_cameraa:
                fragmentClass = CameraFragment.class;
                //Toast.makeText(this,"Camera",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_database:
                fragmentClass = DatabaseFragment.class;
                break;
            case R.id.nav_predict:
                fragmentClass = PrediksiFragment2.class;
                break;
            case R.id.nav_train:
                fragmentClass=TrainFragment.class;
                break;
            case R.id.nav_scan:
                fragmentClass=ScanFragment.class;
                break;
            case R.id.nav_send_ttd:
                fragmentClass=SignatureFragment.class;
                break;
            case R.id.nav_train_ttd:
                fragmentClass=SignatureTrainFragment.class;
                break;
            case R.id.nav_predict_ttd:
                fragmentClass=SignaturePredictFragment.class;
                break;

            default:
                fragmentClass = CameraFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        // Highlight the selected item has been done by NavigationView
        //menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public void requestWriteStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
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
