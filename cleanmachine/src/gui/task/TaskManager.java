package gui.task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskManager {

    private static TaskManager manager = new TaskManager();

    private ExecutorService service = Executors.newCachedThreadPool();

    public void submitTask(Runnable r) {
        service.submit(r);
    }

    public static TaskManager getManager() {
        return manager;
    }
}
