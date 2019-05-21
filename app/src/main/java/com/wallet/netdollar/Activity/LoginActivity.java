package com.wallet.netdollar.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.netdollar.Api.RetrofitClient;
import com.wallet.netdollar.R;
import com.wallet.netdollar.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    Button btn_connect;
    TextView txtnewcompte;
    private EditText edtphone;
    private EditText edtpassword;
    ProgressDialog prgDialog;
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
                String phone=edtphone.getText().toString();
                String walletid=edtpassword.getText().toString();
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
                prgDialog.show();
                Call<LoginResponse> call= RetrofitClient.getInstance().getApi().logger(phone,walletid);
                call.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginres=response.body();
                        String respons=new String(loginres.getStatus());
                        //prgDialog.hide();
                        if(respons.equals("success"))
                        {
                            SharedPrefs.saveShared(LoginActivity.this,"login","false");
                           // SharedPreferences sharedUser=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                           // Toast.makeText(LoginActivity.this,respons,Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class) ;
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            prgDialog.hide();
                            Toast.makeText(LoginActivity.this,respons,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

            }
        });
    }

    public void newcompte()
    {
        txtnewcompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class) ;
                startActivity(intent);
            }
        });
    }
}
