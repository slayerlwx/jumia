package task.cdiscount;

import DO.jumia.SeleniumDo;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.cdiscount.CdiscountService;
import utils.Driver;
import utils.WebElementUtils;

import java.util.List;

/**
 * @program: jumia
 * @description:cdiscount 线程类
 * @author: lwx
 * @create: 2018-05-04 11:28
 **/

public class CdiscountTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(CdiscountTask.class);
    /**
     * 账号和密码
     */
    private List<SeleniumDo> accounts;

    /**
     * sku信息,评语
     */
    private List<String> cdiscountSku, cdiscountReceive;

    public CdiscountTask(List<SeleniumDo> accounts, List<String> cdiscountSku, List<String> cdiscountReceive) {
        this.accounts = accounts;
        this.cdiscountSku = cdiscountSku;
        this.cdiscountReceive = cdiscountReceive;
    }

    @Override
    public void run() {
        WebElementUtils webElementUtils = new WebElementUtils();
        //获取无头浏览器
        //CdiscountService cdiscountService = new CdiscountService(Driver.getNoHeadChromeDriverInstance(), webElementUtils);
        //获取谷歌浏览器
        CdiscountService cdiscountService = new CdiscountService(Driver.getChromeDriverInstance(), webElementUtils);
        cdiscountService.runCdiscount(accounts, cdiscountSku, cdiscountReceive);
    }
}
