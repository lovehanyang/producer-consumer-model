package main.java.chuchujie.thread_in_order;

public class WaitNotifyThread implements Runnable {

    private String name;
    private final Object pre;
    private final Object self;

    public WaitNotifyThread(String name, Object pre, Object self) {
        this.name = name;
        this.pre = pre;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (pre) {
                System.out.println("当前线程->" + name + "获取到pre锁->" + pre);
                synchronized (self) {
                    System.out.println("当前线程->" + name + "获取到self锁->" + self);
                    System.out.println("打印：" + name);

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count--;
                    self.notifyAll();
                }
                System.out.println("当前线程->" + name + ",释放->" + self);
                try {
                    System.out.println("当前线程->" + name + ",释放->" + pre + ",开始阻塞");
                    //如果count==0,表示这是最后一次打印操作，通过notifyAll操作释放对象锁
                    if (count != 0)
                        pre.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
