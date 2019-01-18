package main.java.chuchujie.producer_consumer;

public abstract class AbstractProducer implements BaseContract.Producer, Runnable {

    @Override
    public void run() {
        produce();
    }
}
