package main.java.chuchujie.producer_consumer.method;


import main.java.chuchujie.producer_consumer.AbstractConsumer;
import main.java.chuchujie.producer_consumer.AbstractProducer;
import main.java.chuchujie.producer_consumer.IBaseModel;
import main.java.chuchujie.producer_consumer.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueModel implements IBaseModel {

    private BlockingQueue<Task> queue;
    private AtomicInteger incrementTaskNo = new AtomicInteger(0);


    public BlockingQueueModel(int cap) {
        this.queue = new LinkedBlockingDeque<>(cap);
    }

    private class ProducerImpl extends AbstractProducer {

        @Override
        public void produce() {
            try {
                // 不定期生产，模拟随机的用户请求
                Thread.sleep((long) (1000 * Math.random()));
                Task task = new Task(incrementTaskNo.getAndIncrement());
                System.out.println("produce:" + task.id);
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConsumerImpl extends AbstractConsumer {
        @Override
        public void consume() {
            try {
                Task task = queue.take();
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep((long) (500 + Math.random() * 500));
                System.out.println("consume: " + task.id);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }
}
