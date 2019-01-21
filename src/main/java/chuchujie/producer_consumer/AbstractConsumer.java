package main.java.chuchujie.producer_consumer;

public abstract class AbstractConsumer implements BaseContract.Consumer, Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
