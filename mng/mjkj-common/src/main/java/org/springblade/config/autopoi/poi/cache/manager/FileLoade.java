package org.springblade.config.autopoi.poi.cache.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.config.autopoi.poi.util.PoiPublicUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文件加载类,根据路径加载指定文件
 *
 */
class FileLoade {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileLoade.class);

	public byte[] getFile(String url) {
		FileInputStream fileis = null;
		ByteArrayOutputStream baos = null;
		try {
			// 先用绝对路径查询,再查询相对路径
			try {
				fileis = new FileInputStream(url);
			} catch (FileNotFoundException e) {
				String path = PoiPublicUtil.getWebRootPath(url);
				fileis = new FileInputStream(path);
			}
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileis.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if (fileis != null)
					fileis.close();
				if (fileis != null)
					baos.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		LOGGER.error(fileis + "这个路径文件没有找到,请查询");
		return null;
	}

}
