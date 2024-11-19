package com.workshop.june8.calculation;

import com.workshop.june8.bankingapi.exception.BankingApiException;
import com.workshop.june8.calculation.exception.CalculationException;
import com.workshop.june8.calculation.exception.EndpointException;
import com.workshop.june8.calculation.webservice.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CalculationTest {

    @InjectMocks
    InterestCalculator interestCalculator;

    @Mock
    BankingService bankingService;

    @BeforeEach
    public void setup(){

    }

    @Test
    @DisplayName("Berekening die correct verloopt")
    public void calculationOkTest() throws EndpointException, CalculationException {
        when(bankingService.getInterestRate(LocalDate.now()))
                .thenReturn(0.5);

        assertEquals( 3375.0, interestCalculator.calculateInterest(1000D, 2, LocalDate.now()));

    }

    @Test
    @DisplayName("Berekening die een exceptie geeft")
    public void calculationNotOkTest() throws EndpointException {

        when(bankingService.getInterestRate(LocalDate.now()))
                .thenThrow( new EndpointException("Netwerk error"));

        Exception exception = assertThrows(CalculationException.class, () -> {
            interestCalculator.calculateInterest(1000D, 2, LocalDate.now());
        });

        assertEquals( "Calculation failed due to: Netwerk error", exception.getMessage());

    }
}
