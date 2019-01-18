package main.java.chuchujie.producer_consumer;

public abstract class AbstractConsumer implements BaseContract.Consumer, Runnable {

    @Override
    public void run() {
        while (true) {
            consume();
        }
    }
}
