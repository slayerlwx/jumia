package utils;

import jumia.DO.JumiaDO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * @program: myselenium
 * @description: 测试
 * @author: lwx
 * @create: 2018-04-25 16:42
 **/

public class TestDriver {
    static WebDriver driver = Driver.getChromeDriverInstance();

    public static void main(String[] args) {
        TestDriver testDriver = new TestDriver();
        testDriver.testDriver();
    }

    public void testDriver() {

        driver.get("http://www.baidu.com");
        WebElement baidu = null;
        WebElementUtils webElementUtils = new WebElementUtils();
        baidu = webElementUtils.findByElement(driver, "baidu");
        baidu.sendKeys("守望先鋒");
        System.out.println(baidu);
        Driver.destory(driver);
    }


    public void testRead() {
        String accountPath = Property.getProjectPath() + "/jumia账号密码.txt";
        ArrayList<JumiaDO> account = TxtUtil.readobjectlistTxt(accountPath);
        System.out.println(account);
        String loadUrlPath = Property.getProjectPath() + "/jumiaSku商品信息.txt";
        ArrayList<String> loadUrl = TxtUtil.readpingyuTxt(loadUrlPath);
        System.out.println(loadUrl);
    }


    public void testSplit() {
        String str = "ID(LoginForm_email)";
        String value[] = str.split("\\(");
        String values[] = value[1].split("\\)");
        System.out.println(values[0]);
    }


}
