package com.workshop.june8.bankingapi.service;

import com.workshop.june8.bankingapi.exception.BankingApiException;
import com.workshop.june8.bankingapi.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface BankingApi {

    public Account login(String pincode) throws BankingApiException;

    public boolean transfer(double amount, String accountNrFrom,String accountNrTo) throws BankingApiException;

    public double getBalance(String accountNr) throws BankingApiException;

    public boolean applyForLone(Account account) throws BankingApiException;

}
