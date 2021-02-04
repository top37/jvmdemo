package demo.thread.xxl;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import net.openhft.affinity.Affinity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * linux 可用，所以使用xxl的方式进行探测
 */
/**
 * @author 单强 2020年08月21日
 *
 * 粘贴至glue web ide中，这个功能的实现，可以看一看；蛮实用的一个功能，可实现生产环境的调试；
 * 猜测大概过程：源码入db，编译源码，通过classloader动态加载进内存；
 * 其生成字节码后可直接注入到Spring容器上，优点东西，可以着重关注一下
 */
public class CpuXxlJob extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(CpuXxlJob.class);

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        logger.info("GLUE Java XXL-JOB, redis  opr");

        // TODO code application logic here
        logger.info("--------------------------");
        logger.info("cpu count ==> {}",Runtime.getRuntime().availableProcessors());
        logger.info("--------------------------");
        for (int i = 0; i < 5; i++) {
            new samplethread(i);
        }
        // create a new thread
        //samplethread1.run();
        try {
            for (int i = 5; i > 0; i--) {
                logger.info("Main Thread: " + i + "\t" + new Date());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            logger.info("Main thread interrupted.");
        }
        logger.info("Main thread exiting.");

        return new ReturnT<>(ReturnT.SUCCESS_CODE, "success");
    }


    static class samplethread implements Runnable {

        Thread t;

        samplethread(int i) {
            // Create a new, second thread
            t = new Thread(this, Integer.toString(i));
            logger.info("Child thread Creation NO: " + i + "\t" + t.getName());



            t.start(); // Start the thread
            // t.run();

        }

        @Override
        public void run() {

            try {
                for (int i = 5; i > 0; i--) {

                    logger.info("Child Thread Run: " + i + "\t" + t.getName() + "\t" + new Date());
                    logger.info("cpu == "+ Affinity.getCpu());
                    // Let the thread sleep for a while.
                    logger.info("****************************");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                logger.info("Child interrupted.");
            }
            logger.info("Exiting child thread.");
        }
    }

}
