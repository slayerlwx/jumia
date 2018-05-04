package utils;

import DO.jumia.SeleniumDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * txt读取工具类
 */
public class TxtUtil {
    private static Logger logger = LoggerFactory.getLogger(TxtUtil.class);

    /*
     * 功能：获取txt账号信息  内容进行拆分 分别获取对应的     账号与密码
     */
    public static ArrayList<SeleniumDo> readobjectlistTxt(String filePath) {
        ArrayList<SeleniumDo> list = new ArrayList<SeleniumDo>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                   /* String accountAndPassword[] = lineTxt.split("=");
                    String accountPassword = accountAndPassword[1];*/
                    String[] reds = lineTxt.split("\\|");
                    SeleniumDo seleniumDo = new SeleniumDo();
                    seleniumDo.setAccount(reds[0]);
                    seleniumDo.setPassword(reds[1]);
                    try {
                        seleniumDo.setNickName(reds[2]);
                    } catch (Exception e) {
                        logger.error("此文件里面没有nikeName");
                    }
                    list.add(seleniumDo);
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误1!");
        }

        return list;

    }

    public static ArrayList<String> readlistTxt(String filePath) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    list.add(lineTxt);
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误1111!");
        }
        return list;

    }


    public static ArrayList<String> readpingyuTxt(String filePath) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    list.add(lineTxt);
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
        return list;

    }

}
