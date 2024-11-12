package com.workshop.june8.bankingapi.exception;

public class BankingApiException extends Exception {

    public BankingApiException(){
        super();
    }

    public BankingApiException( String message){
        super( message);
    }

}
