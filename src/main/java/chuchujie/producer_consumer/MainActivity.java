package main.java.chuchujie.producer_consumer;

import main.java.chuchujie.producer_consumer.method.BlockingQueueModel;

public class MainActivity {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        IFactoryModel model = new BlockingQueueModel(3);

        for (int i = 0; i < 2; i++) {
            new Thread(model.newRunnableConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newRunnableProducer()).start();
        }

    }
}
