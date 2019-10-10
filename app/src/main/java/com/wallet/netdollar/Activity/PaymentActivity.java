package com.wallet.netdollar.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wallet.netdollar.R;
import com.wallet.netdollar.Transactions.Record;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTitle,edtmontant,edtaccountId,edtnote;
    String accountId="";
    ImageButton imgscan;
    Button btnconfirm;
    MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTitle=findViewById(R.id.txttitle);
        txtTitle.setText(R.string.payments_method);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
       // toolbar.setSubtitle(R.string.payments_method);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgscan=findViewById(R.id.imgbutton);
        spinner = findViewById(R.id.spinner);
        spinner.setItems("Payment", "Transfer");
        edtmontant=findViewById(R.id.edtmontant);
        edtnote=findViewById(R.id.edtnote);
        edtaccountId=findViewById(R.id.edtAccountId);
        accountId=getIntent().getStringExtra("accountId");
        if (accountId=="")
        {
            Log.d("Info","No Account Found");
        }
        else {edtaccountId.setText(accountId);}
        imgscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ScannerActivity.class);
                startActivity(intent);
               finish();
            }
        });
        btnconfirm=findViewById(R.id.btn_confirm);
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });

    }


  public void  createpayemnt()
  {
      String id=edtaccountId.toString();
      //String type=spinner.get
      int montant=new Integer(edtmontant.toString());
      String note=edtnote.toString();
      Record record=new Record();
      record.setId(id);
      record.setCreatedAt("2019-09-24:15:56");
      record.setOperationCount(montant);
      record.setMemoType(note);
      SharedPrefs.saveObjectShared(this,"record",record);
      SharedPreferences sharedPref=getApplicationContext().getSharedPreferences("record", Context.MODE_PRIVATE);;
      Intent intent=new Intent(getApplicationContext(),MainActivity.class);
      //intent.putExtra("record",record);
      startActivity(intent);
      finish();

  }
    public void confirmDialog()
    {
// get prompts.xml view
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("");
        builder.setMessage("Vous n'avez assez de fonds pour effectuer ce payments");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // EnregistrerParcelle();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
