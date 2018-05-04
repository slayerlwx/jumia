package task.shopee;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.shopee.ShopeeService;
import utils.Driver;
import utils.WebElementUtils;

import java.util.List; /**
 * @program: jumia
 * @description: shopee线程类
 * @author: lwx
 * @create: 2018-05-03 11:24
 **/

public class ShopeeTask implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(ShopeeTask.class);
    /**
     * 账号和密码
     */
    private List<SeleniumDo> accounts;

    /**
     * sku信息,评语
     */
    private List<String> shopeeInfo;
    public ShopeeTask(List<SeleniumDo> accounts, List<String> shopeeInfo) {
        this.accounts=accounts;
        this.shopeeInfo=shopeeInfo;
    }

    @Override
    public void run() {
        WebElementUtils webElementUtils =new WebElementUtils();
        //获取无头浏览器
        //ShopeeService shopeeService =new ShopeeService(Driver.getNoHeadChromeDriverInstance(),webElementUtils);
        //获取谷歌浏览器
        ShopeeService shopeeService =new ShopeeService(Driver.getChromeDriverInstance(),webElementUtils);
        shopeeService.runShopee(accounts,shopeeInfo);
    }
}
