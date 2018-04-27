package task;

import jumia.DO.JumiaDO;
import jumia.service.JumiaService;
import org.openqa.selenium.WebDriver;
import utils.Driver;
import utils.WebElementUtils;

import java.util.List;

/**
 * @program: myselenium
 * @description: jumia 线程类
 * @author: lwx
 * @create: 2018-04-25 19:57
 **/

public class JumiaTask implements Runnable {
    /**
     * 账号和密码
     */
    private List<JumiaDO> accountPath;

    /**
     * sku信息
     */
    private List<String> productInfo;
    /**
     * 加载路径
     */
    private String loadUrl = "https://www.jumia.com.ng";

    /**
     * @param accountPath
     * @param productInfo
     * @param loadUrl
     */
    public JumiaTask(List<JumiaDO> accountPath, List<String> productInfo, String loadUrl) {
        this.accountPath = accountPath;
        this.productInfo = productInfo;
        this.loadUrl = loadUrl;
    }

    @Override
    public void run() {
        WebElementUtils webElementUtils = new WebElementUtils();
        //获取无头浏览器实例
        JumiaService jumiaService = new JumiaService(Driver.getNoHeadChromeDriverInstance(), webElementUtils);
        //获取浏览器实例
        //JumiaService jumiaService = new JumiaService(Driver.getChromeDriverInstance(), webElementUtils);
        jumiaService.runJumiaSerive(accountPath, productInfo, loadUrl);
    }
}
