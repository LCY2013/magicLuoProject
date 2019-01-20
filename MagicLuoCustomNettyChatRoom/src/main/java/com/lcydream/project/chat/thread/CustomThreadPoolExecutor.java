package com.lcydream.project.chat.thread;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CustomThreadPoolExecutor
 * 自定义的线程池
 * @author Luo Chun Yun
 * @date 2018/10/30 15:45
 */
public class CustomThreadPoolExecutor {

    /**
     * 日志控件
     */
    private static Logger logger = Logger.getLogger(CustomThreadPoolExecutor.class);

    /**
     * 私有化构造方法
     */
    private CustomThreadPoolExecutor(){}


    /**
     * 销毁线程池
     */
    public void destory() {
        LazyExecutorService.POOL.shutdownNow();
    }

    /**
     * 内部类实现单例
     */
    private static class LazyExecutorService{
        private final static ThreadPoolExecutor POOL = init();
        /**
         * 线程池初始化方法
         *
         * corePoolSize 核心线程池大小----10
         * maximumPoolSize 最大线程池大小----30
         * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
         * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
         * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列 LinkedBlockingDeque
         * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
         * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
         * 							即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
         * 						          任务会交给RejectedExecutionHandler来处理
         */
        private static ThreadPoolExecutor init() {
            return new ThreadPoolExecutor(
                    10,
                    30,
                    3,
                    TimeUnit.MINUTES,
                    new ArrayBlockingQueue<>(10),
                    new CustomThreadFactory(),
                    new CustomRejectedExecutionHandler());
        }

        private static class CustomThreadFactory implements ThreadFactory {

            private AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
                logger.info(threadName+" create");
                thread.setName(threadName);
                return thread;
            }
        }


        private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // 记录异常
                // 报警处理等
                logger.info("exception :" + executor);
            }
        }
    }

    public final static ExecutorService getCustomThreadPoolExecutor() {
        return LazyExecutorService.POOL;
    }



    // 测试构造的线程池
    public static void main(String[] args) {
        //CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();
        // 1.初始化
        //exec.init();

        ExecutorService pool = CustomThreadPoolExecutor.getCustomThreadPoolExecutor();
        for(int i=1; i<100; i++) {
            System.out.println("提交第" + i + "个任务!");
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("running=====");
                }
            });
        }



        // 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了
        // exec.destory();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
