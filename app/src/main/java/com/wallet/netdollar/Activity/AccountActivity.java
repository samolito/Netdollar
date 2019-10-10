package com.wallet.netdollar.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.wallet.netdollar.R;
import com.wallet.netdollar.models.Auth;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtphone,txtqrcode;
    ImageView imgaccount;
    Auth auth=new Auth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtphone=findViewById(R.id.txtphone);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        imgaccount=findViewById(R.id.imgAccountID);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences sharePefs = getApplicationContext().getSharedPreferences("keychain", Context.MODE_PRIVATE);
        String phone = SharedPrefs.readShared(this,"username", "");
        String accountId=SharedPrefs.readShared(this,"accountId", "");
        imgaccount.setImageBitmap(getQrCode(accountId));
        txtphone.setText("Phone number :"+phone);
    }


    public Bitmap getQrCode(String Inputvalue) {
        //bitmapimage;
        Bitmap bitmapimg=null;
        if (Inputvalue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point=new Point();
            display.getSize(point);
            int width=point.x;
            int height=point.y;
            int smallDim=width<height?width:height;
            smallDim=smallDim *4/4;
            QRGEncoder qrgEncoder=new QRGEncoder(Inputvalue,null, QRGContents.Type.TEXT,smallDim);
            try {
                bitmapimg = qrgEncoder.encodeAsBitmap();
                // QRGSaver.save(savePath, Inputvalue.trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                // qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v("Error :", e.toString());
            }

        }
        return bitmapimg;
    }
}
