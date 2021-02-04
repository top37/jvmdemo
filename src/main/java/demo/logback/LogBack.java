package demo.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBack {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        LogBack logback = new LogBack();
        logback.testLog();
    }

    private void testLog() {
        logger.debug("print debug log.");
        logger.info("print info log.");
        logger.error("print error log.");
    }
}
