package io.github.devsong.base.common.thread;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author zhisong.guan
 * @date 2022/10/13 19:42
 */
public class TtlThreadToolTaskExecutor extends ThreadPoolTaskExecutor {
    /**
     * 错误提示语
     */
    private static final String ERROR_MESSAGE = "task can not be null";

    @Override
    public void execute(Runnable task) {
        super.execute(Objects.requireNonNull(TtlRunnable.get(task), ERROR_MESSAGE));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(Objects.requireNonNull(TtlRunnable.get(task), ERROR_MESSAGE), startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(Objects.requireNonNull(TtlRunnable.get(task), ERROR_MESSAGE));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(Objects.requireNonNull(TtlCallable.get(task), ERROR_MESSAGE));
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(Objects.requireNonNull(TtlRunnable.get(task), ERROR_MESSAGE));
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(Objects.requireNonNull(TtlCallable.get(task), ERROR_MESSAGE));
    }
}
