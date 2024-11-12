package solutions.workshop.june8.calculation;

import com.workshop.june8.calculation.InterestCalculator;
import com.workshop.june8.calculation.exception.CalculationException;
import com.workshop.june8.calculation.exception.EndpointException;
import com.workshop.june8.calculation.webservice.BankingService;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CalculationTest {

    @InjectMocks
    InterestCalculator interestCalculator;

    @Mock
    BankingService bankingService;

    @Test
    public void calculateInterestTest() throws EndpointException, CalculationException {

        when(bankingService.getInterestRate( LocalDate.now())).thenReturn(0.04D);

        assertEquals( 1771.45D, interestCalculator.calculateInterest( 1400D, 5));

    }

    @Test
    public void calculateInterestWithDateTest() throws EndpointException, CalculationException {

        LocalDate date = LocalDate.now();
        when(bankingService.getInterestRate( date)).thenReturn(0.02D);
//        when(bankingService.getInterestRate( any( LocalDate.class))).thenReturn(0.02D);

        assertEquals( 1034.89D, interestCalculator.calculateInterest( 800D, 12, date));

    }

    @Test
    public void calculateInterestWithException() throws EndpointException {

        LocalDate date = LocalDate.now().plus( Period.of(0, -2, 3));

        when(bankingService.getInterestRate(date)).thenThrow( new EndpointException("Endpoint not available"));

        Exception exception = Assertions.assertThrows(CalculationException.class, () -> {
            interestCalculator.calculateInterest( 800D, 12, date);
        });

        assertEquals("Calculation failed due to: Endpoint not available", exception.getMessage());

    }

}
