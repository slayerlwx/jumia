package view.jumia;



import java.awt.EventQueue;
import java.util.List;
import java.util.concurrent.ExecutorService;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.jumia.JumiaReceiveTask;
import utils.Property;
import utils.TxtUtil;
/**
 * jumnia 刷评价
 * @author 125C01063154
 *
 */
public class JumiaReceive {
	private static  Logger logger =LoggerFactory.getLogger(JumiaReceive.class);
	/**
	 * task任务类
	 */
	private static JumiaReceiveTask jumiaTask;
	/**
	 * 线程类
	 */
	private ExecutorService fixedThreadPool = null;

	public static void main(String[] args) {
		try {
			//加载文件
			String accountPath = Property.getProjectPath() + "/file/jumia账号密码.txt";
			List<SeleniumDo> accounts = TxtUtil.readobjectlistTxt(accountPath);
			logger.info("jumia账号密码地址:" + accountPath + "---总数：" + accounts.size());
			String commentPath = Property.getProjectPath() + "/file/jumiaSku地址.txt";
			List<String> productInfo = TxtUtil.readpingyuTxt(commentPath);
			logger.info("jumiaSku商品信息地址:" + commentPath + "---总数：" + productInfo.size());
			String jumiaReceivePath =Property.getProjectPath() + "/file/jumia评论.txt";
			List<String>  jumiaReceiveString =TxtUtil.readpingyuTxt(jumiaReceivePath);
			logger.info("jumia评语地址:" + jumiaReceivePath +"----总数:" + jumiaReceiveString.size());

			//启动任务线程类
			jumiaTask = new JumiaReceiveTask(accounts, productInfo,jumiaReceiveString);
			jumiaTask.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	
