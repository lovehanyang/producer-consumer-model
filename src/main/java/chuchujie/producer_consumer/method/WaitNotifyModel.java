package main.java.chuchujie.producer_consumer.method;

import main.java.chuchujie.producer_consumer.AbstractConsumer;
import main.java.chuchujie.producer_consumer.AbstractProducer;
import main.java.chuchujie.producer_consumer.IBaseModel;

import java.util.concurrent.atomic.AtomicInteger;

public class WaitNotifyModel implements IBaseModel {

    private AtomicInteger increTaskNo = new AtomicInteger(0);

    @Override
    public Runnable newRunnableConsumer() {
        return null;
    }

    @Override
    public Runnable newRunnableProducer() {
        return null;
    }


    private class ConsumerImpl extends AbstractConsumer {

        @Override
        public void consume() {

        }
    }

    private class ProducerImpl extends AbstractProducer {

        @Override
        public void produce() {

        }
    }
}
