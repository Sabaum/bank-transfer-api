package com.resolut.banktransferapi.account.view.request;

import java.math.BigDecimal;

public class TransferRequest {

    private Integer accountIdFrom;
    private Integer accountIdTo;
    private BigDecimal amount;

    public Integer getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Integer accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public Integer getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(Integer accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
