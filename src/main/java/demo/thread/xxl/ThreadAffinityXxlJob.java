package demo.thread.xxl;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import net.openhft.affinity.Affinity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
/**
 * linux 可用，所以使用xxl的方式进行探测
 */
public class ThreadAffinityXxlJob extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(CpuXxlJob.class);


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        logger.info("GLUE Java XXL-JOB, ThreadAffinityXxlJob  opr");

        int cpus = Runtime.getRuntime().availableProcessors();
        Random random=new Random();
        int cpuForTask0=random.nextInt(cpus);
        int cpuForTask1=random.nextInt(cpus);
        printCpuOnThread("main");
        Thread t0 = new Thread(new MyTask(cpuForTask0, "task0"));
        Thread t1 = new Thread(new MyTask(cpuForTask1, "task1"));
        t0.start();
        t1.start();
        t0.join();
        t1.join();

        return new ReturnT<>(ReturnT.SUCCESS_CODE, "success");
    }



    static class MyTask implements Runnable{
        public MyTask(int cpu, String name) {
            this.cpu = cpu;
            this.name = name;
        }
        int cpu;
        String name;
        @Override
        public void run() {
            //指定cpu运行该线程
            Affinity.setAffinity(cpu);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printCpuOnThread(name);
        }
    }
    static void printCpuOnThread(String name){
        logger.info(name +" is running on thread"+Thread.currentThread().toString()+" with cpu "+Affinity.getCpu());
        logger.info(name +" is running on thread"+Thread.currentThread().toString()+" with cpu "+Affinity.getCpu());
    }
}
