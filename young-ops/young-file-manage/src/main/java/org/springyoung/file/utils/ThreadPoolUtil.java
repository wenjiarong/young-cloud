package org.springyoung.file.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadPoolUtil
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class ThreadPoolUtil {

    private static ScheduledThreadPoolExecutor threadPool;

    /**
     * 获取线程池实例(3~20个线程)
     *
     * @return
     */
    public static synchronized ScheduledThreadPoolExecutor getInstance() {
        if (threadPool == null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                AtomicInteger threadId = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread1 = new Thread(r);
                    thread1.setName("tftp-thread-" + threadId.getAndIncrement());
                    return thread1;
                }
            };
            threadPool = new ScheduledThreadPoolExecutor(3, threadFactory);
            threadPool.setMaximumPoolSize(20);
        }
        //
        return threadPool;
    }


}
