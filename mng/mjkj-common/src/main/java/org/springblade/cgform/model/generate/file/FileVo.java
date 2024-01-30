package org.springblade.cgform.model.generate.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileVo
{
    private String templatePath;
    private List<File> templateRootDirs;
    private String stylePath;

    public FileVo(String templatePath) {
        this.templateRootDirs = new ArrayList<File>();
        log.debug("----templatePath-----------------" + templatePath);
        log.debug("----stylePath-----------------" + this.stylePath);
        this.templatePath = templatePath;
    }

    private void setTemplateRootDirs(File file) {
        this.setTemplateRootDirs(new File[] { file });
    }

    private void setTemplateRootDirs(File... array) {
        this.templateRootDirs = Arrays.asList(array);
    }

    public String getStylePath() {
        return this.stylePath;
    }

    public void setStylePath(String stylePath) {
        this.stylePath = stylePath;
    }

    public List<File> getTemplateRootDirs() {
        String classpath = this.getClass().getResource(this.templatePath).getFile().replaceAll("%20", " ");
        log.debug("-------classpath-------" + classpath);
        if (classpath.indexOf("/BOOT-INF/classes!") != -1) {
        	classpath = System.getProperty("user.dir") + File.separator + "config/mjkj/code-template-online/".replace("/", File.separator);
            log.debug("---JAR--config--classpath-------" + classpath);
        }
        this.setTemplateRootDirs(new File(classpath));
        return this.templateRootDirs;
    }

    public void setTemplateRootDirs(List<File> templateRootDirs) {
        this.templateRootDirs = templateRootDirs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"templateRootDirs\":\"");
        sb.append(this.templateRootDirs);
        sb.append("\",\"stylePath\":\"");
        sb.append(this.stylePath);
        sb.append("\"} ");
        return sb.toString();
    }
}
