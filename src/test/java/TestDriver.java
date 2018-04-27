import jumia.DO.JumiaDO;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Driver;
import utils.Property;
import utils.TxtUtil;
import utils.WebElementUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @program: myselenium
 * @description: 测试
 * @author: lwx
 * @create: 2018-04-25 16:42
 **/

public class TestDriver {
    static  WebDriver driver = Driver.getChromeDriverInstance();
    @Test
    public void testDriver() {

        driver.get("http://www.baidu.com");
        WebElement baidu = null;
        WebElementUtils webElementUtils = new WebElementUtils();
        try {
           // baidu = driver.findElement(webElementUtils.getByElement("baidu"));
            baidu.sendKeys("守望先鋒");
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // baidu = driver.findElement(webElementUtils.getByElement("baidu"));
            baidu.sendKeys("守望先鋒");
        }
        System.out.println(baidu);
        Driver.destory(driver);
    }

    @Test
    public void testRead() {
        String accountPath = Property.getProjectPath() + "/jumia账号密码.txt";
        ArrayList<JumiaDO> account = TxtUtil.readobjectlistTxt(accountPath);
        System.out.println(account);
        String loadUrlPath = Property.getProjectPath() + "/jumiaSku商品信息.txt";
        ArrayList<String> loadUrl = TxtUtil.readpingyuTxt(loadUrlPath);
        System.out.println(loadUrl);
    }

    @Test
    public void testSplit() {
        String str = "ID(LoginForm_email)";
        String value[] = str.split("\\(");
        String values[] = value[1].split("\\)");
        System.out.println(values[0]);
    }

    public static WebElement findtest(By by) {
        try {
            WebElement baidu = driver.findElement(by);
            return baidu;
        } catch (Exception e) {
            return null;
        }
    }
}
