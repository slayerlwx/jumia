package service.jumia;

import DO.jumia.SeleniumDo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;
import utils.MyUtils;
import utils.WebElementUtils;

import java.util.Collections;
import java.util.List;

/**
 * jumia 刷评价业务主体类
 *
 * @author 125C01063154
 */
public class JumiaReceiveService {
    private static Logger logger = LoggerFactory.getLogger(JumiaReceiveService.class);
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


    public JumiaReceiveService(ChromeDriver webDriver, WebElementUtils webElementUtils) {
        this.driver = webDriver;
        this.webElementUtils = webElementUtils;
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * 主体业务方法
     *
     * @param accounts
     * @param productInfo
     * @param jumiaReceive
     */
    public void runJumiaReceiveService(List<SeleniumDo> accounts, List<String> productInfo, List<String> jumiaReceive) {
        //循环读取账号和密码文件,以及sku信息文件
        for (SeleniumDo seleniumDo : accounts) {
            //调用登录方法
            this.login(seleniumDo.getAccount(), seleniumDo.getPassword());
            MyUtils.sleep();
            //循环读取SKU链接
            for (String skuAddress : productInfo) {
               MyUtils.shuffle(seleniumDo,jumiaReceive);
                //开始工作
                driver.get(skuAddress);
                logger.info("加载url:" + skuAddress);
                MyUtils.sleep();

                //找不到这个页面
                try {
                    WebElement noPage = driver.findElement(By.className("-warning"));
                    if (noPage.getText().equals("Sorry, the page you have requested was not found. Try searching for a brand or a keyword")) {
                        logger.info("此链接为无效链接,跳过处理:" + skuAddress);
                        continue;
                    }
                } catch (Exception e2) {
                    logger.info("链接正常,执行操作");
                }
                MyUtils.sleep();
                //详情选项列表 ,跳转评论页面按钮
                WebElement writeReceiveButton = webElementUtils.findByElement(driver, "writeReceiveButton");
                writeReceiveButton.click();
                MyUtils.sleep();
                this.receive(seleniumDo.getReviewTitle(), seleniumDo.getReviewContent(), seleniumDo.getPublishTitle());
            }
            logger.info("当前账号执行完毕:"+seleniumDo.getAccount());
            this.outlogin();
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
     * @param title
     * @param content
     * @param publishTitle
     */
    public void receive(String title, String content, String publishTitle) {
        //找五星按钮
        WebElement clickFiveStar = webElementUtils.findByElement(driver, "clickFiveStar");
        clickFiveStar.click();

        //找评价标题
        WebElement reviewTitle = webElementUtils.findByElement(driver, "reviewTitle");
        reviewTitle.clear();
        reviewTitle.sendKeys(title);
        //找评价内容
        WebElement reviewContent = webElementUtils.findByElement(driver, "reviewContent");
        reviewContent.clear();
        reviewContent.sendKeys(content);
        //找发布标题
        WebElement ReceivepublishTitle = webElementUtils.findByElement(driver, "publishTitle");
        ReceivepublishTitle.clear();
        ReceivepublishTitle.sendKeys(publishTitle);
        //提交评价
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
