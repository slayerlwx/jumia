package view.jumia;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.jumia.JumiaFiveStarTask;
import utils.Property;
import utils.TxtUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @program: myselenium
 * @description: 启动类
 * @author: lwx
 * @create: 2018-04-25 19:46
 **/

public class JumiaFiveStar{
    private static final Logger logger = LoggerFactory.getLogger(JumiaFiveStar.class);
    /**
     * task类
     */
    private static JumiaFiveStarTask jumiaTask = null;
    private ExecutorService fixedThreadPool = null;

    public static void main(String[] args) {
        try {
            //加载文件
            String accountPath = Property.getProjectPath() + "/file/jumia账号密码.txt";
            List<SeleniumDo> accounts = TxtUtil.readobjectlistTxt(accountPath);
            logger.info("account path:" + accountPath + "---总数：" + accounts.size());
            String commentPath = Property.getProjectPath() + "/file/jumiaSku商品信息.txt";
            List<String> productInfo = TxtUtil.readpingyuTxt(commentPath);
            logger.info("jumiaSku商品信息地址:" + commentPath + "---总数：" + productInfo.size());
            String loadUrl = "https://www.jumia.com.ng";

            //启动任务线程类
            jumiaTask = new JumiaFiveStarTask(accounts, productInfo, loadUrl);
            jumiaTask.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
