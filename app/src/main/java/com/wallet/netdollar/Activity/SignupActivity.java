package com.wallet.netdollar.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.netdollar.Api.RetrofitClient;
import com.wallet.netdollar.R;
import com.wallet.netdollar.models.SignupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTitle;
    Button btn_creercompte;
    private EditText edtphone;
    private EditText edtpassword;
    private EditText edtconfirmpass;
    ProgressDialog prgDialog;
    String privetkey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle=findViewById(R.id.txttitle);
        txtTitle.setText(R.string.titre_newcompte);
        btn_creercompte=findViewById(R.id.btn_creer);
        edtphone=findViewById(R.id.edtphonenumber);
        edtpassword=findViewById(R.id.edtmotdepasse);
        edtconfirmpass=findViewById(R.id.edtconfirmep);
            creerCompte();

    }
    public void creerCompte()
    {

        btn_creercompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone=edtphone.getText().toString();
                final String walletid=edtpassword.getText().toString();
                String confirmid=edtconfirmpass.getText().toString();
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
                if(!walletid.equals(confirmid))
                {
                    edtconfirmpass.setError("Password must be same");
                    edtconfirmpass.requestFocus();
                    Toast.makeText(SignupActivity.this,walletid+" : "+confirmid,Toast.LENGTH_LONG).show();
                    return;
                }

                prgDialog.show();
                //Toast.makeText(SignupActivity.this,"Verification code was sent",Toast.LENGTH_LONG).show();
                Intent in=new Intent(SignupActivity.this,ConfirmcodeActivity.class);
                in.putExtra("phone",phone);
                in.putExtra("walletid",walletid);
                startActivity(in);
                finish();
               Call<SignupResponse> call= RetrofitClient.getInstance().getApi().signup(phone);
                call.enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        SignupResponse signupres=response.body();
                        String respons=signupres.getStatus();
                        String msg="";
                        String err="";
                        if (respons.equals("success")){
                             msg=new String(signupres.getData().getMessage());
                             err=new String(signupres.getData().getError());
                            Toast.makeText(SignupActivity.this,msg,Toast.LENGTH_LONG).show();
                            if(!msg.equals(""))
                            {
                                Toast.makeText(SignupActivity.this,msg,Toast.LENGTH_LONG).show();
                                Intent in=new Intent(SignupActivity.this,ConfirmcodeActivity.class);
                                in.putExtra("phone",phone);
                                in.putExtra("walletid",walletid);
                                startActivity(in);
                                finish();
                            }
                            else if(!err.equals("")) {
                                prgDialog.hide();
                                Toast.makeText(SignupActivity.this,err,Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        prgDialog.hide();
                    }
                });
            }
        });
    }

}
