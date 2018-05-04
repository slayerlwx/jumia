package task.jumia;

import java.util.List;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.jumia.JumiaReceiveService;
import utils.Driver;
import utils.WebElementUtils;

/**
 * jumia 评论 任务类
 * @author 125C01063154
 *
 */
public class JumiaReceiveTask  implements Runnable{
	private static Logger logger =LoggerFactory.getLogger(JumiaReceiveTask.class);
	 /**
     * 账号和密码
     */
    private List<SeleniumDo> accounts;
    /**
     * sku信息 ,评语
     */
    private List<String> productInfo,receive;
    /**
     * @param accounts
     * @param productInfo
     * @param receive
     */
    public JumiaReceiveTask(List<SeleniumDo> accounts, List<String> productInfo, List<String> receive) {
        this.accounts = accounts;
        this.productInfo = productInfo;
        this.receive=receive;
    }
	@Override
	public void run() {
		WebElementUtils webElementUtils =new WebElementUtils();
		//获取无头浏览器
		JumiaReceiveService jumiaReceiveService =new JumiaReceiveService(Driver.getNoHeadChromeDriverInstance(),webElementUtils);
		//获取谷歌浏览器
		//JumiaReceiveService jumiaReceiveService =new JumiaReceiveService(Driver.getChromeDriverInstance(), webElementUtils);
		jumiaReceiveService.runJumiaReceiveService(accounts, productInfo,receive);
	}

}
