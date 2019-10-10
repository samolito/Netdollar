package com.wallet.netdollar.Api;


import com.wallet.netdollar.models.ConfirmResponse;
import com.wallet.netdollar.models.LoginResponse;
import com.wallet.netdollar.models.SignupResponse;
import com.wallet.netdollar.models.TransactionsResponse;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
//    @POST("")
//    Call<ResponseBody>adduser(
//      //@Field("") String phone
//    );

    @FormUrlEncoded
    @POST("getparams")
    Call<LoginResponse>logger(
            @Field("username") String phone,
            @Field("walletid") String walletid
    );
    @FormUrlEncoded
    @POST("verifyPhone")
    Call<SignupResponse>signup(
            @Field("username") String phone
           // @Field("walletid") String walletid
    );
    @FormUrlEncoded
    @POST("create")
    Call<ConfirmResponse>createuser(
            @Field("username") String phone,
            @Field("walletId") String walletid,
            @Field("accountId") String accountid,
            @Field("salt") String salt,
            @Field("kdfParams") JSONArray kdfparams,
            @Field("publicKey") String publickey,
            @Field("mainData") String maindata,
            @Field("keychainData") String kcdata,
            @Field("verifyCode") String verifycode,
            @Field("isMobile") boolean isMobile
    );

    @GET("transactions")
    Call<TransactionsResponse>transactions(
    );

}
