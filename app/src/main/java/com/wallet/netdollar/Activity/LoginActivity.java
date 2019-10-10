package com.wallet.netdollar.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.netdollar.R;
import com.wallet.netdollar.models.AESHelper;

import org.spongycastle.util.encoders.Base64;


public class LoginActivity extends AppCompatActivity {
    Button btn_connect;
    TextView txtnewcompte;
    private EditText edtphone;
    private EditText edtpassword;
    ProgressDialog prgDialog;
    private  String phone="";
    private String walletid="";
    AESHelper aes=new AESHelper();
private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
         btn_connect=findViewById(R.id.btn_connecter);
         txtnewcompte=findViewById(R.id.txtnewcompte);
         edtphone=findViewById(R.id.edtphone);
        edtpassword=findViewById(R.id.edtpassword);
        connecter();
        newcompte();

    }
    public void connecter()
    {
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=edtphone.getText().toString();
                walletid=edtpassword.getText().toString();
               if(phone.isEmpty())
               {
                   edtphone.setError("Phone is required");
                   edtphone.requestFocus();
                   return;
               }
                if(phone.length()<10)
                {
                    edtphone.setError("Phone number is missing");
                    edtphone.requestFocus();
                    return;
                }
                if(walletid.isEmpty())
                {
                    edtpassword.setError("Password is required");
                    edtpassword.requestFocus();
                    return;
                }
               // prgDialog.show();
                verifyID();

//                Call<LoginResponse> call= RetrofitClient.getInstance().getApi().logger(phone,walletid);
//                call.enqueue(new Callback<LoginResponse>() {
//
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                        LoginResponse loginres=response.body();
//                        String respons=new String(loginres.getStatus());
//                        //prgDialog.hide();
//                        if(respons.equals("success"))
//                        {
//                            //if()
//                            SharedPrefs.saveShared(LoginActivity.this,"login","false");
//                            SharedPreferences sharedUser=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//                           // Toast.makeText(LoginActivity.this,respons,Toast.LENGTH_LONG).show();
//                            Intent intent=new Intent(LoginActivity.this,MainActivity.class) ;
//                            startActivity(intent);
//                            finish();
//
//                        }
//                        else
//                        {
//                            prgDialog.hide();
//                            Toast.makeText(LoginActivity.this,respons,Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//                    }
//                });

            }
        });
    }
    public   void verifyID() {
        byte[]salt=null;
        String passwgiven="";
        SharedPreferences sharePefs = getApplicationContext().getSharedPreferences("signup", Context.MODE_PRIVATE);
        String username = SharedPrefs.readShared(this,"username", "");
        String password = SharedPrefs.readShared(this,"password", "");
        String saltS =  SharedPrefs.readShared(this,"salt", "");
        salt= Base64.decode(saltS);
        passwgiven=aes.hashPassword(walletid,salt);
        prgDialog.show();
        if(password.equals(passwgiven))
        {
            Toast.makeText(getApplicationContext(), "Succes login", Toast.LENGTH_LONG).show();

            SharedPrefs.saveShared(LoginActivity.this,"login","false");
            SharedPreferences sharedUser=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            Intent intentlog=new Intent(LoginActivity.this,MainActivity.class) ;
            startActivity(intentlog);
            finish();
        }

        else {
            prgDialog.hide();
            Toast.makeText(getApplicationContext(), "Password Incorrect", Toast.LENGTH_LONG).show();

        }
      }
    public void newcompte()
    {
        txtnewcompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class) ;
                startActivity(intent);
                finish();
            }
        });
    }
}
