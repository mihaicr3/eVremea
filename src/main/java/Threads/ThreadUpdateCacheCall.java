package Threads;

public class ThreadUpdateCacheCall extends Thread{
    public synchronized void run()
    {
        while(true)
        {
            try {
                wait(1200000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }

            ThreadCacheUpdate threadCacheUpdate=new ThreadCacheUpdate();
            threadCacheUpdate.start();

        }
    }
}
