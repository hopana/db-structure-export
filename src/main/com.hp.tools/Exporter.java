package com.hp.tools;

import com.hp.tools.model.Table;
import com.hp.tools.task.TaskExecutor;
import com.hp.tools.utils.DbUtils;
import com.hp.tools.utils.FreeMarkerUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库结果导出
 *
 * @author hupan
 * @since 2018-06-01 10:21
 */
public class Exporter {
    public static void main(String[] args) {
        try {
            List<Table> tables = Collections.synchronizedList(DbUtils.getInstance().getAllTables());

//            for (Table table : tables) {
//                table.setColumns(DbUtils.getInstance().getStructOfTable(table.getTableName()));
//            }

            new TaskExecutor().invokeTask(tables.subList(0, 10));

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("database", "spdb");
            dataMap.put("tables", tables);

            FreeMarkerUtils.process("tables.ftl", "D:/db.html", dataMap);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}
