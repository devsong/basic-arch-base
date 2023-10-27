package io.github.devsong.base.common.thread;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author zhisong.guan
 * @date 2022/10/13 19:42
 */
public class TtlThreadToolTaskScheduler extends ThreadPoolTaskScheduler {

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

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        return super.scheduleWithFixedDelay(Objects.requireNonNull(TtlRunnable.get(task)), delay);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        return super.scheduleWithFixedDelay(Objects.requireNonNull(TtlRunnable.get(task)), startTime, delay);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return super.schedule(Objects.requireNonNull(TtlRunnable.get(task)), trigger);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        return super.schedule(Objects.requireNonNull(TtlRunnable.get(task)), startTime);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
        return super.schedule(Objects.requireNonNull(TtlRunnable.get(task)), startTime);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
        return super.scheduleAtFixedRate(Objects.requireNonNull(TtlRunnable.get(task)), startTime, period);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
        return super.scheduleAtFixedRate(Objects.requireNonNull(TtlRunnable.get(task)), period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
        return super.scheduleWithFixedDelay(Objects.requireNonNull(TtlRunnable.get(task)), startTime, delay);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
        return super.scheduleWithFixedDelay(Objects.requireNonNull(TtlRunnable.get(task)), delay);
    }
}
