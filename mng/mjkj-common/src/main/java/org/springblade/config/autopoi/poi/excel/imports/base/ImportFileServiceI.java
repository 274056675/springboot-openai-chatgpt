package org.springblade.config.autopoi.poi.excel.imports.base;

public interface ImportFileServiceI {

    /**
     * 上传文件 返回文件地址字符串
     * @param data
     * @return
     */
    String doUpload(byte[] data);

}
