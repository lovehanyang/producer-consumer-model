package main.java.chuchujie.producer_consumer;

public interface BaseContract {

    public interface Consumer {
        void consume() throws InterruptedException;
    }

    public interface Producer {
        void produce();
    }
}
