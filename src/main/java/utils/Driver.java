package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: myselenium
 * @description: 驱动工具类
 * @author: lwx
 * @create: 2018-04-25 15:40
 **/

public class Driver {
    private static Logger logger = LoggerFactory.getLogger(Driver.class);
    private static ChromeDriverService service = null;

    //驱动路径
    private static final String DRIVERPATH = "chrome_driver/chromedriver.exe";

    //配置运行驱动
    static {
        try {
            System.setProperty("webdriver.chrome.driver", DRIVERPATH);
            service = new ChromeDriverService.Builder().usingDriverExecutable(new File(DRIVERPATH)).usingAnyFreePort().build();
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成无头驱动实例
     *
     * @param
     * @return
     */
    public static WebDriver getNoHeadChromeDriverInstance() {
        ChromeOptions chromeOptions = new ChromeOptions();
        // 改为 无头模式
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        logger.info("获取无头浏览器实例");
        return driver;
    }

    /**
     * 谷歌浏览器实例
     *
     * @return
     */
    public static WebDriver getChromeDriverInstance() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        logger.info("获取浏览器实例");
        return driver;
    }

    /**
     * 关闭浏览器
     *
     * @param driver
     */
    public static void destory(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
        logger.info("关闭chrome浏览器");
    }
}
