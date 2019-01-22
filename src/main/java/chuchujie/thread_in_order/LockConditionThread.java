package main.java.chuchujie.thread_in_order;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class LockConditionThread implements Runnable {

    Condition self, next;
    String str;
    Lock lock;
    int currentIndex;
    static volatile int count;


    public LockConditionThread(String str, Lock lock, int currentIndex, Condition self, Condition next) {
        this.self = self;
        this.next = next;
        this.str = str;
        this.lock = lock;
        this.currentIndex = currentIndex;
    }

    @Override
    public void run() {
        try {
            lock.lockInterruptibly();

            for (int i = 0; i <= 10; i++) {
                System.out.println(str + "线程：执行第" + i + "次，当前count：" + count);
                while (count % 3 != currentIndex) {
                    System.out.println("线程" + str + "进入休眠状态" );
                    self.await();
                }
                System.out.println(str);
                count++;
                next.signal();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
