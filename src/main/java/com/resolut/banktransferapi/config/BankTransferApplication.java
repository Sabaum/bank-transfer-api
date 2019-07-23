package com.resolut.banktransferapi.config;

import com.resolut.banktransferapi.account.view.AccountController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class BankTransferApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AccountController.class);
        return classes;
    }
}