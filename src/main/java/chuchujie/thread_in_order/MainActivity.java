package main.java.chuchujie.thread_in_order;

import sun.java2d.loops.GraphicsPrimitive;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");
//
//        Thread t1 = new Thread(new JoinThread("a"));
//        Thread t2 = new Thread(new JoinThread("b"));
//        Thread t3 = new Thread(new JoinThread("c"));
//
//        t1.start();
//        t1.join();
//
//        t2.start();
//        t2.join();
//
//        t3.start();
//        t3.join();

//        Object a = "锁①";
//        Object b = "锁②";
//        Object c = "锁③";
//        WaitNotifyThread pa = new WaitNotifyThread("A", c, a);
//        WaitNotifyThread pb = new WaitNotifyThread("B", a, b);
//        WaitNotifyThread pc = new WaitNotifyThread("C", b, c);
//
//        new Thread(pa).start();
//        Thread.sleep(10);// 保证初始 ABC 的启动顺序
//        new Thread(pb).start();
//        Thread.sleep(10);
//        new Thread(pc).start();
//        Thread.sleep(10);

//         Semaphore A = new Semaphore(1);
//         Semaphore B = new Semaphore(0);
//         Semaphore C = new Semaphore(0);
//
//        Thread t1 = new Thread(new SemaphoreThread("a", A, B));
//        Thread t2 = new Thread(new SemaphoreThread("b", B, C));
//        Thread t3 = new Thread(new SemaphoreThread("c", C, A));
//
//        t1.start();
//        t2.start();
//        t3.start();


        ReentrantLock lock = new ReentrantLock();


        Condition cona = lock.newCondition();
        Condition conb = lock.newCondition();
        Condition conc = lock.newCondition();

        Thread t1 = new Thread(new LockConditionThread("A", lock, 0,  cona, conb));
        Thread t2 = new Thread(new LockConditionThread("B", lock, 1,  conb, conc));
        Thread t3 = new Thread(new LockConditionThread("C", lock, 2,  conc, cona));

        t1.start();
        t2.start();
        t3.start();
    }


}
