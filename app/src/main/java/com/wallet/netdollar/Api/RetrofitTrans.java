package com.wallet.netdollar.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTrans {
    private static final  String BASE_URL="http://blockchain.netdollar.us/";
    private static RetrofitTrans mInstance;
    private Retrofit retrofit;

    private  RetrofitTrans(){
        retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitTrans getInstance(){
        if(mInstance==null){
            mInstance=new RetrofitTrans();
        }return mInstance;
    }

    public Api getApi(){
        return  retrofit.create(Api.class);
    }
}
