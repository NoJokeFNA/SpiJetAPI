package de.worldoneo.spijetapi.utils;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;

    public AsyncExecutor() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public AsyncExecutor(int corePoolSize) {
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
    }

    /**
     * Runs the callable async and returns its future.
     * Its recommended to take a look at <code>#complete(Supplier)</code>
     * as it provides better handling.
     *
     * @param callable the callable to run async
     * @param <T>      the type which the callable returns
     * @return the future of the async running callable
     */
    public <T> Future<T> submit(Callable<T> callable) {
        return getThreadPoolExecutor().submit(callable);
    }

    /**
     * Runs the supplier async and returns a {@link CompletableFuture}
     *
     * @param supplier the supplier to run in the completable future
     * @param <T>      the return type of the supplier
     * @return a {@link CompletableFuture} executing the task
     */
    public <T> CompletableFuture<T> complete(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, getThreadPoolExecutor());
    }

    public void submit(Runnable runnable) {
        getThreadPoolExecutor().submit(runnable);
    }

    public final ThreadPoolExecutor getThreadPoolExecutor() {
        return this.threadPoolExecutor;
    }

    public int getCorePoolSize() {
        return getThreadPoolExecutor().getCorePoolSize();
    }

    public void setCorePoolSize(int corePoolSize) {
        getThreadPoolExecutor().setCorePoolSize(corePoolSize);
    }
}
