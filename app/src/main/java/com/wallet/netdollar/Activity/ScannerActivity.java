package com.wallet.netdollar.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.wallet.netdollar.R;

import java.io.IOException;

public class ScannerActivity extends AppCompatActivity {
Toolbar toolbar;
    TextView txtTitle,txtqrcode;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION=201;
    SurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle=findViewById(R.id.txttitle);
        txtTitle.setText(R.string.titre_scan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        surfaceView=findViewById(R.id.surfaceView);
        txtqrcode=findViewById(R.id.txtqrcode);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void Detector_code()
    {
        barcodeDetector=new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(1500, 900)
                .setAutoFocusEnabled(true)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
                    {
                        cameraSource.start(surfaceView.getHolder());
                    }
                    else {
                        ActivityCompat.requestPermissions(ScannerActivity.this,
                                new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);

                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                   if(barcodes.size()!=0)
                   {
                       txtqrcode.post(new Runnable() {
                           @Override
                           public void run() {
                               if(barcodes.valueAt(0).email==null) {
                                   String value = barcodes.valueAt(0).displayValue;
                                   txtqrcode.setText(value);
                                   if(value.equals(""))
                                   {
                                       Toast.makeText(getApplicationContext(),"Account ID not found",Toast.LENGTH_LONG).show();;
                                   }
                                   else {
                                   Intent intent=new Intent(getApplicationContext(),PaymentActivity.class);
                                   intent.putExtra("accountId",value);
                                   startActivity(intent);
                                   finish();
                                   }
                               }
                           }
                       });

                           }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Detector_code();
    }
}
