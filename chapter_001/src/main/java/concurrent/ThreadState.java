package concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println(getThreadNameAndState(first));
        System.out.println(getThreadNameAndState(second));
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(getThreadNameAndState(first));
            System.out.println(getThreadNameAndState(second));
        }
        System.out.println(getThreadNameAndState(first));
        System.out.println(getThreadNameAndState(second));
        System.out.println("work completed");
    }

    static String getThreadNameAndState(Thread thread) {
        return thread.getName().concat(" is ").concat(thread.getState().toString());
    }
}
