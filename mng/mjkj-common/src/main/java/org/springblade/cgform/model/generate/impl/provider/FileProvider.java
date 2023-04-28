package org.springblade.cgform.model.generate.impl.provider;

import org.springblade.config.db.DbConfig;
import org.springblade.cgform.model.generate.file.FileVo;
import org.springblade.cgform.model.generate.util.FileUtil;
import org.springblade.cgform.model.generate.util.TemplatePathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class FileProvider {
	protected static String defaultEncoding = "UTF-8";
	protected List<String> msg = new ArrayList<>();

	/**
	 * 读取模板文件并根据模板生成代码
	 * 2020-03-14
	 * @param rootfileObj  模板主路径对象
	 * @param projectPath 项目路径
	 * @param dataMap 数据
	 * @throws Exception
	 */
	protected void generateCodeFile(FileVo rootfileObj, String projectPath, Map<String, Object> dataMap) throws Exception {
		log.debug("--------generate----projectPath--------" + projectPath);
		for (int i = 0; i < rootfileObj.getTemplateRootDirs().size(); i++) {
			File file = (File) rootfileObj.getTemplateRootDirs().get(i);
			loadTemplate(projectPath, file, dataMap, rootfileObj);
		}
	}

	/**
	 * 读取文件
	 * 2020-03-14
	 * @param projectPath 项目路径
	 * @param firstfile 根路径下的第一级文件
	 * @param dataMap 数据
	 * @param rootfileObj 根路径对象
	 * @throws Exception
	 */
	protected void loadTemplate(String projectPath, File firstfile, Map<String, Object> dataMap,
			FileVo rootfileObj) throws Exception {
		if (firstfile == null) {
			throw new IllegalStateException("'templateRootDir' must be not null");
		}
		log.info("  load template from templateRootDir = '" + firstfile.getAbsolutePath() + "',stylePath ='" + rootfileObj.getStylePath()
				+ "',  out GenerateRootDir:" + DbConfig.projectPath);

		List<File> localList = FileUtil.fileArrSort(firstfile);
		log.debug("----srcFiles----size-----------" + localList.size());
		log.debug("----srcFiles----list------------" + localList.toString());
		for (int i = 0; i < localList.size(); i++) {
			File localFile = (File) localList.get(i);
			templateDraw(projectPath, firstfile, dataMap, localFile, rootfileObj);
		}
	}

	/**
	 * 替换模板生成代码
	 * 2020-03-15
	 * @param projectPath 项目路径
	 * @param firstfile 根路径下的第一级文件
	 * @param dataMap 数据
	 * @param secondFile 根路径下的第二级文件
	 * @param rootfileObj 根路径对象
	 * @throws Exception
	 */
	protected void templateDraw(String projectPath, File firstfile, Map<String, Object> dataMap, File secondFile,
			FileVo rootfileObj) throws Exception {
		log.debug("-------templateRootDir--" + firstfile.getPath());
		log.debug("-------srcFile--" + secondFile.getPath());

		String templateFile = FileUtil.fileArrSort(firstfile, secondFile);
		try {
			log.debug("-------templateFile--" + templateFile);
			if ((rootfileObj.getStylePath() != null) && (!"".equals(rootfileObj.getStylePath()))
					&& (!templateFile.replace(File.separator, ".").startsWith(rootfileObj.getStylePath()))) {
				return;
			}
			String outputFilepath = handleFileObj(dataMap, templateFile, rootfileObj);
			log.debug("-------outputFilepath--" + outputFilepath);
			String str3;
			String str4;
			if (outputFilepath.startsWith("java")) {
				str3 = projectPath + File.separator
						+ DbConfig.sourceRoot.replace(".", File.separator);

				str4 = str3;
				outputFilepath = outputFilepath.substring("java".length());
				outputFilepath = str4 + outputFilepath;
				log.debug("-------java----outputFilepath--" + outputFilepath);
				templateDraw(templateFile, outputFilepath, dataMap, rootfileObj);
			} else if (outputFilepath.startsWith("webapp")) {
				str3 = projectPath + File.separator
						+ DbConfig.webRoot.replace(".", File.separator);

				str4 = str3;
				outputFilepath = outputFilepath.substring("webapp".length());
				outputFilepath = str4 + outputFilepath;
				log.debug("-------webapp---outputFilepath---" + outputFilepath);
				templateDraw(templateFile, outputFilepath, dataMap, rootfileObj);
			}
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
	}

	/**
	 * 根据模板替换对应的值
	 * 2020-03-14
	 * @param templatePath
	 * @param writePath
	 * @param dataMap 数据
	 * @param FileObj
	 * @throws Exception
	 */
	protected void templateDraw(String templatePath, String writePath, Map<String, Object> dataMap,
			FileVo FileObj) throws Exception {
		if (writePath.endsWith("i")) {
			writePath = writePath.substring(0, writePath.length() - 1);
		}
		Template localTemplate = templateConfig(templatePath, FileObj);
		localTemplate.setOutputEncoding(defaultEncoding);
		File localFile = FileUtil.readFile(writePath);
		log.info("[generate]\t template:" + templatePath + " ==> " + writePath);
		TemplatePathUtil.templateDraw(localTemplate, dataMap, localFile, defaultEncoding);
		if (!isOneVn(localFile)) {
			msg.add("生成成功：" + writePath);
		}
		if (isOneVn(localFile)) {
			writeOneVn(localFile, "#segment#");
		}
	}

	protected Template templateConfig(String paramString, FileVo parama) throws IOException {
		return TemplatePathUtil.templateConfig(parama.getTemplateRootDirs(), defaultEncoding, paramString).getTemplate(paramString);
	}

	/**
	 * 判断是否为一对多类型
	 * 2020-03-14
	 * @param paramFile
	 * @return
	 */
	protected boolean isOneVn(File paramFile) {
		if (paramFile.getName().startsWith("[1-n]")) {
			return true;
		}
		return false;
	}

	/**
	 * 处理一对多类型
	 * 2020-03-14
	 * @param writefile
	 */
	protected void writeOneVn(File writefile, String paramString) {
		InputStreamReader localInputStreamReader = null;
		BufferedReader localBufferedReader = null;
		List<OutputStreamWriter> localArrayList = new ArrayList<>();
		try {
			localInputStreamReader = new InputStreamReader(new FileInputStream(writefile), "UTF-8");
			localBufferedReader = new BufferedReader(localInputStreamReader);

			int m = 0;
			OutputStreamWriter localOutputStreamWriter = null;
			String str1;
			while ((str1 = localBufferedReader.readLine()) != null) {
				if ((str1.trim().length() > 0) && (str1.startsWith(paramString))) {
					String str2 = str1.substring(paramString.length());
					String str3 = writefile.getParentFile().getAbsolutePath();
					str2 = str3 + File.separator + str2;
					log.info("[generate]\t split file:" + writefile.getAbsolutePath() + " ==> " + str2);

					localOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(str2), "UTF-8");
					localArrayList.add(localOutputStreamWriter);
					msg.add("生成成功：" + str2);
					m = 1;
				} else if (m != 0) {
					localOutputStreamWriter.append(str1 + "\r\n");
				}
			}
			for (int n = 0; n < localArrayList.size(); n++) {
				((Writer) localArrayList.get(n)).close();
			}
			localBufferedReader.close();
			localInputStreamReader.close();

			log.debug("[generate]\t delete file:" + writefile.getAbsolutePath());
			deleteFile(writefile);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (localBufferedReader != null) {
					localBufferedReader.close();
				}
				if (localInputStreamReader != null) {
					localInputStreamReader.close();
				}
				if (localArrayList.size() > 0) {
					for (int i = 0; i < localArrayList.size(); i++) {
						if (localArrayList.get(i) != null) {
							((Writer) localArrayList.get(i)).close();
						}
					}
				}
			} catch (IOException localIOException5) {
				localIOException5.printStackTrace();
			}
		}
	}

	/**
	 * 处理路径问题
	 * @param dataMap
	 * @param templatePath
	 * @param rootfileObj
	 * @return
	 * @throws Exception
	 */
	protected static String handleFileObj(Map<String, Object> dataMap, String templatePath,
			FileVo rootfileObj) throws Exception {
		String str1 = templatePath;

		int i = -1;
		if ((i = templatePath.indexOf('@')) != -1) {
			str1 = templatePath.substring(0, i);
			String localObject1 = templatePath.substring(i + 1);
			Object localObject2 = dataMap.get(localObject1);
			if (localObject2 == null) {
				System.err.println("[not-generate] WARN: test expression is null by key:[" + (String) localObject1
						+ "] on template:[" + templatePath + "]");
				return null;
			}
			if (!"true".equals(String.valueOf(localObject2))) {
				log.error("[not-generate]\t test expression '@" + (String) localObject1 + "' is false,template:"
						+ templatePath);
				return null;
			}
		}
		Object localObject1 = TemplatePathUtil.templateConfig(rootfileObj.getTemplateRootDirs(), defaultEncoding, "/");
		str1 = TemplatePathUtil.a(str1, dataMap, (Configuration) localObject1);

		Object localObject2 = rootfileObj.getStylePath();
		if ((localObject2 != null) && (localObject2 != "")) {
			str1 = str1.substring(((String) localObject2).length() + 1);
		}
		String str2 = str1.substring(str1.lastIndexOf("."));
		String str3 = str1.substring(0, str1.lastIndexOf(".")).replace(".", File.separator);
		str1 = str3 + str2;
		return str1;
	}

	/**
	 * 删除文件
	 * @param paramFile
	 * @return
	 */
	protected static boolean deleteFile(File paramFile) {
		boolean bool = false;
		int i = 0;
		while ((!bool) && (i++ < 10)) {
			System.gc();
			bool = paramFile.delete();
		}
		return bool;
	}

	/**
	 * 去掉第一个字符串中开头和结尾包含的字符
	 * 2020-03-15
	 * @param str1
	 * @param str2
	 * @return
	 */
	protected static String strSubStartEnd(String str1, String str2) {
		boolean flag = true;
		boolean flag1 = true;

		do {
			int index = str1.indexOf(str2) == 0 ? 1 : 0;
			int index2 = str1.lastIndexOf(str2) + 1 == str1.length() ? str1.lastIndexOf(str2) : str1.length();
			str1 = str1.substring(index, index2);
			flag = str1.indexOf(str2) == 0;
			flag1 = str1.lastIndexOf(str2) + 1 == str1.length();
		} while (flag || flag1);

		return str1;
	}
}
