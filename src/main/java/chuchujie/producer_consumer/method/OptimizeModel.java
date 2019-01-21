package main.java.chuchujie.producer_consumer.method;

import main.java.chuchujie.producer_consumer.AbstractConsumer;
import main.java.chuchujie.producer_consumer.AbstractProducer;
import main.java.chuchujie.producer_consumer.IBaseModel;
import main.java.chuchujie.producer_consumer.Task;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OptimizeModel implements IBaseModel {

    private final int capacity;
    private final Lock CONSUME_LOCK = new ReentrantLock();
    private final Condition NOT_EMPTY = CONSUME_LOCK.newCondition();
    private final Lock PRODUCE_LOCK = new ReentrantLock();
    private final Condition NOT_FULL = PRODUCE_LOCK.newCondition();
    private final AtomicInteger increTaskNo = new AtomicInteger(0);
    private final BufferQueue<Task> bufferQueue = new BufferQueue<>();

    public OptimizeModel(int capacity) {
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
                System.out.println("消费者尝试拿锁");
                CONSUME_LOCK.lockInterruptibly();
                System.out.println("--->消费者成功拿到<---");
                while (bufferQueue.getLength() == 0) {
                    System.out.println("buffer is empty...，消费者阻塞");
                    NOT_EMPTY.await();
                }
                Task task = bufferQueue.poll();
                assert task != null;
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.id);
                NOT_EMPTY.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("消费者锁释放");
                CONSUME_LOCK.unlock();
            }

            System.out.println("当前队列长度：" + bufferQueue.getLength());
            if (bufferQueue.getLength() < capacity) {
                try {
                    PRODUCE_LOCK.lockInterruptibly();
                    NOT_FULL.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    PRODUCE_LOCK.unlock();
                }
            }

        }
    }

    private class ProducerImpl extends AbstractProducer {
        @Override
        public void produce() {
            // 不定期生产，模拟随机的用户请求
            try {
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("生产者尝试拿锁");
                PRODUCE_LOCK.lockInterruptibly();
                System.out.println("--->生产者成功拿到<---");
                while (bufferQueue.getLength() == capacity) {
                    System.out.println("buffer is full...生产者阻塞");
                    NOT_FULL.await();
                }
                Task task = new Task(increTaskNo.getAndIncrement());
                bufferQueue.offer(task);
                System.out.println("produce: " + task.id);
                NOT_FULL.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("生产者锁释放");
                PRODUCE_LOCK.unlock();
            }

            System.out.println("当前队列长度：" + bufferQueue.getLength());
            if (bufferQueue.getLength() > 0) {
                try {
                    CONSUME_LOCK.lockInterruptibly();
                    NOT_EMPTY.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    CONSUME_LOCK.unlock();
                }
            }
        }
    }


    private static class BufferQueue<E> {

        private Node head; //头部
        private Node tail; //尾部
        private AtomicInteger bufLen = new AtomicInteger(0);

        public int getLength() {
            return bufLen.get();
        }

        BufferQueue() {
            head = tail = new Node(null);
        }

        public void offer(E e) {
            tail.next = new Node(e);
            tail = tail.next;
            bufLen.getAndIncrement();
        }

        public E poll() {
            head = head.next;
            E e = head.item;
            head.item = null;
            bufLen.decrementAndGet();
            return e;
        }

        private class Node {
            E item;
            Node next;

            Node(E item) {
                this.item = item;
            }
        }

    }


}
