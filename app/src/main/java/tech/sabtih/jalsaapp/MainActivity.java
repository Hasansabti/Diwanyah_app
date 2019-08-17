package tech.sabtih.jalsaapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.dummy.itemnop;
import tech.sabtih.jalsaapp.dummy.userdata;
import tech.sabtih.jalsaapp.net.Api;
import tech.sabtih.jalsaapp.net.ApiInterface;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, logindialog.dialoglistener {

    ImageView img;
    ImageView pimage;
    TextView username;
    TextView userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        pimage = hView.findViewById(R.id.pimage);
        username = hView.findViewById(R.id.username);
        userdata = hView.findViewById(R.id.userdata);
        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logindialog login = new logindialog();
                login.show(getSupportFragmentManager(),"login");
            }
        });

        img = findViewById(R.id.imagetest);
        File images = new File(Environment.getExternalStorageDirectory().toString()+"/myimages/001.jpg");
        Bitmap bm = BitmapFactory.decodeFile(images.getPath());
        img.setImageBitmap(bm);
        System.out.println(Environment.getExternalStorageDirectory());

  //      ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPref = getSharedPreferences("jalsa",Context.MODE_PRIVATE);
        int userid = sharedPref.getInt("userid",-1);

        if(userid != -1){
            onLogin(userid);
        }






    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar itemnop clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view itemnop clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(this,videos.class);
            intent.putExtra("parent","0");
            startActivity(intent);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLogin(int userid) {
        System.out.println("==============="+userid);
        ApiInterface service = Api.getRetrofitInstance().create(ApiInterface.class);

        final Call<userdata> userdet = service.getUsersata(userid);
        userdet.enqueue(new Callback<userdata>() {
            @Override
            public void onResponse(Call<userdata> call, Response<userdata> response) {

                //System.out.println(new Gson().toJson(response));
                username.setText(response.body().getDetails().getName());
                userdata.setText(""+response.body().getMyamnt() +" / " + response.body().getUnpaid());
            }

            @Override
            public void onFailure(Call<userdata> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());

            }
        });


    }


}
