package com.workshop.june8.bankingapi;

import com.workshop.june8.bankingapi.client.BankingClient;
import com.workshop.june8.bankingapi.exception.BankingApiException;
import com.workshop.june8.bankingapi.model.Account;
import com.workshop.june8.bankingapi.service.BankingApi;
import com.workshop.june8.bankingapi.service.LoanApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankingApiTest {

    @InjectMocks
    BankingClient bankingClient;

    @Mock
    BankingApi bankingApi;

    @Mock
    LoanApi loanApi;

    @Test
    public void applyForLoanOkTest() throws BankingApiException {

        Account account = new Account( "NL98A838845959","John Doe","123456789");

        when( bankingApi.getBalance(account.getAccountNr())).thenReturn(20000D);
        when( loanApi.hasCurrentDebts(account.getBsn())).thenReturn(false);

        double result = bankingClient.applyForLone( account);

        assertEquals( 40000,result);

    }






}
