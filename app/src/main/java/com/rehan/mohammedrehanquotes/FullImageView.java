package com.rehan.mohammedrehanquotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.rehan.mohammedrehanquotes.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FullImageView extends AppCompatActivity {
    ActionBar actionBar;
    ImageView imageView;

    //creating global variable to display data in this destination activity
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adding run time permission because in android API 23>, it requires permission programmatically rather than adding in manifest
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        setContentView(R.layout.activity_full_image_view);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Rehan Quotes");

        imageView=findViewById(R.id.imageView);

        Glide.with(this).load(getIntent().getStringExtra("image")).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.share_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.share:
                BitmapDrawable drawable= (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap=drawable.getBitmap();
                String bitmapPath=MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
                Uri uri=Uri.parse(bitmapPath);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Quote With :"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}