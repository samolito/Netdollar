package com.wallet.netdollar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.wallet.netdollar.R;

public class Splash_screenActivity extends AppCompatActivity {
    private static int splash_time=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(Splash_screenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splash_time);
    }
}
