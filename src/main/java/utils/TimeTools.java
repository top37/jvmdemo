package utils;

import java.util.concurrent.TimeUnit;

public class TimeTools {


    /**
     * 线程休眠
     * @param seconds 秒数
     */
    public static void stop(Integer seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
