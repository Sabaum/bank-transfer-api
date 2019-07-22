package com.resolut.banktransferapi.account.service;

import com.resolut.banktransferapi.transfer.view.request.TransferRequest;

public interface AccountService {

    void transfer(TransferRequest transferRequest);

}
