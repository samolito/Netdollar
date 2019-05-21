package com.wallet.netdollar.Transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Links {
    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("account")
    @Expose
    private Account account;
    @SerializedName("ledger")
    @Expose
    private Ledger ledger;
    @SerializedName("operations")
    @Expose
    private Operations operations;
    @SerializedName("effects")
    @Expose
    private Effects effects;
    @SerializedName("precedes")
    @Expose
    private Precedes precedes;
    @SerializedName("succeeds")
    @Expose
    private Succeeds succeeds;

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public Operations getOperations() {
        return operations;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    public Effects getEffects() {
        return effects;
    }

    public void setEffects(Effects effects) {
        this.effects = effects;
    }

    public Precedes getPrecedes() {
        return precedes;
    }

    public void setPrecedes(Precedes precedes) {
        this.precedes = precedes;
    }

    public Succeeds getSucceeds() {
        return succeeds;
    }

    public void setSucceeds(Succeeds succeeds) {
        this.succeeds = succeeds;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(precedes).append(account).append(effects).append(self).append(operations).append(succeeds).append(ledger).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Links) == false) {
            return false;
        }
        Links rhs = ((Links) other);
        return new EqualsBuilder().append(precedes, rhs.precedes).append(account, rhs.account).append(effects, rhs.effects).append(self, rhs.self).append(operations, rhs.operations).append(succeeds, rhs.succeeds).append(ledger, rhs.ledger).isEquals();
    }
}
