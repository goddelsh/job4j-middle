package struct;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void transfer() throws IllegalAccessException {
        UserStorage storage = new UserStorage();

        User user = new User(1, 1000);

        assertTrue(storage.add(user));
        assertTrue(storage.add(new User(2, 500)));
        storage.transfer(1, 2, 50);

        user.setAmount(100000);

        assertThat(storage.getUser(1).getAmount(), is(950));
        assertThat(storage.getUser(2).getAmount(), is(550));
    }
}