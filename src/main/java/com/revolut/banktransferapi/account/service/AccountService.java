package com.revolut.banktransferapi.account.service;

import com.revolut.banktransferapi.account.view.request.TransferRequest;

public interface AccountService {

    void transfer(TransferRequest transferRequest);

}
