package view.shopee;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.shopee.ShopeeTask;
import utils.Property;
import utils.TxtUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @program: jumia
 * @description: shopee 分页刷启动类
 * @author: lwx
 * @create: 2018-05-03 11:22
 **/

public class Shopee {
    private static Logger logger= LoggerFactory.getLogger(Shopee.class);
    /**
     * shopee线程类
     */
    private static ShopeeTask shopeeTask;
    private ExecutorService service;

    public static void main(String[] args) {
        try {
            String accountPath = Property.getProjectPath() + "/file/shopee账号密码.txt";
            List<SeleniumDo> accounts = TxtUtil.readobjectlistTxt(accountPath);
            logger.info("shopee账号地址:" + accountPath + "----总数:" +accounts.size());

            String shopeeInfoPath =Property.getProjectPath() + "/file/shopee详情地址.txt";
            List<String> shopeeInfo=TxtUtil.readlistTxt(shopeeInfoPath);
            logger.info("shopee链接地址:" + shopeeInfoPath + "-----总数" + shopeeInfo.size());

            shopeeTask =new ShopeeTask(accounts,shopeeInfo);
            shopeeTask.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
