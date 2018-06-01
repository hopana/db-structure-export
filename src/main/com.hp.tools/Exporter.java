import freemarker.template.TemplateException;
import model.Table;
import utils.DbUtils;
import utils.FreeMarkerUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

            for (Table table : tables) {
                // TODO: 优化采用并发方案
                // ScheduledExecutorService exportService = Executors.newSingleThreadScheduledExecutor();
                // exportService.scheduleWithFixedDelay(new ExportTaskOfSymbol(key), 0, Integer.parseInt(System.getProperty("timeInterval")), TimeUnit.MINUTES);ForkJoin

                table.setColumns(DbUtils.getInstance().getStructOfTable(table.getTableName()));
            }


            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("database", "spdb");
            dataMap.put("tables", tables);

            FreeMarkerUtils.process("tables.ftl", "D:/db.html", dataMap);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
