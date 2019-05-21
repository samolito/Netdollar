package com.wallet.netdollar.Transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Succeeds {

        @SerializedName("href")
        @Expose
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(href).toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Succeeds) == false) {
                return false;
            }
            Succeeds rhs = ((Succeeds) other);
            return new EqualsBuilder().append(href, rhs.href).isEquals();
        }

    }
