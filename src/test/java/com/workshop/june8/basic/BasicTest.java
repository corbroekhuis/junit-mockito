package com.workshop.june8.basic;

import com.workshop.june8.bankingapi.exception.BankingApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class BasicTest {

    @Test
    public void basicSendTest() throws Exception {
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.sendMail("Hello", "john@comp.org");
        emailHandler.sendMail("Hello", "john@comp.org");
        emailHandler.sendMail("Hello", "john@comp.org");
        emailHandler.sendMail("Hello", "john@comp.org");
        int result = emailHandler.getSent();
        assertEquals( 4, result);
    }

    @Test
    public void basicSendTestWithException() throws Exception {
        EmailHandler emailHandler = new EmailHandler();

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            emailHandler.sendMail("Hello", "johncomp.org");
        });

        assertEquals("Not a valid email address", exception.getMessage());


    }

}
