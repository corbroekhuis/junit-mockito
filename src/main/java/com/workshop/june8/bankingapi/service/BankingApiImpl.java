package com.workshop.june8.bankingapi.service;

import com.workshop.june8.bankingapi.exception.BankingApiException;
import com.workshop.june8.bankingapi.model.Account;
import org.springframework.stereotype.Service;

@Service
public class BankingApiImpl implements BankingApi{

    @Override
    public Account login(String pincode) throws BankingApiException {

        // This method calls an internal or external service that in general is not available during unit testing

        return null;
    }

    @Override
    public boolean transfer(double amount, String accountNrFrom, String accountNrTo) throws BankingApiException {

        // This method calls an internal or external service that in general is not available during unit testing

        return false;

    }

    @Override
    public double getBalance(String accountNr) throws BankingApiException {

        // This method calls an internal or external service that in general is not available during unit testing

        return 0;

    }

    @Override
    public boolean applyForLone(Account account) throws BankingApiException {

        // This method calls an internal or external service that in general is not available during unit testing

        return false;

    }

}
