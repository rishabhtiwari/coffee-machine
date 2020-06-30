package coffeeMachine.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class TaskExecutor {

    private int POOL_SIZE = 1;
    private final RejectedExecutionHandler handler = new CustomRejectedExecutionHandler();
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), handler);

    public void init (int poolSize) {
        if (poolSize > 0) POOL_SIZE = poolSize;
    }

    public void submitTasks(Runnable tasks) {
        executor.submit(tasks);
    }

    public Future submitTasks(Callable tasks) {
        return executor.submit(tasks);
    }

    public void close () {
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                System.out.println("Exception In Processing");
                e.printStackTrace();
            }
        }
    }
}
