package solutions.workshop.june8.bankingapi;

import com.workshop.june8.bankingapi.client.BankingClient;
import com.workshop.june8.bankingapi.exception.BankingApiException;
import com.workshop.june8.bankingapi.model.Account;
import com.workshop.june8.bankingapi.service.BankingApi;
import com.workshop.june8.bankingapi.service.LoanApi;
import com.workshop.june8.calculation.exception.CalculationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankingApiTest {

    @Mock
    BankingApi bankingApi;

    @Mock
    LoanApi loanApi;

    @InjectMocks
    BankingClient bankingClient;

    @Test
    public void TransferSufficientFundsTest() throws BankingApiException {

        when(bankingApi.getBalance("12345")).thenReturn(100000D);

        boolean succes = bankingClient.transfer(23000D, "12345", "23456");
        assertEquals(true, succes);

    }

    @Test
    public void TransferInSufficientFundsTest() throws BankingApiException {

        when(bankingApi.getBalance("12345")).thenReturn(1000D);

        Exception exception = Assertions.assertThrows(BankingApiException.class, () -> {
            bankingClient.transfer(23000D, "12345", "23456");
        });

        assertEquals("Amount exceeds balance", exception.getMessage());

    }

    @Test
    public void applyForLoanOkTest() throws BankingApiException {

        Account account = new Account();
        account.setAccountNr("7624542");
        account.setBsn("12345890");
        account.setName("Julia");

        when(loanApi.hasCurrentDebts(account.getBsn())).thenReturn(false);
        when(bankingApi.getBalance(account.getAccountNr())).thenReturn(12000D);

        double amount = bankingClient.applyForLone(account);

        assertEquals(24000D, amount);

    }

    @Test
    public void applyForLoanNotOkTest() throws BankingApiException {

        Account account = new Account();
        account.setAccountNr("7624542");
        account.setBsn("12345890");
        account.setName("Julia");

        when(loanApi.hasCurrentDebts(account.getBsn())).thenReturn(false);
        when(bankingApi.getBalance(account.getAccountNr())).thenReturn(-1200D);

        Exception exception = Assertions.assertThrows(BankingApiException.class, () -> {
            bankingClient.applyForLone(account);
        });

        assertEquals("Loan declined: balance negative", exception.getMessage());

    }

    @Test
    public void applyForLoanNotOk2Test() throws BankingApiException {

        Account account = new Account();
        account.setAccountNr("7624542");
        account.setBsn("12345890");
        account.setName("Julia");

        when(loanApi.hasCurrentDebts(account.getBsn())).thenReturn(true);
        when(bankingApi.getBalance(account.getAccountNr())).thenReturn(12000D);

        Exception exception = Assertions.assertThrows(BankingApiException.class, () -> {
            bankingClient.applyForLone(account);
        });

        assertEquals("Loan declined: open debts", exception.getMessage());

    }

    @Test
    public void applyForLoanNotOk3Test() throws BankingApiException {

        Account account = new Account();
        account.setAccountNr("7624542");
        account.setBsn("12345890");
        account.setName("Julia");

        when(loanApi.hasCurrentDebts(account.getBsn())).thenReturn(true).thenThrow();
        when(bankingApi.getBalance(account.getAccountNr())).thenReturn(12000D);

        Exception exception = Assertions.assertThrows(BankingApiException.class, () -> {
            bankingClient.applyForLone(account);
        });

        assertEquals("Loan declined: open debts", exception.getMessage());

    }


}

