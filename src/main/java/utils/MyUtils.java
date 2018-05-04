package utils;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: myselenium
 * @description: 睡眠工具类
 * @author: lwx
 * @create: 2018-04-25 16:28
 **/

public class MyUtils {
    private static Logger logger = LoggerFactory.getLogger(MyUtils.class);

    public static void sleep() {
        int num = (int) (Math.random() * 3 + 2);
        try {
            TimeUnit.SECONDS.sleep(num);
        } catch (InterruptedException e) {
            logger.info("睡眠失效");
        }

    }
    public static void shuffle(SeleniumDo seleniumDo,List<String> stringShuffle){
        try {
            Collections.shuffle(stringShuffle);
            String recevies[] = stringShuffle.get(0).split("\\|");
            //评论标题
            seleniumDo.setReviewTitle(recevies[0]);
            //评论内容
            seleniumDo.setReviewContent(recevies[1]);
            logger.info("打乱评论数组成功");
        } catch (Exception e) {
            seleniumDo.setReviewTitle("good!");
            seleniumDo.setReviewContent("good!");
            logger.info("打乱评论数组失败用good!");
        }
    }
}
