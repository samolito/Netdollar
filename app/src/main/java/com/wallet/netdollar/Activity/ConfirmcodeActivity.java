package com.wallet.netdollar.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.netdollar.Api.RetrofitClient;
import com.wallet.netdollar.R;
import com.wallet.netdollar.models.AESHelper;
import com.wallet.netdollar.models.Auth;
import com.wallet.netdollar.models.ConfirmResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.spongycastle.util.encoders.Base64;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import androidmads.library.qrgenearator.QRGEncoder;
import customfonts.PinEntryEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmcodeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTitle,txt1,txt2,txt3;
    Button btn_confirmer;
    PinEntryEditText edtconfirmer;
    ProgressDialog prgDialog;
    String code="",data="";
    byte[] deriveKey=null;
    String pubkeyString="",privkeyString="";
    String accountID_encypt="",keychain="",mainData="";
    String decrptText="",passHash="",passUnHash="";
    PublicKey publicKey=null;
    PrivateKey privateKey=null;
    KeyPairGenerator kpg;
    KeyPair kp;
    AESHelper aes=new AESHelper();
    Auth auth=new Auth();
    private String new_password="",EncrpytpasswSalt;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/NetDollar/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String msg="",walletid;
    String err="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmcode);
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle=findViewById(R.id.txttitle);
        txtTitle.setText(R.string.titre_verifier);
        txt1=findViewById(R.id.tv1);
        txt2=findViewById(R.id.tv2);
        txt3=findViewById(R.id.tv3);
        edtconfirmer= findViewById(R.id.edtEntryconfirmer);
        confirmerCode();
    }

    public void confirmerCode()
    {
       edtconfirmer.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
              if (s.length() == "123456".length()) {
                  code=s.toString();
                   //Toast.makeText(ConfirmcodeActivity.this, code, Toast.LENGTH_SHORT).show();
                   edtconfirmer.setText(null);
                    setData();
               }
           }
       });
    }

    public void setData()
    {
        String phone =getIntent().getStringExtra("phone");
        walletid=getIntent().getStringExtra("walletid");
        //String.format("{\"Name\":\"%s\", \"Email\":\"%s\", \"Addresss\":\"%s\"}",phone,passHash,accountID_encypt);
        JSONArray kdfparams=null;
        try {
            data="{\"algorithm\":\"scrypt\",\"bits\":256,\"n\":2,\"r\":8,\"p\":1,\"passwordHashAlgorithm\":\"sha256\",\"hashRounds\":524288}";
          kdfparams = new JSONArray(data);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String saltS="";
        String accountId=aes.getAccountID();
        byte[] salt=null,passwSalted=null;
        byte[]phonebyte=null;
        try {
            //generate public and private Key
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024 );
            kp = kpg.generateKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();

            //Generate salt
            salt= aes.getSalt();

            //password salted
            passHash=aes.hashPassword(walletid,salt);
            saltS= Base64.toBase64String(salt);
            ///////////////////////////////////////////////////
            pubkeyString=Base64.toBase64String(publicKey.getEncoded());
            privkeyString=Base64.toBase64String(privateKey.getEncoded());
            //convert publicKey to string
           // pubkeyString = auth.bytesToString(Base64.encode(publicKey.getEncoded()));
            //Toast.makeText(getApplicationContext(),"Pub :"+pubkeyString ,Toast.LENGTH_LONG).show();
            //convert String to publicKey
             //privkeyString=auth.bytesToString(Base64.encode(privateKey.getEncoded()));

            //encrypt password
            //passHash=auth.Encrypt(new_password,publicKey);

           // PublicKey new_pk=pk.hashCode();

            //passUnHash=auth.Decrypt(passHash,privateKey);
            //deriveKey=aes.deriveKey(new_password,salt);
            //Generate Account id
            accountId=aes.getAccountID();

            accountID_encypt=Base64.toBase64String(accountId.getBytes());
            //Encrypt
            //accountID_encypt=auth.encryptData(accountId,publicKey);
           // keychain=auth.Encrypt(passHash,publicKey);
           // mainData=auth.Encrypt(phone+""+accountId,publicKey);
           // decrptText=aes.decrypt(new_password,encryptAccount,salt,deriveKey);
           // publicKey=aes.bytesToString(deriveKey);
           String keyc = String.format("{\"User\":\"%s\", \"Password\":\"%s\", }",phone,passHash);
           keychain=auth.Encrypt(keyc,publicKey);
           String main=String.format("{ \"AccountID\":\"%s\"}",accountID_encypt);
            mainData= auth.Encrypt(main,publicKey);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
        }
        /*catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        msg="Votre compte a ete cree avec succes";
        SharedPrefs.saveShared(this,"username",phone);
        SharedPrefs.saveShared(this,"password",new_password);
        SharedPrefs.saveShared(this,"salt",saltS);
        SharedPrefs.saveShared(this,"accountId",accountID_encypt);
        SharedPrefs.saveShared(this,"privateKey",privkeyString);
        SharedPrefs.saveShared(this,"publicKey",pubkeyString);
        SharedPreferences sharedPref=getApplicationContext().getSharedPreferences("signup", Context.MODE_PRIVATE);
        txt1.setText("public key  : "+pubkeyString);
        txt2.setText("Keychain : "+keychain);
        txt3.setText("Account : "+mainData);
        //Log.d("pass: ",passHash);
        //prgDialog.show();
        Toast.makeText(ConfirmcodeActivity.this, "salt :"+saltS, Toast.LENGTH_SHORT).show();
       // confirmDialog();

       // prgDialog.show();
        Call<ConfirmResponse> call= RetrofitClient.getInstance().getApi().createuser(phone,passHash,accountID_encypt,saltS,kdfparams,pubkeyString,mainData,keychain,code,true);
        call.enqueue(new Callback<ConfirmResponse>() {
            @Override
            public void onResponse(Call<ConfirmResponse> call, Response<ConfirmResponse> response) {
                ConfirmResponse signupres=response.body();
                Toast.makeText(ConfirmcodeActivity.this,response.body().toString(), Toast.LENGTH_SHORT).show();

//                String respons=new String(signupres.getStatus());

                if ( signupres.getError().equals(null) ) {
                   // Toast.makeText(ConfirmcodeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    confirmDialog();
                    Intent intent = new Intent(ConfirmcodeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    prgDialog.hide();
                    msg = new String(signupres.getMessage());
                    err = new String(signupres.getError());
                        Toast.makeText(ConfirmcodeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ConfirmcodeActivity.this, err, Toast.LENGTH_SHORT).show();
                        // finish();

                }
            }

            @Override
            public void onFailure(Call<ConfirmResponse> call, Throwable t) {
                prgDialog.hide();
            }
        });
    }

    public void confirmDialog()
    {
// get prompts.xml view
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("");
        builder.setMessage("Votre compte a été créé avec succes");
        builder.setPositiveButton("FINI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // EnregistrerParcelle();
                Intent intent = new Intent(ConfirmcodeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
