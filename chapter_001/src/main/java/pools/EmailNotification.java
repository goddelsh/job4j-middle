package pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private List<String> letters = new ArrayList<>();

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.execute(() -> {
              String subject = String.format("Notification %s to email %s.", user.getUsername(), user.getEmail());
              String body = String.format("Add a new event to %s", user.getUsername());
              this.send(subject, body, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String subject, String body, String email) {
        letters.add(String.format("Subject: %s Body: %s sended", subject, body));
    }

    public List<String> getLetters() {
        return letters;
    }

}
