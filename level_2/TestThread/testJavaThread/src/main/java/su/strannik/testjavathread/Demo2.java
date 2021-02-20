package su.strannik.testjavathread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo2 {
    public static void main(String[] args) {
        System.out.println("Main thread id is: " + Thread.currentThread().getId());

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("new thread r1 id is: " + Thread.currentThread().getId());
            }
        };

        Thread t2 = new Thread(r1);
        t2.start();

        Thread t3 = new Thread(r1);
        t3.start();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("new thread t1 id is: " + Thread.currentThread().getId());
            }
        };
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        //t1.start();

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("cores: " + cores);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new MyRunnable(400));
        service.submit(new MyRunnable(2000));
        service.submit(new MyRunnable(50));
        service.submit(new MyRunnable(500));

        Future<String> result = service.submit(new MyCallable());
        if (result.isDone()) {
            try {
                String res = result.get();
                System.out.println("result: " + res);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("result not done");
        }

        service.shutdown();
    }
}

class MyRunnable implements Runnable
{
    private int pause;

    public MyRunnable(int pause) {
        this.pause = pause;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyRunnable id is: " + Thread.currentThread().getId());
        System.out.println("MyRunnable pause is: " + pause);
    }
}

class MyCallable implements Callable<String>
{

    @Override
    public String call() throws Exception {
        Thread.sleep(4000);
        return "hello";
    }
}
