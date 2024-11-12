package solutions.workshop.june8.basic;

import com.workshop.june8.basic.EmailHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
public class BasicTest {

    @Mock
    EmailHandler emailHandlerMock;

    @Spy
    EmailHandler emailHandlerSpy;

    @BeforeEach
    public void setup(){
      //  MockitoAnnotations.initMocks(this);

    }


    @Test
    public void testEmailHandlerMock() throws Exception {

        doNothing().when(emailHandlerMock).sendMail("Hello", "jan@domain.com");

        emailHandlerMock.sendMail("Hello", "piet@domain.com");

        Mockito.verify(emailHandlerMock).sendMail("Hello", "piet@domain.com");

        System.out.println("Emails sent through mock: " + emailHandlerMock.getSent());
        assertEquals(0, emailHandlerMock.getSent());

    }

    @Test
    public void testEmailHandlerSpy() throws Exception {


        emailHandlerSpy.sendMail("Hello", "jan@domain.com");
        emailHandlerSpy.sendMail("Hello", "piet@domain.com");

        Mockito.verify(emailHandlerSpy).sendMail("Hello", "jan@domain.com");
        Mockito.verify(emailHandlerSpy).sendMail("Hello", "piet@domain.com");

        System.out.println( "Emails sent through spy: " +emailHandlerSpy.getSent());

        assertEquals(2, emailHandlerSpy.getSent());

    }


}
