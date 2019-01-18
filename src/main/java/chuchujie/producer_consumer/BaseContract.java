package main.java.chuchujie.producer_consumer;

public interface BaseContract {

    public interface Consumer {
        void consume();
    }

    public interface Producer {
        void produce();
    }
}
