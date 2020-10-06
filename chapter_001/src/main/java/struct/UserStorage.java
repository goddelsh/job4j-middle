package struct;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;


@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    final private Map<Integer, User> users;

    public UserStorage() {
        this.users = new HashMap();
    }

    public synchronized boolean add(User user) {
        boolean result = false;
        if (!this.users.containsKey(user.getId())) {
            this.users.put(user.getId(), new User(user.getId(), user.getAmount()));
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (this.users.containsKey(user.getId())) {
            this.users.put(user.getId(), new User(user.getId(), user.getAmount()));
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        if (this.users.containsKey(user.getId())) {
            this.users.remove(user.getId());
            result = true;
        }
        return result;
    }

    public synchronized User getUser(int userId) {
        User result = null;
        if (this.users.containsKey(userId)) {
            result = this.users.get(userId);
        }
        return result;
    }

    public synchronized void transfer(int fromId, int toId, int amount) throws IllegalAccessException {
        User fromUser = this.users.get(fromId);
        User toUser = this.users.get(toId);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
        } else {
            throw new IllegalAccessException("User id is inaccessible!");
        }
    }

}
