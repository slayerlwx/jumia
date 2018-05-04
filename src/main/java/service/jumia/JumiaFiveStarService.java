package service.jumia;

import DO.jumia.SeleniumDo;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;
import utils.MyUtils;
import utils.WebElementUtils;

import java.util.List;


/**
 * @program: myselenium
 * @description: 业务实现类
 * @author: lwx
 * @create: 2018-04-25 19:47
 **/
public class JumiaFiveStarService {
    private static final Logger logger = LoggerFactory.getLogger(JumiaFiveStarService.class);
    /**
     * 驱动类
     */
    private ChromeDriver driver;
    /**
     * 元素工具类
     */
    private WebElementUtils webElementUtils;
    /**
     * js 执行方法类
     */
    private JavascriptExecutor js = null;

    /**
     * jumiaService 构造方法
     *
     * @param driver
     * @param webElementUtils
     */
    public JumiaFiveStarService(ChromeDriver driver, WebElementUtils webElementUtils) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.webElementUtils = webElementUtils;
    }

    /**
     * 业务主体类
     *
     * @param accounts
     * @param productInfo
     * @param loadUrl
     */
    public void runJumiaFiveStarSerive(List<SeleniumDo> accounts, List<String> productInfo, String loadUrl) {
        //循环读取账号和密码文件,以及sku信息文件
        for (SeleniumDo jumiaDO : accounts) {
            //调用登录方法
            this.login(jumiaDO.getAccount(), jumiaDO.getPassword());

            MyUtils.sleep();

            for (String productInfoSku : productInfo) {
                driver.get(loadUrl);
                logger.info("加载url:" + loadUrl);

                //在搜索栏目找到对应的搜索输入框
                WebElement searchBox = webElementUtils.findByElement(driver, "searchBox");
                searchBox.sendKeys(productInfoSku);

                //搜索按钮
                WebElement searchButton = webElementUtils.findByElement(driver, "searchButton");
                searchButton.click();

                MyUtils.sleep();
                //产品缩略图
                WebElement productThumbnailLink = webElementUtils.findByElement(driver, "productThumbnail");
                productThumbnailLink.click();
                //js.executeScript("arguments[0].click();",productThumbnailLink);
                MyUtils.sleep();

                //详情选项列表 ,跳转评论页面按钮
                WebElement writeReceiveButton =webElementUtils.findByElement(driver,"writeReceiveButton");
                writeReceiveButton.click();

                MyUtils.sleep();
                //填写五星评价
                this.writeReceive();

            }
            this.outlogin();
            logger.info("登出,当前账号执行完成:" + jumiaDO.getAccount());
        }
       //所有任务执行完毕,退出浏览器
        Driver.destory(driver);
    }

    /**
     * 登录方法
     *
     * @param account
     * @param password
     */
    private void login(String account, String password) {
        driver.get("https://www.jumia.com.ng/customer/account/login/");

        MyUtils.sleep();

        driver.get("https://www.jumia.com.ng/customer/account/login/");
        //定位账号输入框
        WebElement accountBox = webElementUtils.findByElement(driver, "loginAccountBox");
        accountBox.clear();
        accountBox.sendKeys(account);
        //定位密码框
        WebElement passwordBox = webElementUtils.findByElement(driver, "loginPasswordBox");
        passwordBox.clear();
        passwordBox.sendKeys(password);
        //提交
        WebElement loginButton = webElementUtils.findByElement(driver, "loginButton");
        loginButton.click();
    }

    /**
     * 填写评价
     */
    private void writeReceive() {
        //五星评价
        WebElement clickFiveStar = webElementUtils.findByElements(driver, "clickFiveStar", 0);
        clickFiveStar.click();

        //按照bug提交评论
        WebElement commitReceive = webElementUtils.findByElement(driver, "commitRecive");
        js.executeScript("arguments[0].click();", commitReceive);
        logger.info("评价成功");
        MyUtils.sleep();
    }

    /**
     * 登出
     */
    public void outlogin() {
        driver.get("https://www.walmart.com/account/logout");
    }
}
