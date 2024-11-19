package com.workshop.june8.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class BasicTest {

    static EmailHandler emailHandler;

    @Spy
    EmailHandler emailHandlerSpy;

    @Mock
    EmailHandler emailHandlerMock;

    @Test
    public void testUsingMock() throws IOException {

        emailHandlerMock.sendMail("Hello2", "j2ohn@comp.org");
        emailHandlerMock.sendMail("Hello1", "john@comp.org");

        verify(emailHandlerMock).sendMail("Hello2", "j2ohn@comp.org");
        verify(emailHandlerMock).sendMail("Hello1", "john@comp.org");

        assertEquals( 0, emailHandlerMock.getSent());
    }

//    @BeforeAll
//    public static void setUpAll(){
//        // Eenmalig: waarde send wordt in elke test opgehoogd
//        emailHandler = new EmailHandler();
//    }

    @BeforeEach
    public void setUpEach(){
        // Voor edere test: waarde send is in elke test weer 0
        emailHandler = new EmailHandler();
    }

    @Test
    public void testUsingSpy() throws IOException {

        emailHandlerSpy.sendMail("Hello2", "j2ohn@comp.org");
        emailHandlerSpy.sendMail("Hello1", "john@comp.org");

        verify(emailHandlerSpy).sendMail("Hello2", "j2ohn@comp.org");
        verify(emailHandlerSpy).sendMail("Hello1", "john@comp.org");

        assertEquals( 2, emailHandlerSpy.getSent());
    }

    @Test
    public void basicSendTest() throws Exception {

        emailHandler.sendMail("Hello", "john@comp.org");
        emailHandler.sendMail("Hello", "john@comp.org");
        emailHandler.sendMail("Hello", "john@comp.org");
        emailHandler.sendMail("Hello", "john@comp.org");
        int result = emailHandler.getSent();
        assertEquals( 4, result);
    }

    @Test
    public void basicSendTest2() throws Exception {

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
