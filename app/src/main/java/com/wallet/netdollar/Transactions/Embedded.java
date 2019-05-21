package com.wallet.netdollar.Transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

 public class Embedded {

     @SerializedName("records")
     @Expose
     private List<Record> records = null;

     public List<Record> getRecords() {
         return records;
     }

     public void setRecords(List<Record> records) {
         this.records = records;
     }

     @Override
     public int hashCode() {
         return new HashCodeBuilder().append(records).toHashCode();
     }

     @Override
     public boolean equals(Object other) {
         if (other == this) {
             return true;
         }
         if ((other instanceof Embedded) == false) {
             return false;
         }
         Embedded rhs = ((Embedded) other);
         return new EqualsBuilder().append(records, rhs.records).isEquals();
     }

}
