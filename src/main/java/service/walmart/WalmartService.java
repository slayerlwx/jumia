package service.walmart;

import DO.jumia.SeleniumDo;
import constant.Constant;
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
 * @program: jumia
 * @description:
 * @author: lwx
 * @create: 2018-05-02 16:45
 **/

public class WalmartService {
    private static Logger logger = LoggerFactory.getLogger(WalmartService.class);
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


    public WalmartService(ChromeDriver driver, WebElementUtils webElementUtils) {
        this.driver = driver;
        this.webElementUtils = webElementUtils;
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * wakmart 业务主体类
     *
     * @param accounts
     * @param productInfo
     * @param walmartReceive
     */
    public void runWalmart(List<SeleniumDo> accounts, List<String> productInfo, List<String> walmartReceive) {
        //循环读取账号和密码文件,以及sku信息文件
        for (SeleniumDo seleniumDo : accounts) {
            //循环读取SKU 链接
            for (String skuAddress : productInfo) {
                //清洗打乱评论
                MyUtils.shuffle(seleniumDo,walmartReceive);
                //开始运行,加载链接地址
                driver.get(skuAddress);
                logger.info("加载url:" + skuAddress);
                MyUtils.sleep();

                try {
                    WebElement errorPage = driver.findElement(By.className("error-ErrorPage-copy"));
                    if (errorPage.getText().equals("The page you are looking for could not be found.")) {
                        logger.error("页面链接失效,跳转下一个页面链接,此失效链接为:" + skuAddress);
                        continue;
                    }
                } catch (Exception e4) {

                }

                MyUtils.sleep();
                //详情选项列表 ,跳转评论页面按钮
                try {
                    WebElement writeReceiveButton = webElementUtils.findByElement(driver, "writeReceiveButton");
                    writeReceiveButton.click();
                }catch (Exception e){
                    logger.error("找不到跳转评论按钮,跳过此条链接:" + skuAddress);
                    continue;
                }
                //调登录页面
                String loginResult =this.login(seleniumDo.getAccount(), seleniumDo.getPassword());
                if(loginResult.equals(Constant.Result.FAILED)){
                    logger.error("登录异常,跳过此条链接:" + skuAddress);
                    continue;
                }

                try {
                    driver.findElement(By.className("error-page-message-title-text")).getText().equals("Sorry...");
                    logger.error("评论页面异常,跳过此条链接:" + skuAddress);
                    continue;
                }catch(Exception e) {

                }

                try {
                    driver.findElement(By.className("alert-warning ")).getText().equals("You've already reviewed this item. Thanks for sharing your opinion with other customers!");
                    logger.info("已经评论过了,跳过此条链接:" + skuAddress);
                    continue;
                } catch (Exception e) {

                }

                MyUtils.sleep();
                //开始评价
               String receiveResult= this.receive(seleniumDo.getReviewTitle(),seleniumDo.getReviewContent(),seleniumDo.getNickName());
               if (receiveResult.equals(Constant.ReceiveResult.RECEIVE_FAILED)){
                   logger.info("此次评论失败,链接为:" + skuAddress);
               }
               logger.info("评论成功");
                MyUtils.sleep();
            }
            driver.get("https://www.walmart.com/account/logout");
            logger.info("当前账号执行完成");
        }
        //执行完毕 关闭浏览器
        Driver.destory(driver);
    }

    /**
     * 登录方法
     *
     * @param account
     * @param password
     */
    private String login(String account, String password) {
        try {
            driver.findElement(By.className("review-purchase-text")).getText().equals("Your review will help other shoppers make better purchase decisions.");
            logger.info("已经登录");
            return Constant.Result.SUCCESS;
         }catch (Exception e) {
            logger.info("准备登录");
        }
        MyUtils.sleep();
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

        try {
            WebElement rj = driver.findElement(By.className("bot-message"));
            logger.info("检测到人机验证界面，请验证:" + rj.getText());
            MyUtils.sleep();
            return Constant.Result.FAILED;
        } catch (Exception e) {

        }
        return Constant.Result.SUCCESS;
    }

    /**
     * 填写评价
     *
     * @param title
     * @param content
     * @param nickName
     * @param publishTitle
     */
    public String receive(String title, String content, String nickName ) {
        try {
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
            //找nickName
            try {
                WebElement writeNickName = webElementUtils.findByElement(driver, "writeNickName");
                writeNickName.clear();
                writeNickName.sendKeys(nickName);
            }catch (Exception e){
                logger.info("已存在了昵称,不需要填写");
            }
            //提交评价
            WebElement commitReceive = webElementUtils.findByElements(driver, "commitRecive",0);
            js.executeScript("arguments[0].click();", commitReceive);
            return Constant.ReceiveResult.RECEIVE_SUCCESS;
        } catch (Exception e) {
            return Constant.ReceiveResult.RECEIVE_FAILED;
        }
    }

    /**
     * 登出
     */
    public void outlogin() {
        driver.get("https://www.walmart.com/account/logout");
    }

}

