package com.workshop.june8.calculation.webservice;

import com.workshop.june8.calculation.exception.EndpointException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BankingServiceImpl implements BankingService {

    @Override
    public double getInterestRate(LocalDate date) throws EndpointException {

        // This method calls an internal or external service that in general is not available during unit testing

        return 1.5;

    }

}
