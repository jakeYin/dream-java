package tk.mybatis.springboot.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL-5490 on 2018/9/22.
 */
public class CreateThreadPool {

    //加载字节码的时候初始化线程池
    private static ThreadPoolExecutor executor;

    public static ThreadPoolExecutor getThreadPool(){
        if(executor!=null){
            //当服务器性能出现问题，打开以下代码查看线程池的使用情况
//			System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
            return executor;
        }else{
            //同步创建线程池
            synchronized(CreateThreadPool.class){
                if(executor==null){
                    //线程池大小为150个，最大线程数为200个，队列中最多允许50个线程等待队列
                    executor = new ThreadPoolExecutor(20, 180, 20, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(50));
                }else{
                    return executor;
                }
            }
            return executor;
        }
    }
}
