package jumia;

import jumia.DO.JumiaDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.JumiaTask;
import utils.Property;
import utils.TxtUtil;

import java.util.List;

/**
 * @program: myselenium
 * @description: 启动类
 * @author: lwx
 * @create: 2018-04-25 19:46
 **/

public class JumiaFiveStar {
    private static Logger logger = LoggerFactory.getLogger(JumiaFiveStar.class);
    private static JumiaTask jumiaTask = null;

    public static void main(String[] args) {
        //加载文件
        String accountPath = Property.getProjectPath() + "/jumia账号密码.txt";
        List<JumiaDO> accounts = TxtUtil.readobjectlistTxt(accountPath);
        logger.info("account path:" + accountPath + "---总数：" + accounts.size());
        String commentPath = Property.getProjectPath() + "/jumiaSku商品信息.txt";
        List<String> productInfo = TxtUtil.readpingyuTxt(commentPath);
        logger.info("jumiaSku商品信息地址:" + commentPath + "---总数：" + productInfo.size());
        String loadUrl = "https://www.jumia.com.ng";

        //启动任务线程类
        jumiaTask = new JumiaTask(accounts, productInfo, loadUrl);
        jumiaTask.run();
    }
}
