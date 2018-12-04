package com.hp.tools.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

/**
 * FreeMarker文件生成工具类
 *
 * @author hupan
 * @since 2017-03-20 17:00
 */
public class FreeMarkerUtils {
    private final static String TEMPLATE_PATH = "templates";

    /**
     * 获取Template对象
     *
     * @param templateName 模版名称（不带路径）
     * @return Template对象
     * @throws IOException 抛出IOException
     */
    public static Template getTemplate(String templateName) throws IOException {
        Configuration conf = new Configuration(Configuration.VERSION_2_3_23);
        conf.setDefaultEncoding("UTF-8");
        URL url = FreeMarkerUtils.class.getClassLoader().getResource(TEMPLATE_PATH + File.separator + templateName);
        if (url == null) {
            throw new IOException("模板文件不存在");
        }

        conf.setDirectoryForTemplateLoading(new File(url.getPath()).getParentFile());

        return conf.getTemplate(templateName);
    }

    public static void process(String templateName, String fileName, Map<String, Object> dataModel) throws IOException, TemplateException {
        Template template = getTemplate(templateName);

        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        template.process(dataModel, writer);
        writer.flush();
        writer.close();
    }

}
