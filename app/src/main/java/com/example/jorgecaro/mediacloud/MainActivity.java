package com.example.jorgecaro.mediacloud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jorgecaro.mediacloud.Fragments.AnimationFragment;
import com.example.jorgecaro.mediacloud.Fragments.AudioFragment;
import com.example.jorgecaro.mediacloud.Fragments.GraphicFragment;
import com.example.jorgecaro.mediacloud.Fragments.ImageFragment;
import com.example.jorgecaro.mediacloud.Fragments.VideoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static int REQUEST_CODE = 1;
    private final static String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private int read, write, recorde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        read = ActivityCompat.checkSelfPermission(this, PERMISSIONS[0]);
        write = ActivityCompat.checkSelfPermission(this, PERMISSIONS[1]);
        recorde = ActivityCompat.checkSelfPermission(this, PERMISSIONS[2]);
        if(read == PackageManager.PERMISSION_DENIED || write == PackageManager.PERMISSION_DENIED || recorde == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new AnimationFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_animation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new AnimationFragment()).commit();
        } else if (id == R.id.nav_graphic) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new GraphicFragment()).commit();
        } else if (id == R.id.nav_image) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new ImageFragment()).commit();
        } else if (id == R.id.nav_video) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new VideoFragment()).commit();
        } else if (id == R.id.nav_audio) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new AudioFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
