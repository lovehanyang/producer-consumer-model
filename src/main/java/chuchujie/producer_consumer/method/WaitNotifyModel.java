package main.java.chuchujie.producer_consumer.method;

import main.java.chuchujie.producer_consumer.AbstractConsumer;
import main.java.chuchujie.producer_consumer.AbstractProducer;
import main.java.chuchujie.producer_consumer.IBaseModel;
import main.java.chuchujie.producer_consumer.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitNotifyModel implements IBaseModel {

    private final int capacity;
    private final Queue<Task> buffer = new LinkedList<>();
    private final AtomicInteger increTaskNo = new AtomicInteger(0);
    private final Object BUFFER_LOCK = new Object();

    public WaitNotifyModel(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }


    private class ConsumerImpl extends AbstractConsumer {

        @Override
        public void consume() throws InterruptedException {
            synchronized (BUFFER_LOCK) {
                while (buffer.size() == 0) {
                    BUFFER_LOCK.wait();
                }
                Task task = buffer.poll();
                assert task != null;
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.id);
                BUFFER_LOCK.notifyAll();
            }

        }
    }

    private class ProducerImpl extends AbstractProducer {

        @Override
        public void produce() {
            // 不定期生产，模拟随机的用户请求
            try {
                Thread.sleep((long) (Math.random() * 1000));
                synchronized (BUFFER_LOCK) {
                    while (buffer.size() == capacity) {
                        BUFFER_LOCK.wait();
                    }
                    Task task = new Task(increTaskNo.getAndIncrement());
                    buffer.offer(task);
                    System.out.println("produce:" + task.id);
                    BUFFER_LOCK.notifyAll();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
