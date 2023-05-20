package Threads;

import cache.InMemoryCache;

public class ThreadCacheUpdate extends Thread{
public void run()
{
    System.out.println("Update Cache");
    InMemoryCache.updateCache();
}
}
