package concurrent;

public class ConsoleProgress implements Runnable {

    private String bar = "|";

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String str = process();
            System.out.print("\r Loading ... " + str);
        }
    }

    private String process() {
        if (this.bar == "|") {
            this.bar = "/";
        } else if (this.bar == "/") {
            this.bar = "-";
        } else if (this.bar == "-") {
            this.bar = "\\";
        } else if (this.bar == "\\") {
            this.bar = "|";
        }
        return this.bar;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
