package multithreads;

public class MasterSlaveBarrier {


    public synchronized  void tryMaster() throws InterruptedException {
       System.out.println("Выполнение основного блока");
    }

    public synchronized  void trySlave() throws InterruptedException {
        this.wait();
        System.out.println("Выполнение второго блока");
    }

    public synchronized  void doneMaster() {
        System.out.println("Основной блок закончен");
        this.notifyAll();
    }

    public synchronized  void doneSlave() {
        System.out.println("Второй блок закончен");
    }
}