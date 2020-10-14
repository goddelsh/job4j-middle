package pools;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class EmailNotificationTest {

    @Test
    public void testEmailInThreeThreads() throws InterruptedException {
        EmailNotification emailNotification = new EmailNotification();

        Thread firstThread = new Thread(() -> {
            emailNotification.emailTo(new User("Adam", "adam@mail.com"));
        });
        Thread secondThread = new Thread(() -> {
            emailNotification.emailTo(new User("Eve", "eve@mail.com"));
        });
        Thread thirdThread = new Thread(() -> {
            emailNotification.emailTo(new User("Avel", "avel@mail.com"));
        });

        firstThread.start();
        secondThread.start();
        thirdThread.start();
        firstThread.join();
        secondThread.join();
        thirdThread.join();

        emailNotification.close();

        assertThat(emailNotification.getLetters().size(), is(3));
    }
}