package task;

import model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * 具体的顶级任务执行器
 *
 * @author hupan
 * @since 2018-06-01 17:30
 * 参照：https://blog.csdn.net/u013262051/article/details/51541627
 */
public class TaskExecutor {
    private ForkJoinPool forkJoinPool = new ForkJoinPool(20);
    //private List<ExportTask> exportTasks = new ArrayList<>();

    public TaskExecutor() {
        if (forkJoinPool == null) {
            forkJoinPool = new ForkJoinPool();
        }
    }

    public void invokeTask(List<Table> tables) {
        for (Table table : tables) {
            /* 划分的子任务*/
            ExportTask task = new ExportTask(table);
            //exportTasks.add(task);
            /* 调用任务并等待返回执行结果 */
            forkJoinPool.invoke(task);
        }

        forkJoinPool.shutdown();
    }
}