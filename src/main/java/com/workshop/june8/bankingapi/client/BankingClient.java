package com.workshop.june8.bankingapi.client;

import com.workshop.june8.bankingapi.exception.BankingApiException;
import com.workshop.june8.bankingapi.model.Account;
import com.workshop.june8.bankingapi.service.BankingApi;
import com.workshop.june8.bankingapi.service.LoanApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankingClient {

    private static final String PIN = "6295";

    BankingApi bankingApi;
    LoanApi loanApi;

    @Autowired
    public BankingClient(BankingApi bankingApi, LoanApi loanApi){
        this.bankingApi = bankingApi;
        this.loanApi = loanApi;
    }

    public void login() throws BankingApiException {
        bankingApi.login(PIN);
    }

    public boolean transfer( double amount, String accountFrom, String accountTo) throws BankingApiException {

        login();

        if(bankingApi.getBalance(accountFrom) < amount){
            throw new BankingApiException("Amount exceeds balance");
        }

        bankingApi.transfer(amount, accountFrom, accountTo);

        return true;

    }

    public double applyForLone( Account account) throws BankingApiException {

        double balance = bankingApi.getBalance(account.getAccountNr());

        if(balance < 0){
            throw new BankingApiException("Loan declined: balance negative");
        }

        if(loanApi.hasCurrentDebts(account.getBsn())){
            throw new BankingApiException("Loan declined: open debts");
        }

        return( 2 * balance);

    }

}
