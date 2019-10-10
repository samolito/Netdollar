package com.wallet.netdollar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmResponse {
   /* @SerializedName("data")
    @Expose
    private List<Object> data = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public ConfirmResponse withData(List<Object> data) {
        this.data = data;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ConfirmResponse withStatus(String status) {
        this.status = status;
        return this;
    }*/
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ConfirmResponse withError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConfirmResponse withMessage(String message) {
        this.message = message;
        return this;
    }
}
