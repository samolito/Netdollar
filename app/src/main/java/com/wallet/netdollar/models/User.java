package com.wallet.netdollar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("kdfParams")
    @Expose
    private String kdfParams;
    @SerializedName("walletid")
    @Expose
    private String walletid;

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

}
