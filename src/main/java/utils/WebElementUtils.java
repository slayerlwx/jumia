package utils;

import constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @program: myselenium
 * @description: webElement工厂类
 * @author: lwx
 * @create: 2018-04-26 09:07
 **/
public class WebElementUtils {
    private static Logger logger = LoggerFactory.getLogger(WebElementUtils.class);

    /**
     * 读取配置文件
     *
     * @param driver
     */
    private Properties property = null;
    /**
     * 定义输入流
     *
     * @param driver
     */
    private InputStream is;
    /**
     * 抓元素的类型
     */
    private By by;
    /**
     * 获取抓到的元素
     */
    /**
     * 接收 by 类型多个参数
     */
    private By[] bys;

    private WebElement webElement;
    /**
     * 尝试次数
     */
    private int count;
    /**
     * 以括号进行分割,括号前面的内容来判断调用哪种方法,括号里面的为传入的值
     * By.xpath(),By.className(),By.cssSelector(),By.id(),By.linkText(),By.name(),By.partialLinkText(),By.tagName()
     *
     * @param propertyKey
     * @return
     */
    public WebElement findByElement(WebDriver driver, String propertyKey) {
        WebElementUtils webElementUtils = new WebElementUtils();
        String getElement = null;
        String getForElement = null;
        //思路 先把所有的元素存进list, 然后再取出其中的元素判断 是那种类型的   例子 ID(LoginForm_email)&CLASS(LoginForm_password)
        property = Property.getProperties(is);
        String propertyValue = property.getProperty(propertyKey);
        if (!propertyValue.isEmpty()) {
            if (propertyValue.contains(",")) {
                // elemet 为解析出来的 元素和类型
                String[] element = propertyValue.split(",");
                for (int i = 0; i < element.length; i++) {
                    //获取到的value 例子 ID(LoginForm_email)  需要对value进行处理 以括号分割

                    getElement = element[i];
                   logger.info("解析成功,成功获取key:" + propertyKey + ",对应的值:" + getElement);
                    by = judgeType(getElement);

                    webElement = webElementUtils.driverfindElement(by, driver);
                    if (webElement != null) {
                        return webElement;
                    }
                }
            }
            //判断是否是以-为分隔符的, 如果是这种那就是需要循环调用的
            /*    else if (propertyValue.contains("#")) {
                    // forelemet 为解析出来的 元素和类型
                    String[] forelement = propertyValue.split("#");
                    for (int i = 0; i < forelement.length; i++) {
                        //获取到的value 例子 ID(LoginForm_email)  需要对value进行处理 以括号分割
                        getForElement = forelement[i];
                        logger.info("解析成功,成功获取key:" + propertyKey + ",对应的值:" + getForElement);

                        by = judgeType(getForElement);
                        bys[i] = by;

                    }
                    //调用循环方法
                    webElement = webElementUtils.forDriverfindElement(driver, bys);
                    if (webElement != null) {
                        return webElement;
                    }
            }*/ else {
                //因为只有一个参数 直接解析即可
                logger.info("解析成功,成功获取key:" + propertyKey + ",对应的值:" + propertyValue);
                by = judgeType(propertyValue);
                webElement = webElementUtils.driverfindElement(by, driver);
                if (webElement != null) {
                    return webElement;
                }
            }
        }
        logger.error("找不到key:" + propertyKey + "对应的值为空");
        return null;
    }

    /**
     * 执行复元素
     *
     * @param driver
     * @param propertyKey
     * @param number
     * @return
     */
    public WebElement findByElements(WebDriver driver, String propertyKey, int number) {
        WebElementUtils webElementUtils = new WebElementUtils();
        String getElement = null;
        //思路 先把所有的元素存进list, 然后再取出其中的元素判断 是那种类型的   例子 ID(LoginForm_email)&CLASS(LoginForm_password)
        property = Property.getProperties(is);
        String propertyValue = property.getProperty(propertyKey);
        if (!propertyValue.isEmpty()) {
            if (propertyValue.contains(",")) {
                // elemet 为解析出来的 元素和类型
                String[] element = propertyValue.split(",");
                for (int i = 0; i < element.length; i++) {
                    //获取到的value 例子 ID(LoginForm_email)  需要对value进行处理 以括号分割

                    getElement = element[i];
                    logger.info("解析成功,成功获取key:" + propertyKey + ",对应的值:" + getElement);
                    by = judgeType(getElement);
                    webElement = webElementUtils.driverfindElements(by, driver, number);
                    if (webElement != null) {
                        return webElement;
                    }
                }
            } else {
                //因为只有一个参数 直接解析即可
                logger.info("解析成功,成功获取key:" + propertyKey + ",对应的值:" + propertyValue);
                by = judgeType(propertyValue);
                webElement = webElementUtils.driverfindElements(by, driver, number);
                if (webElement != null) {
                    return webElement;
                }
            }
        }
        logger.error("找不到key:" + propertyKey + "对应的值为空");
        return null;
    }

    /**
     * 执行单元素定位
     *
     * @param by
     * @param driver
     * @return
     */
    public WebElement driverfindElement(By by, WebDriver driver) {
        try {
            webElement = driver.findElement(by);
            return webElement;
        } catch (Exception e) {
            logger.info("找不到" + by.toString() + "的元素,进行第" + (++count) + "次尝试");
            return null;
        }
    }

    /**
     * 执行复合元素定位
     *
     * @param by
     * @param driver
     * @return
     */
    public WebElement driverfindElements(By by, WebDriver driver, int number) {
        try {
            webElement = driver.findElements(by).get(number);
            return webElement;
        } catch (Exception e) {
            logger.info("找不到" + by.toString() + "的元素,进行第" + (++count) + "次尝试");
            return null;
        }
    }

    /**
     * 针对多次循环调用findbyelement方法
     *
     * @param driver
     * @param bys
     * @return
     */
    public WebElement forDriverfindElement(WebDriver driver, By... bys) {
        WebElement forWebElement = null;
        for (By by : bys) {
            try {
                forWebElement = driver.findElement(by);
            } catch (Exception e) {
                logger.info("找不到" + by.toString() + "的元素,进行第" + (++count) + "次尝试");
                return null;
            }
        }
        return forWebElement;
    }

    /**
     * 匹配By类型
     *
     * @param getelement
     * @return
     */
    public By judgeType(String getelement) {
        //切割 ID(LoginForm_email)
        String elementValue[] = getelement.split("\\(");
        //获取头部类型 ID
        String elementHead = elementValue[0];
        //去掉尾部的)
        String elementFoot[] = elementValue[1].split("\\)");
        //获取值
        String value = elementFoot[0];

        //若是以 ID 开头,则是id属性
        if (elementHead.equals(Constant.ByTpye.ID)) {
            by = By.id(value);
        }
        //若是以 CLASS 开头 则是class 属性
        else if (elementHead.equals(Constant.ByTpye.CLASS_NAME)) {
            by = By.className(value);
        }
        //若是以 CS 开头 ,则是cssSekector()属性
        else if (elementHead.equals(Constant.ByTpye.CSS_SELECTOR)) {
            by = By.cssSelector(value);
        }
        //若是以 XPATH 开头,则是Xpath
        else if (elementHead.equals(Constant.ByTpye.XPATH)) {
            by = By.xpath(value);
        }
        //若是以 TAG,则是tagName
        else if (elementHead.equals(Constant.ByTpye.TAG_NAME)) {
            by = By.tagName(value);
        }
        //若是以 NAME 开头,则是name属性
        else if (elementHead.equals(Constant.ByTpye.NAME)) {
            by = By.name(value);
        }
        //若是以 LINK 开头,则是linkText属性
        else if (elementHead.equals(Constant.ByTpye.LINK_TEXT)) {
            return By.linkText(value);
        }
        //若是以 PLT 开头,则是partialLinkText
        else if (elementHead.equals(Constant.ByTpye.PARTIAL_LINK_TEXT)) {
            by = By.partialLinkText(value);
        }
        return by;
    }
}

