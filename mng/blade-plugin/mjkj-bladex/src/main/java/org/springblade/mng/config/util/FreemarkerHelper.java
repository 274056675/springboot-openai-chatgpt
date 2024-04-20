package org.springblade.mng.config.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.util.Map;

/**
 * Freemarker助手
 */
@Slf4j
public class FreemarkerHelper {
    private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);;

    public static String process(String templatePath, String encode, Map<String, Object> data) {
        try {
            StringWriter write = new StringWriter();
            Template template = null;
            template = configuration.getTemplate(templatePath, encode);
            template.process(data, write);
            return write.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return e.toString();
        }
    }

    public static String process(String templatePath, Map<String, Object> data) {
        return process(templatePath, "utf-8", data);
    }

/*    public static void main(String[] args) {
        String var1 = process("org/springblade/cgform/config/engine/tableTemplate.ftl", null);
        System.out.println(var1);
    }*/

    static {
    	configuration.setNumberFormat("0.#####################");
    	configuration.setClassForTemplateLoading(FreemarkerHelper.class, "/");
    }
}
