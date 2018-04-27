package utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileUtil {
	/**
	 * 新建文件 
	 * 给定的路径没有文件夹则先新建文件夹 ，再查看是否有该文件，没有则新建
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String path) throws IOException{
		if(path == null || path.length() <= 0){
			return null;
		}
		File folder = new File(path.substring(0, path.lastIndexOf("/")));
		if(!folder.exists()){
			folder.mkdirs();
		}
		File file = new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	public static void createDir(String path){
		if(path == null || path.length() <= 0){
			return ;
		}
		File folder = new File(path);
		if(!folder.exists()){
			folder.mkdirs();
		}
	}
	/**
	 * 给定目录
	 * 冒泡排序找出修改时间最大的两个文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static File[] findExcel(String path,final String suffix) throws Exception{
		File dir = new File(path);
		if(!dir.exists() || !dir.isDirectory()){
			throw new Exception("给定的excel下载路径不存在或者不是文件夹");
		}
		File[] files = dir.listFiles(new FilenameFilter(){
			public boolean accept(File file, String s){
				return s.endsWith(suffix);
			}
		});
		File temp = null;
		for (int i = 0; i < files.length; i++) {
			temp = files[i];
			for (int j = 0; j < files.length; j++) {
				if(files[j].lastModified() < temp.lastModified()){
					files[i] = files[j];
					files[j] = temp;
					temp = files[i];
				}
			}
		}
		return files;
	}
	
	/*public static void main(String[] args) throws Exception{
		String path = "F:/xiekanglin/冯菡炜";
		File[]  files = findExcel(path, ".txt");
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
	}*/
}
