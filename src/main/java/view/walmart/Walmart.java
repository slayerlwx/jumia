package view.walmart;


import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.walmart.WalamrtTask;
import utils.Property;
import utils.TxtUtil;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @program: jumia
 * @description: walmart启动类
 * @author: lwx
 * @create: 2018-05-02 16:20
 **/

public class Walmart {
    private static Logger logger = LoggerFactory.getLogger(Walmart.class);
    /**
     * walmart线程类
     */
    private static WalamrtTask walamrtTask;
    private ExecutorService fixedThreadPool = null;

    public static void main(String[] args) {
        try {
            //加载文件
            String accountPath = Property.getProjectPath() + "/file/walmartAccount.txt";
            List<SeleniumDo> accounts = TxtUtil.readobjectlistTxt(accountPath);
            logger.info("walmart账号和密码地址::" + accountPath + "---总数：" + accounts.size());

            String commentPath = Property.getProjectPath() + "/file/walmartSkuaddress.txt";
            List<String> productInfo = TxtUtil.readlistTxt(commentPath);
            logger.info("walmartsku地址:" + commentPath + "---总数：" + productInfo.size());

            String walmartReceivePath =Property.getProjectPath() + "/file/评语.txt";
            List<String> walmartReceive =TxtUtil.readpingyuTxt(walmartReceivePath);
            logger.info("walmart评语地址::" + walmartReceivePath + "---总数：" + walmartReceive.size());

            //启动任务线程类
            walamrtTask =new WalamrtTask(accounts,productInfo,walmartReceive);
            walamrtTask.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
