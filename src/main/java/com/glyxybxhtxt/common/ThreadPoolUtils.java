package com.glyxybxhtxt.common;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author wzh
 * @Date 2021/5/28 6:53 下午
 * @Description 线程池工具类
 */
public class ThreadPoolUtils {
    private ThreadPoolUtils() {
    }

    private static int CORE_POOL_SIZE = 5;

    private static int MAX_POOL_SIZE = 100;

    private static int KEEP_ALIVE_TIME = 10000;

    private static BlockingQueue workQueue = new ArrayBlockingQueue(10);

    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        //这个工厂只是为线程做了个新的命名
        public Thread newThread(Runnable r) {
            return new Thread(r, "glmcThreadPool-thread:" + integer.getAndIncrement());
        }

    };

    private static ThreadPoolExecutor threadPool;

    static {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, workQueue, threadFactory);

    }

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

}


