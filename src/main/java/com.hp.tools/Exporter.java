package com.hp.tools;

import com.hp.tools.model.Table;
import com.hp.tools.task.TaskExecutor;
import com.hp.tools.utils.DbUtils;
import com.hp.tools.utils.FreeMarkerUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 数据库结果导出
 *
 * @author hupan
 * @since 2018-06-01 10:21
 */
public class Exporter {
    public static void main(String[] args) {
        try {
            List<Table> tables = Collections.synchronizedList(DbUtils.getInstance(DbUtils.Type.MYSQL).getAllTables());

//            for (Table table : tables) {
//                table.setColumns(DbUtils.getInstance().getStructOfTable(table.getTableName()));
//            }

            new TaskExecutor().invokeTask(tables);

            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = Exporter.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 使用properties对象加载输入流
            properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            String db = properties.getProperty("mysql.db");

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("database", db);
            dataMap.put("tables", tables);

            FreeMarkerUtils.process("tables.ftl", "/Users/MZ/Documents/"+db+".html", dataMap);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}
