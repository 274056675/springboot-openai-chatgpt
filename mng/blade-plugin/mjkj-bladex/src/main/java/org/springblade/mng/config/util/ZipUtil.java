package org.springblade.mng.config.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {



    public static File compressFiles(List<String> files, String var1) throws RuntimeException {
        File var2 = FileUtil.touch(var1);
        if (var2 == null) {
            return null;
        } else if (!var2.getName().endsWith(".zip")) {
            return null;
        } else {
            ZipOutputStream var3 = null;

            try {
                FileOutputStream var4 = new FileOutputStream(var2);
                var3 = new ZipOutputStream(var4);
                Iterator<String> var5 = files.iterator();

                while(true) {
                    File var7;
                    do {
                        do {
                            if (!var5.hasNext()) {
                                if (var3 != null) {
                                    try {
                                        var3.close();
                                    } catch (IOException var12) {
                                        System.out.println("ZipUtil toZip close exception" + var12);
                                    }
                                }

                                var4.close();
                                return var2;
                            }

                            String var6 = (String)var5.next();
                            var6 = URLDecoder.decode(var6, "UTF-8").replace("生成成功：", "");
                            var7 = new File(var6);
                        } while(var7 == null);
                    } while(!var7.exists());

                    byte[] var8 = new byte[4096];
                    String var9 = null;
                    if (var7.getAbsolutePath().indexOf("src\\") != -1) {
                        var9 = var7.getAbsolutePath().substring(var7.getAbsolutePath().indexOf("src\\") - 1);
                    } else {
                        var9 = var7.getAbsolutePath().substring(var7.getAbsolutePath().indexOf("src/") - 1);
                    }

                    var3.putNextEntry(new ZipEntry(var9));
                    FileInputStream var11 = new FileInputStream(var7);

                    int var10;
                    while((var10 = var11.read(var8)) != -1) {
                        var3.write(var8, 0, var10);
                    }

                    var11.close();
                    var3.closeEntry();
                }
            } catch (Exception var13) {
                throw new RuntimeException("zipFile error from ZipUtils", var13);
            }
        }
    }
}
