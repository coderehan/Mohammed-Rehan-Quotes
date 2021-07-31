package com.rehan.mohammedrehanquotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    RecyclerView recyclerView;
    List<String> images;
    MyAdapter myAdapter;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //internet connection
        if(!isConnected(MainActivity.this))
        {
            showAlertDialog();
        }

        progressDialog=new ProgressDialog(this);

        databaseReference= FirebaseDatabase.getInstance().getReference("add_quote");

        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                images=new ArrayList<>();

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String data= (String) snapshot.getValue();
                    images.add(0, data);
                }

                recyclerView=findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                myAdapter=new MyAdapter(MainActivity.this, images);
                recyclerView.setAdapter(myAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        drawerLayout=findViewById(R.id.drawerLayout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mohammed Rehan Quotes");
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView=findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "This is the Home Page of this app.", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_download_app:
                        String download_app="https://play.google.com/store/apps/dev?id=8077454561024306335";
                        Uri uri_download= Uri.parse(download_app);
                        Intent rate=new Intent(Intent.ACTION_VIEW, uri_download);
                        try {
                            startActivity(rate);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Unable to open", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case R.id.nav_about_developer:
                        Intent intent1=new Intent(MainActivity.this, AboutDeveloper.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_rate_app:
                        String app_link="https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                        Uri uri= Uri.parse(app_link);
                        Intent intent2=new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(intent2);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Unable to open", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case R.id.nav_share_app:
                        try {
                            Intent share=new Intent(Intent.ACTION_SEND);
                            share.setType("text/plain");
                            share.putExtra(Intent.EXTRA_SUBJECT, "Mohammed Rehan Quotes" + "\n");
                            share.putExtra(Intent.EXTRA_TEXT, "Download this app from Google PlayStore by clicking this below link. Please do share this app info with your friends and tell them to give feedback about this app. Your feedback is mostly appreciated and delighted to hear." + "\n\n" + "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                            startActivity(Intent.createChooser(share, "Share App Info With :"));
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Unable to share", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return true;
            }
        });
    }

    //checking internet connection
    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager= (ConnectivityManager) mainActivity.getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi!=null && wifi.isConnectedOrConnecting()) || (mobile!=null && mobile.isConnectedOrConnecting()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Dear user! Please connect app to internet to proceed further.");
        builder.setCancelable(false);

        builder.setPositiveButton("CONNECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder obj=new AlertDialog.Builder(MainActivity.this);

        obj.setTitle("This Application Says:");
        obj.setMessage("Do you really want to close this app?");
        obj.setCancelable(true);

        obj.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        obj.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog=obj.create();
        alertDialog.show();
    }
}

