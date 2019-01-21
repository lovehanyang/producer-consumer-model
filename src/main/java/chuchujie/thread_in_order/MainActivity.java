package main.java.chuchujie.thread_in_order;

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

        Object a = "锁①";
        Object b = "锁②";
        Object c = "锁③";
        WaitNotifyThread pa = new WaitNotifyThread("A", c, a);
        WaitNotifyThread pb = new WaitNotifyThread("B", a, b);
        WaitNotifyThread pc = new WaitNotifyThread("C", b, c);

        new Thread(pa).start();
        Thread.sleep(10);// 保证初始 ABC 的启动顺序
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }


}
