package main.java.chuchujie.producer_consumer;

public interface IFactoryModel {
    Runnable newRunnableConsumer();

    Runnable newRunnableProducer();
}
