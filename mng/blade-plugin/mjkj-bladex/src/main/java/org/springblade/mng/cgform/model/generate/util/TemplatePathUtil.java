package org.springblade.mng.cgform.model.generate.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Slf4j
public class TemplatePathUtil
{
    public static Configuration templateConfig(List<File> list, String defaultEncoding, String s) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        log.debug(" FileTemplateLoader[] size " + list.size());
        log.debug(" templateRootDirs templateName " + s);
        FileTemplateLoader[] array = new FileTemplateLoader[list.size()];

        for (int i = 0; i < list.size(); ++i) {
            File file = list.get(i);
            log.debug(" FileTemplateLoader " + file.getAbsolutePath());
            array[i] = new FileTemplateLoader(file);
        }
        configuration.setTemplateLoader((TemplateLoader)new MultiTemplateLoader((TemplateLoader[])array));
        configuration.setNumberFormat("###############");
        configuration.setBooleanFormat("true,false");
        configuration.setDefaultEncoding(defaultEncoding);
        return configuration;
    }

    public static List<String> a(String s, String s2) {
        String[] b = b(s, "\\/");
        ArrayList<String> list = new ArrayList<String>();
        list.add(s2);
        list.add(File.separator + s2);
        String string = "";
        for (int i = 0; i < b.length; ++i) {
            string = string + File.separator + b[i];
            list.add(string + File.separator + s2);
        }
        return list;
    }

    public static String[] b(String s, String s2) {
        if (s == null) {
            return new String[0];
        }
        StringTokenizer stringTokenizer = new StringTokenizer(s, s2);
        ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreElements()) {
            list.add(stringTokenizer.nextElement().toString());
        }
        return list.toArray(new String[list.size()]);
    }

    public static String a(String s, Map<String, Object> map, Configuration configuration) {
        StringWriter stringWriter = new StringWriter();
        try {
            new Template("templateString...", (Reader)new StringReader(s), configuration).process((Object)map, (Writer)stringWriter);
            return stringWriter.toString();
        }
        catch (Exception ex) {
            throw new IllegalStateException("cannot process templateString:" + s + " cause:" + ex, ex);
        }
    }

    public static void templateDraw(Template template, Map<String, Object> map, File file, String s) throws IOException, TemplateException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), s));
        map.put("Format", new SimpleFormat());
        template.process((Object)map, (Writer)bufferedWriter);
        bufferedWriter.close();
    }

}
