package com.wallet.netdollar.models;

import com.wallet.netdollar.Transactions.Transactions;

import java.util.List;

public class TransactionsResponse {
private String link;
private List<Transactions> trans;

    public TransactionsResponse(String link, List<Transactions> transactions) {
        this.link = link;
        this.trans = transactions;
    }

    public String getLink() {
        return link;
    }

    public List<Transactions> getTrans() {
        return trans;
    }
}
