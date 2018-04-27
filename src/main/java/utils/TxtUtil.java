package utils;

import jumia.DO.JumiaDO;

import java.io.*;

import java.io.File;
import java.util.ArrayList;

/**
 * txt读取工具类
 */
public class TxtUtil {

    public static void main(String[] args) {

        String filePath = "F:\\PHP\\评语.txt";

    }


    /*
     * 功能：获取txt账号信息  内容进行拆分 分别获取对应的     账号与密码
     */
    public static ArrayList<JumiaDO> readobjectlistTxt(String filePath) {
        ArrayList<JumiaDO> list = new ArrayList<JumiaDO>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    String accountAndPassword[] = lineTxt.split("=");
                    String accountPassword = accountAndPassword[1];
                    String[] reds = accountPassword.split("\\|");
                    JumiaDO jumiaDO = new JumiaDO();
                    jumiaDO.setAccount(reds[0]);
                    jumiaDO.setPassword(reds[1]);
                    list.add(jumiaDO);
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
