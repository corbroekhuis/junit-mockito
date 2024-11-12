package com.workshop.june8.calculation.webservice;

import com.workshop.june8.calculation.exception.EndpointException;

import java.time.LocalDate;

public interface BankingService {

    public double getInterestRate(LocalDate date) throws EndpointException;

}
