package com.resolut.banktransferapi.transfer.view.request;

import java.math.BigDecimal;

public class TransferRequest {

    private Integer accountIdFrom;
    private BigDecimal amount;

    public Integer getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Integer accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
