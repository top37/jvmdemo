package demo.thread.xxl;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import net.openhft.affinity.Affinity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * linux 可用，所以使用xxl的方式进行探测
 */
public class ThreadAffinity0XxlJob extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(CpuXxlJob.class);


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        logger.info("GLUE Java XXL-JOB, ThreadAffinityXxlJob  opr");

        int cpus = Runtime.getRuntime().availableProcessors();

        logger.info("cpu数量 ===> {}", cpus);
        printCpuOnThread("main");
        Thread t0 = new Thread(new MyTask("task0"),"task0");
        Thread t1 = new Thread(new MyTask("task1"));
        t0.start();
        t1.start();
        t0.join();
        t1.join();

        return new ReturnT<>(ReturnT.SUCCESS_CODE, "success");
    }



    static class MyTask implements Runnable{
        public MyTask(String name) {
            this.name = name;
        }
        String name;
        @Override
        public void run() {
            //指定cpu运行该线程
            for(int i = 0; i < 30;i++){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                printCpuOnThread(name);
                logger.info("[{}]=is running on thread=[{}] with Affinity cpu=[{}] and Affinity threadName=[{}]",Thread.currentThread().getName(),Thread.currentThread().getId(),Affinity.getCpu(),Affinity.getThreadId());
            }

        }
    }
    static void printCpuOnThread(String name){
        logger.info("[{}]=is running on thread=[{}] with Affinity cpu=[{}] and Affinity threadName=[{}]",name,Thread.currentThread().getId(),Affinity.getCpu(),Affinity.getThreadId());
    }
}
