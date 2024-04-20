package org.springblade.mng.cgform.model.generate.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class FileUtil
{
    public static List<String> fileStrList = new ArrayList<String>();
    public static List<String> fileStrListFtl = new ArrayList<String>();

    /**
	 * 文件排序
	 * 2020-03-14
	 * @param file
	 * @return
	 * @throws IOException
	 */
    public static List<File> fileArrSort(File file) throws IOException {
        ArrayList<File> list = new ArrayList<File>();
        readAllFiles(file, (List<File>)list);
        Collections.sort(list, (Comparator<? super File>)new Comparator<File>() {
            public int compare(File file, File file2) {
                return file.getAbsolutePath().compareTo(file2.getAbsolutePath());
            }
        });
        return (List<File>)list;
    }

    /**
	 * 读取所给的文件下的所有子文件
	 * 2020-03-14
	 * @param file
	 * @param filesList
	 * @throws IOException
	 */
    public static void readAllFiles(File file, List<File> list) throws IOException {
        log.debug("---------dir------------path: " + file.getPath() + " -- isHidden --: " + file.isHidden() + " -- isDirectory --: " + file.isDirectory());
        if (!file.isHidden() && file.isDirectory() && !isFileStrList(file)) {
            final File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; ++i) {
            	readAllFiles(listFiles[i], list);
            }
        }
        else if (!isFileStrList2(file) && !isFileStrList(file)) {
            list.add(file);
        }
    }

    /**
	 * 文件排序
	 * 2020-03-14
	 * @param var0
	 * @param var1
	 * @return
	 */
    public static String fileArrSort(File file, File file2) {
        if (file.equals(file2)) {
            return "";
        }
        if (file.getParentFile() == null) {
            return file2.getAbsolutePath().substring(file.getAbsolutePath().length());
        }
        return file2.getAbsolutePath().substring(file.getAbsolutePath().length() + 1);
    }

    /**
	 * 判断文件是否为文件夹
	 * 2020-03-14
	 * @param file
	 * @return
	 */
    public static boolean isDirectory(File file) {
        return !file.isDirectory() && isBlank(file.getName());
    }

    /**
	 * 判断字符串是否为空
	 * 2020-03-14
	 * @param s
	 * @return
	 */
    public static boolean isBlank(String s) {
        return !StringUtils.isBlank(subStrDot(s));
    }

    /**
	 * 去掉.之前的字符
	 * 2020-03-14
	 * @param s
	 * @return
	 */
    public static String subStrDot(String s) {
        if (s == null) {
            return null;
        }
        final int index = s.indexOf(".");
        if (index == -1) {
            return "";
        }
        return s.substring(index + 1);
    }

    /**
	 * 读取文件
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
    public static File readFile(String s) {
        if (s == null) {
            throw new IllegalArgumentException("file must be not null");
        }
        File file = new File(s);
        createFiles(file);
        return file;
    }

    /**
	 * 创建文件或者文件夹
	 * @param file
	 */
    public static void createFiles(File file) {
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
    }

    /**
	 * 是否存在过滤的文件组中
	 * @param file
	 * @return
	 */
    private static boolean isFileStrList(File file) {
        for (int i = 0; i < fileStrList.size(); ++i) {
            if (file.getName().equals(fileStrList.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
	 * 是否存在模板文件组中
	 * @param file
	 * @return
	 */
    private static boolean isFileStrList2(File file) {
        for (int i = 0; i < fileStrListFtl.size(); ++i) {
            if (file.getName().endsWith(fileStrListFtl.get(i))) {
                return true;
            }
        }
        return false;
    }

    static {
    	fileStrList.add(".svn");
    	fileStrList.add("CVS");
    	fileStrList.add(".cvsignore");
    	fileStrList.add(".copyarea.db");
    	fileStrList.add("SCCS");
    	fileStrList.add("vssver.scc");
    	fileStrList.add(".DS_Store");
    	fileStrList.add(".git");
    	fileStrList.add(".gitignore");
    	fileStrListFtl.add(".ftl");
    }
}
