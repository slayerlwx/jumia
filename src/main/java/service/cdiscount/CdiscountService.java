package service.cdiscount;

import DO.jumia.SeleniumDo;
import constant.Constant;
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
 * @program: jumia
 * @description: cdiscount刷评价
 * @author: lwx
 * @create: 2018-05-04 11:37
 **/

public class CdiscountService {
    private static Logger logger = LoggerFactory.getLogger(CdiscountService.class);
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

    public CdiscountService(ChromeDriver driver, WebElementUtils webElementUtils) {
        this.driver = driver;
        this.webElementUtils = webElementUtils;
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * cdiscount业务主体类
     *
     * @param accounts
     * @param cdiscountSku
     * @param cdiscountReceive
     */
    public void runCdiscount(List<SeleniumDo> accounts, List<String> cdiscountSku, List<String> cdiscountReceive) {
        for (SeleniumDo seleniumDo : accounts) {
            for (String cdisSku : cdiscountSku) {
                //清洗打乱评论
                MyUtils.shuffle(seleniumDo, cdiscountReceive);
                //开始运行
                driver.get(cdisSku);
                logger.info("加载url:" + cdisSku);
                MyUtils.sleep();
                //详情选项列表 ,跳转评论页面按钮
                try {
                    WebElement writeReceiveButton = webElementUtils.findByElement(driver, "writeReceiveButton");
                    js.executeScript("arguments[0].click();", writeReceiveButton);
                } catch (Exception e) {
                    logger.error("找不到跳转评论按钮,跳过此条链接:" + cdisSku);
                    continue;
                }
                MyUtils.sleep();
                //调登录
                String resultLogin = this.login(seleniumDo.getAccount(), seleniumDo.getPassword());
                if (resultLogin.equals(Constant.Result.FAILED)) {
                    logger.error("登录异常,跳过此条链接:" + cdisSku);
                    continue;
                }
                MyUtils.sleep();
                //调用评价
                String recevieResult = this.cdisReceive(seleniumDo.getReviewTitle(), seleniumDo.getReviewContent(), seleniumDo.getNickName());
                if (recevieResult.equals(Constant.ReceiveResult.RECEIVE_FAILED)) {
                    logger.error("此次评价失败,链接为:" + cdisSku);
                }
            }
            logger.info("当前账号执行完毕:" + seleniumDo.getAccount());
            this.outlogin();
        }
        Driver.destory(driver);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     */
    public String login(String account, String password) {
        //driver.get("https://clients.cdiscount.com/Account/Login.html?");
        //定位账号输入框
        try {
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
            return Constant.Result.SUCCESS;
        } catch (Exception e) {
            return Constant.Result.FAILED;
        }
    }

    /**
     * 评价
     *
     * @param title
     * @param content
     * @param nickName
     */
    public String cdisReceive(String title, String content, String nickName) {
        try {
            // 获取随机数，来选择 刷星星的数量    是五星还是四星
            try {
                int starNum = (int)(1+Math.random()*(10-1+1));
                //模拟星星点击按钮，刷4星好评
                if (starNum > 5){
                    js.executeScript("$(\".jsRatingValue\").val(4)");
                }else {
                    js.executeScript("$(\".jsRatingValue\").val(5)");
                }
            } catch (Exception e) {
                logger.info("刷星评价错误");
                return Constant.ReceiveResult.RECEIVE_FAILED;
            }

          /*  //找五星按钮
            WebElement clickFiveStar = webElementUtils.findByElement(driver, "clickFiveStar");
            clickFiveStar.click();*/
            //找评价标题
            WebElement reviewTitle = webElementUtils.findByElement(driver, "reviewTitle");
            reviewTitle.clear();
            reviewTitle.sendKeys(title);
            //找评价内容
            WebElement reviewContent = webElementUtils.findByElement(driver, "reviewContent");
            reviewContent.clear();
            reviewContent.sendKeys(content);
            WebElement writeNickName = webElementUtils.findByElement(driver, "writeNickName");
            writeNickName.clear();
            writeNickName.sendKeys(nickName);
            //提交评价
            WebElement commitReceive = webElementUtils.findByElements(driver, "commitRecive", 0);
            js.executeScript("arguments[0].click();", commitReceive);
            return Constant.ReceiveResult.RECEIVE_SUCCESS;
        } catch (Exception e) {
            return Constant.ReceiveResult.RECEIVE_FAILED;
        }
    }

    /**
     * 登出方法
     */
    public void outlogin() {
        driver.get("https://www.walmart.com/account/logout");
    }
}
