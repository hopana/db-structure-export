package task;

import model.Table;
import utils.DbUtils;

import java.util.concurrent.RecursiveTask;

/**
 * 导出任务类
 *
 * @author hupan
 * @since 2018-06-01 17:28
 */
public class ExportTask extends RecursiveTask<Void> {
    private Table table;

    public ExportTask(Table table) {
        this.table = table;
    }

    @Override
    protected Void compute() {
        table.setColumns(DbUtils.getInstance().getStructOfTable(table.getTableName()));

        return null;
    }
}