package view.cdiscount;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.cdiscount.CdiscountTask;
import utils.Property;
import utils.TxtUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @program: jumia
 * @description: cdiscount启动类
 * @author: lwx
 * @create: 2018-05-04 11:25
 **/

public class Cdiscount {
    private static Logger logger = LoggerFactory.getLogger(Cdiscount.class);
    /**
     * cdiscount 线程类
     */
    private  static  CdiscountTask cdiscountTask;
    private ExecutorService service;

    public static void main(String[] args) {
        //读取文件
        //加载文件
        String accountPath = Property.getProjectPath() + "/file/cdiscount账号密码.txt";
        List<SeleniumDo> accounts = TxtUtil.readobjectlistTxt(accountPath);
        logger.info("cdiscount账号密码地址::" + accountPath + "---总数：" + accounts.size());

        String cdiscountSkuPath = Property.getProjectPath() + "/file/cdiscount详情地址.txt";
        List<String> cdiscountSku = TxtUtil.readlistTxt(cdiscountSkuPath);
        logger.info("cdiscount详情地址:" + cdiscountSkuPath + "---总数：" + cdiscountSku.size());

        String cdiscountReceivePath =Property.getProjectPath() + "/file/cdiscount评论.txt";
        List<String> cdiscountReceive =TxtUtil.readpingyuTxt(cdiscountReceivePath);
        logger.info("cdiscount评论地址::" + cdiscountReceivePath + "---总数：" + cdiscountReceive.size());

        cdiscountTask =new CdiscountTask(accounts,cdiscountSku,cdiscountReceive);
        cdiscountTask.run();
    }
}
