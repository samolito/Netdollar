package com.wallet.netdollar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    /*@SerializedName("username")
    @Expose
    private String username;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }*/
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("kdfParams")
    @Expose
    private String kdfParams;
    @SerializedName("walletid")
    @Expose
    private String walletid;
    @SerializedName("apollo_test")
    @Expose
    private String apollo_test;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getKdfParams() {
        return kdfParams;
    }

    public void setKdfParams(String kdfParams) {
        this.kdfParams = kdfParams;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getApollo_test() {
        return apollo_test;
    }

    public void setApollo_test(String apollo_test) {
        this.apollo_test = apollo_test;
    }
}
