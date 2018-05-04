package constant;

/**
 * @program: myselenium
 * @description: 常量
 * @author: lwx
 * @create: 2018-04-26 10:54
 **/

public interface Constant {
    /**
     * BY类的方法名
     * By.xpath(),By.className(),By.cssSelector(),By.id(),By.linkText(),By.name(),By.partialLinkText(),By.tagName()
     */
    interface ByTpye {
        /**
         * By.id()方法识别
         */
        String ID = "ID";
        /**
         * By.className() 方法识别
         */
        String CLASS_NAME = "CLASS";
        /**
         * By.tagName()
         */
        String TAG_NAME = "TAG";
        /**
         * By.cssSelector()
         */
        String CSS_SELECTOR = "CS";
        /**
         * By.linkText()
         */
        String LINK_TEXT = "LINK";
        /**
         * By.name()
         */
        String NAME = "NAME";
        /**
         * By.partialLinkText()
         */
        String PARTIAL_LINK_TEXT = "PLT";
        /**
         * By.xpath()
         */
        String XPATH = "XPATH";

    }

    interface Result {
        String SUCCESS = "登录成功";
        String FAILED = "登录异常";
    }
    interface ReceiveResult{
        String RECEIVE_SUCCESS="评价成功";
        String RECEIVE_FAILED="评价失败";
    }
}
