package service.shopee;

import DO.jumia.SeleniumDo;
import constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MyUtils;
import utils.WebElementUtils;

import java.util.List;
import java.util.Random;

/**
 * @program: jumia
 * @description: shopee分页业务
 * @author: lwx
 * @create: 2018-05-03 17:26
 **/

public class ShopeeService {
    private static Logger logger = LoggerFactory.getLogger(ShopeeService.class);
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

    public ShopeeService(ChromeDriver driver, WebElementUtils webElementUtils) {
        this.driver = driver;
        this.webElementUtils = webElementUtils;
        this.js = (JavascriptExecutor) driver;
    }

    public void runShopee(List<SeleniumDo> accounts, List<String> shopeeInfo) {
        // 对账户的站点类型设置字段接受
        String loginSite = "";
        for (SeleniumDo seleniumDo : accounts) {
            // 进行店铺站点判断judgeSiteResult
            loginSite = seleniumDo.getAccount();
            String judgeSiteResult = this.checkLoginSite(loginSite);
            // flag 标记用于控制站号登录验证语言这个功能
            int flag = 1;
            for (String shopeeSite : shopeeInfo) {
                // 进行店铺站点判断examinationResult
                String examinationResult = this.site(shopeeSite);
                logger.info("站点信息结果是:" + judgeSiteResult);
                logger.info("店铺属于的站点examinationResult是-" + examinationResult);
                logger.info("判断结果是：" + judgeSiteResult.equals(examinationResult));
                // 对账户的具体站点信息与店铺站点进行比对，如果该账户是该站点下的账户，允许登录处理，否则，结束这个账户，开始下个账户的判断
                if (judgeSiteResult.equals(examinationResult)) {
                    driver.get(shopeeSite);
                    logger.info("加载的链接是:" + shopeeSite);
                    MyUtils.sleep();
                    if (flag == 1) {
                        try {
                            WebElement chooseChinese = webElementUtils.findByElement(driver, "chooseChinese");
                            chooseChinese.click();
                        } catch (Exception e) {
                            logger.info("已经选择过了,不需要再次选择语言");
                        }
                        MyUtils.sleep();
                        // 点击登录按钮
                        WebElement clickLoginButton = webElementUtils.findByElement(driver, "clickLoginButton");
                        clickLoginButton.click();
                        //调登录页面
                        String loginResult = this.login(seleniumDo.getAccount(), seleniumDo.getPassword());
                        if (loginResult.equals(Constant.Result.FAILED)) {
                            logger.info("登录异常,跳过此站点:" + shopeeSite);
                            continue;
                        }
                        //关注店铺
                        try {
                            WebElement followSited = driver.findElement(By.cssSelector(
                                    "div.section-seller-overview-horizontal__buttons > div:nth-child(1) > button"));
                            js.executeScript("arguments[0].scrollIntoView();", followSited);
                            if (followSited.getText().equals("FOLLOWING")) {
                                logger.info("店铺已经被关注,无需关注");
                            }
                        } catch (Exception e) {
                            WebElement followSite = webElementUtils.findByElement(driver, "followSite");
                        }
                        MyUtils.sleep();
                        // 开始点赞, 获取页面的总的点赞数量
                        int numStar = this.countStar();
                        logger.info("总点赞的数量:" + numStar);
                        // 将当前的总的星星数量传入进去
                        this.clickToFlag(numStar);

                    }
                }
            }
        }
    }


    /**
     * 登录
     *
     * @param account
     * @param password
     */
    private String login(String account, String password) {
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

        String s1 = "您的帐号或密码不正确，请稍后再试";
        String s2 = "您的帐号尚未注册，现在注册使用Shopee？";
        try {
            WebElement error = driver.findElement(By.className("shopee-authen__error"));
            if (error.getText().equals(s1)) {
                logger.info("账号或密码错误");
                return Constant.Result.FAILED;
            } else if (error.equals(s2)) {
                logger.info("不存在此账号");
                return Constant.Result.FAILED;
            } else {
                logger.info("未知错误");
                return Constant.Result.FAILED;
            }
        } catch (Exception e) {

        }

        try {
            WebElement pictureCheck = driver.findElement(By.className("shopee-authen__form"))
                    .findElements(By.className("input-with-status__input")).get(2);
            logger.info("出现了验证码机制,登录异常");
            return Constant.Result.FAILED;
        } catch (Exception e) {

        }
        return Constant.Result.SUCCESS;
    }

    /**
     * 拆分链接地址 获取站点信息
     *
     * @param shopeeSite
     * @return
     */
    private String site(String shopeeSite) {
        // 对获取的链接地址关于元素“//”进行拆分
        logger.info("处理的初始地址是：" + shopeeSite);
        String[] firstResultList = shopeeSite.split("//");
        // 截取需要第二次拆分的数据内容
        String secondList = firstResultList[firstResultList.length - 1];

        String[] secondElement = secondList.split("/");
        String thirdList = secondElement[0];
        System.out.println(thirdList);
        String[] thirdElement = thirdList.split("\\.");
        String finalyResult = thirdElement[thirdElement.length - 1];
        logger.info("最终的拆分结果是：" + finalyResult);
        finalyResult = finalyResult.trim();
        return finalyResult;

    }

    /**
     * 检查站点
     *
     * @param loginSite
     * @return
     */
    private String checkLoginSite(String loginSite) {
        // 去除首尾段的空格
        String firstElement = loginSite.trim();
        String[] firstResultList = firstElement.split("\\.");
        String result = firstResultList[firstResultList.length - 1];
        result = result.trim();
        logger.info("站点检查出来的是:" + result);
        return result;
    }

    /**
     * @return
     */
    public int countStar() {
        // 获取最热链接页面
        System.out.println("缓存整个页面！！");
        try {
            WebElement target = driver.findElement(By.cssSelector(" div:nth-child(2) > div.footer__block__content"));
            js.executeScript("arguments[0].scrollIntoView();", target);
        } catch (Exception e) {
            logger.info("缓存页面操作出错");
        }

        // 获取要点赞的总数量
        int quantity = 0;
        try {
            quantity = driver.findElementsByCssSelector(
                    "div.shopee-item-card__section-actions > div.shopee-item-card__btn-likes > svg").size();
        } catch (Exception e) {
            logger.info("获取节点信息出错");
        }
        System.out.println("节点的数量：--" + quantity);
        return quantity;
    }

    /**
     * 随机点击页面数量
     * @param numStar
     */
    private void clickToFlag(int numStar) {
        // 生成随机数
        Random rand = new Random();
        // 随机出现时，如果，反复出现3此，相同的，进行翻页标识
        int stateFlag = 0;
        // 点赞次数记录 like
        int like = 0;
        // 翻页存在的标志,默认不存在翻页是1，存在了翻页后为2
        int kl = 1;
        // 记录总的点赞星数
        int j = 0;
        // 老页面的加载后，随机点赞数量
        int oldStar = 0;
        // 加载新页面后，随机点赞星数量
        int newStar = 0;
        // 新页面总的信心数量
        int newCount = 0;
        int quantity1 = 0;
        // 随机遍历6个数，累计重复出现2次，那么就翻页
        for (int m = 0; m <= 600; m++) {
            // 以下是更新 老页面与翻页新页面的总的点赞数量
            if (kl == 1) {
                oldStar = rand.nextInt(quantity1);
               logger.info("传入值总数quantity---" + numStar);
                if (oldStar == 0) {
                    oldStar = oldStar + 1;
                }
                quantity1 = oldStar;
              logger.info("此时的随机值是P1--" + quantity1);
            } else if (kl == 2) {
                newStar = rand.nextInt(newCount);
                logger.info("传入值总数newCount---" + newCount);
                // 由于从上一页过来有6个是可能被点赞的，所以随机数小于6时，实际上要点击地方数量是大于6
                if (newStar < 6) {
                    newStar = newStar + 5;
                }
                quantity1 = newStar;
                logger.info("此时的随机值是Pn==" + quantity1);
            }

            j = quantity1;
            logger.info("此时的随机值是j1==" + j);
            // 获取随机生成的j这个元素标签下的类的内容
            WebElement clickone = null;
            try {
                clickone = driver.findElementsByCssSelector(
                        "div.shopee-item-card__section-actions > div.shopee-item-card__btn-likes > svg").get(j);
                driver.executeScript("arguments[0].scrollIntoView();", clickone);
            } catch (Exception e1) {
                logger.info("查找随机生成的元素类内容出错");
            }
            String contentFlag = clickone.getAttribute("class");
            logger.info("获得的属性值：" + contentFlag);
            MyUtils.sleep();
            // 如果点赞满足了6次，终止此次循环

            // 判断标志是否是需要点赞，是进入点赞
            if (contentFlag.equals("shopee-svg-icon icon-like-2")) {
                try {
                    driver.findElementsByCssSelector(
                            "div.shopee-item-card__section-actions > div.shopee-item-card__btn-likes > svg").get(j).click();
                } catch (Exception e) {
                    logger.info("找不到点赞元素");
                }
                logger.info("第" + j + "个产品，点赞完成！！！");
                like = like + 1;
            } else {
                stateFlag = stateFlag + 1;
                logger.info("第" + j + "已点击完，无需再点击！！！");
            }
            logger.info("like ---" + like);
            if (like == 6) {
                break;
            }
            logger.info("此时的stateFlag值是：" + stateFlag);
            if (stateFlag == 3) {
                // 进行分页按钮
                this.turnPage();
                stateFlag = 0;
                // 标记已经翻页
                kl = 2;

                // 新的页面的总的点赞星数 kl
                newCount = countStar();
                logger.info("newCount的值是：" + newCount);
            }
        }
    }

    /**
     * 跳转分页
     */
    private void turnPage() {
        logger.info("正在翻页！！！");
        try {
            WebElement o = driver.findElement(By.cssSelector(" div:nth-child(2) > div.footer__block__content"));
            driver.executeScript("arguments[0].scrollIntoView();", o);
        } catch (Exception e1) {
            logger.info("找不到翻页元素");
        }
        // 以下是小的分页标志
        WebElement nextPage =null;
        try {
            nextPage = driver
                    .findElementByXPath("(//div/div[@class=\"shopee-mini-page-controller\"]/button)[2]");
        } catch (Exception e1) {
            logger.info("找不到分页标志元素");
        }
        String content = nextPage.getAttribute("class");
        System.out.println("获得的属性值：" + content);

        if (content.indexOf("disabled") > -1) {
            logger.info("已经是最后一页了，即将终止了点赞！！！");

        } else {
            try {
                driver.findElementByXPath("(//div/div[@class=\"shopee-mini-page-controller\"]/button)[2]").click();
            } catch (Exception e1) {
                logger.info("找不到翻页按钮");
            }
            logger.info("产生翻页！！");
            MyUtils.sleep();
        }
    }
}
