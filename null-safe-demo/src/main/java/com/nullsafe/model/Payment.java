package com.nullsafe.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class Payment {
    private String id;
    private double amount;
    private String currency;
    private String description;

    public Payment(@Nonnull String id, double amount, @Nonnull String currency, @CheckForNull String description) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    @Nonnull
    public String getCurrency() {
        return currency;
    }

    @CheckForNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@CheckForNull String description) {
        this.description = description;
    }
}

