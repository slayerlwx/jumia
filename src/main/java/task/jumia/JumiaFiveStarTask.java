package task.jumia;

import DO.jumia.SeleniumDo;
import service.jumia.JumiaFiveStarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Driver;
import utils.WebElementUtils;

import java.util.List;

/**
 * @program: myselenium
 * @description: jumia 线程类
 * @author: lwx
 * @create: 2018-04-25 19:57
 **/

public class JumiaFiveStarTask implements Runnable {
	  private static Logger logger = LoggerFactory.getLogger(JumiaFiveStarTask.class);
    /**
     * 账号和密码
     */
    private List<SeleniumDo> accounts;

    /**
     * sku信息
     */
    private List<String> productInfo;
    /**
     * 加载路径
     */
    private String loadUrl = "https://www.jumia.com.ng";
    /**
     * @param accounts
     * @param productInfo
     * @param loadUrl
     */
    public JumiaFiveStarTask(List<SeleniumDo> accounts, List<String> productInfo, String loadUrl) {
        this.accounts = accounts;
        this.productInfo = productInfo;
        this.loadUrl = loadUrl;
    }

    @Override
    public void run() {
        WebElementUtils webElementUtils =new WebElementUtils();
        //获取无头浏览器实例
        JumiaFiveStarService jumiaService = new JumiaFiveStarService(Driver.getNoHeadChromeDriverInstance(), webElementUtils);
        //获取浏览器实例
        //JumiaFiveStarService jumiaService = new JumiaFiveStarService(Driver.getChromeDriverInstance(), webElementUtils);
        jumiaService.runJumiaFiveStarSerive(accounts, productInfo, loadUrl);
    }
}
