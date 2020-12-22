package org.springyoung.file.utils;

import java.io.File;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author 小温
 * @Date 2020/12/9 11:47
 * @Version 1.0
 */
public class FileUtil {

	public static void initPath(File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static String findFileNameByPath(String path) {
		File tempFile = new File(path.trim());
		return tempFile.getName();
	}

	public static String addTail(String s) {
		if (!s.endsWith("/"))
			s = s + "/";
		return s;
	}

	public static String addHead(String s) {
		if (!s.startsWith("/"))
			s = "/" + s;
		return s;
	}

}
