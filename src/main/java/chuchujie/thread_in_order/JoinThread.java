package main.java.chuchujie.thread_in_order;

public class JoinThread implements Runnable{
    String str;

    public JoinThread(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        System.out.println(str);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str + "end");
    }

}
