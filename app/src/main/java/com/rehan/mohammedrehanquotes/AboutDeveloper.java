package com.rehan.mohammedrehanquotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AboutDeveloper extends AppCompatActivity {
ActionBar actionBar;
ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);

        actionBar=getSupportActionBar();
        actionBar.setTitle("About Developer");

        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagram("https://www.instagram.com/mohammedrehan6532");
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook("https://www.facebook.com/mohammed.m.319");
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedin("https://www.linkedin.com/in/mohammedrehan37");
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmail();
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playstore("https://play.google.com/store/apps/dev?id=8077454561024306335");
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yourquote("https://www.yourquote.in/mohammed-rehan-voir/quotes");
            }
        });
    }

    private void instagram(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void facebook(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void linkedin(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void playstore(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void yourquote(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void gmail() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String[] email_id={"mohammedrehan6532@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, email_id);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Interview Crackathon App");
        intent.putExtra(Intent.EXTRA_TEXT, "Please write your feedback here...");
        startActivity(Intent.createChooser(intent, "Share App With :"));
    }

}
