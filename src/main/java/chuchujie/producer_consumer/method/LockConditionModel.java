package main.java.chuchujie.producer_consumer.method;

import main.java.chuchujie.producer_consumer.AbstractConsumer;
import main.java.chuchujie.producer_consumer.AbstractProducer;
import main.java.chuchujie.producer_consumer.IBaseModel;
import main.java.chuchujie.producer_consumer.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionModel implements IBaseModel {

    private final int capacity;
    private final Lock BUFFER_LOCK = new ReentrantLock();
    private final Condition BUFFER_COND = BUFFER_LOCK.newCondition();
    private final Queue<Task> buffer = new LinkedList<>();
    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public LockConditionModel(int capacity) {
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
        public void consume() {
            try {
                BUFFER_LOCK.lockInterruptibly();
                System.out.println("消费者获得锁");
                while (buffer.size() == 0) {
                    System.out.println("消费者线程执行await()");
                    BUFFER_COND.await();
                }

                Task task = buffer.poll();
                assert task != null;
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.id);
                BUFFER_COND.signalAll();
                System.out.println("消费者signalAll():唤醒其它进程");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("消费者释放锁");
                BUFFER_LOCK.unlock();
            }


        }
    }

    private class ProducerImpl extends AbstractProducer {

        @Override
        public void produce() {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                BUFFER_LOCK.lockInterruptibly();
                System.out.println("生产者获得锁");
                while (buffer.size() == capacity) {
                    System.out.println("生产者线程执行await()");
                    BUFFER_COND.await();
                }
                Task task = new Task(increTaskNo.getAndIncrement());
                buffer.offer(task);
                System.out.println("produce: " + task.id);
                BUFFER_COND.signalAll();
                System.out.println("生产者signalAll():唤醒其它进程");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("生产者释放锁");
                BUFFER_LOCK.unlock();
            }
        }
    }


}
