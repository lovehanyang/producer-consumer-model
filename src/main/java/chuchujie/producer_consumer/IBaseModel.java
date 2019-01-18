package main.java.chuchujie.producer_consumer;

public interface IBaseModel {
    Runnable newRunnableConsumer();

    Runnable newRunnableProducer();
}
