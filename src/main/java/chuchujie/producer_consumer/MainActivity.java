package main.java.chuchujie.producer_consumer;

import main.java.chuchujie.producer_consumer.method.BlockingQueueModel;
import main.java.chuchujie.producer_consumer.method.LockConditionModel;
import main.java.chuchujie.producer_consumer.method.OptimizeModel;
import main.java.chuchujie.producer_consumer.method.WaitNotifyModel;

public class MainActivity {

    public static void main(String[] args) {
        System.out.println("Hello World!");

//        IBaseModel model = new BlockingQueueModel(3);
//
//        for (int i = 0; i < 2; i++) {
//            new Thread(model.newRunnableConsumer()).start();
//        }
//        for (int i = 0; i < 5; i++) {
//            new Thread(model.newRunnableProducer()).start();
//        }

//        IBaseModel model2 = new WaitNotifyModel(10);
//        for (int i = 0; i < 5; i++) {
//            new Thread(model2.newRunnableConsumer()).start();
//        }
//
//        for (int i = 0; i < 2; i++) {
//            new Thread(model2.newRunnableProducer()).start();
//        }

//        IBaseModel model3 = new LockConditionModel(10);
//        for (int i = 0; i < 5; i++) {
//            new Thread(model3.newRunnableConsumer()).start();
//        }
//
//        for (int i = 0; i < 2; i++) {
//            new Thread(model3.newRunnableProducer()).start();
//        }

        IBaseModel model3 = new OptimizeModel(10);
        for (int i = 0; i < 5; i++) {
            new Thread(model3.newRunnableConsumer()).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(model3.newRunnableProducer()).start();
        }

    }
}
