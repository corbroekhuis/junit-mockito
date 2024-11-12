package com.workshop.june8.bankingapi.service;

import org.springframework.stereotype.Service;

@Service
public class LoanApiImpl implements LoanApi{

    @Override
    public boolean hasCurrentDebts(String bsn) {
        return false;
    }

}
