package task.walmart;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.walmart.WalmartService;
import utils.Driver;
import utils.WebElementUtils;

import java.util.List;

/**
 * @program: jumia
 * @description:
 * @author: lwx
 * @create: 2018-05-02 16:24
 **/

public class WalamrtTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(WalamrtTask.class);
    /**
     * 账号和密码
     */
    private List<SeleniumDo> accounts;

    /**
     * sku信息,评语
     */
    private List<String> productInfo,walmartReceive;

    public WalamrtTask(List<SeleniumDo> accounts, List<String> productInfo, List<String> walmartReceive) {
        this.accounts =accounts;
        this.productInfo =productInfo;
        this.walmartReceive=walmartReceive;
    }

    @Override
    public void run() {
        WebElementUtils webElementUtils =new WebElementUtils();
        //获取无头浏览器
        //WalmartService walmartService =new WalmartService(Driver.getNoHeadChromeDriverInstance(),webElementUtils);
       //获取谷歌浏览器
        WalmartService walmartService =new WalmartService(Driver.getChromeDriverInstance(),webElementUtils);
        walmartService.runWalmart(accounts,productInfo,walmartReceive);
    }
}
