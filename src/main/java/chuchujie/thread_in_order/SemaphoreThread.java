package main.java.chuchujie.thread_in_order;

public class SemaphoreThread implements Runnable {

    //Semaphore可以控制某个资源可被同时访问的个数，通过 acquire() 获取一个许可，如果没有就等待，而 release()
    //单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合
    //Semaphore可以控制某个共享资源可被同时访问的次数,即可以维护当前访问某一共享资源的线程个数,并提供了同步机制.例如控制某一个文件允许的并发访问的数量.
    //例如网吧里有100台机器,那么最多只能提供100个人同时上网,当来了第101个客人的时候,就需要等着,一旦有一个人人下机,就可以立马得到了个空机位补上去.这个就是信号量的概念.

    @Override
    public void run() {

    }
}
