package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @program: myselenium
 * @description: 睡眠工具类
 * @author: lwx
 * @create: 2018-04-25 16:28
 **/

public class Sleep {
    private static Logger logger = LoggerFactory.getLogger(Sleep.class);

    public static void sleep() {
        int num = (int) (Math.random() * 4 + 2);
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            logger.info("睡眠失效");
        }

    }
}
