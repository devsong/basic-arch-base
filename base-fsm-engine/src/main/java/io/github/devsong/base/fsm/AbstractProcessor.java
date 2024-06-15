package io.github.devsong.base.fsm;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import io.github.devsong.base.common.thread.TtlThreadToolTaskExecutor;
import io.github.devsong.base.fsm.check.Checkable;
import io.github.devsong.base.fsm.check.Checker;
import io.github.devsong.base.fsm.enums.StateEnum;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
@Slf4j
public abstract class AbstractProcessor<T> implements ProcessingStep {

    @Resource
    private TtlThreadToolTaskExecutor executor;

    public ServiceResult<T> action(StateContext stateContext) {
        String className = getClass().getSimpleName();
        long start = System.currentTimeMillis();

        // prepare stage
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("start execute processor {} orderId {}", className, stateContext.getOrderId());
        ServiceResult prepareResult = this.prepare(stateContext);
        stopwatch.stop();
        if (!prepareResult.isSuccess()) {
            return prepareResult;
        }
        log.info("prepare stage cost {}", stopwatch);

        // check stage
        stopwatch.start();
        ServiceResult<T> checkResult = this.check(stateContext, this.getCheckable());
        if (checkResult != null && !checkResult.isSuccess()) {
            return checkResult;
        }
        stopwatch.stop();
        log.info("check stage cost {}", stopwatch);

        // process business logic stage
        StateEnum nextState = this.transmitNextState(stateContext);
        stopwatch.start();
        ServiceResult processResult = this.process(nextState, stateContext);
        stopwatch.stop();
        log.info("process stage cost {}", stopwatch);
        if (!processResult.isSuccess()) {
            return processResult;
        }

        stopwatch.start();
        // save stage
        ServiceResult saveResult = this.save(stateContext);
        stopwatch.stop();
        log.info("process stage cost {}", stopwatch);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }

        // after process stage
        stopwatch.start();
        this.afterProcess(stateContext);
        stopwatch.stop();
        log.info("process stage cost {}", stopwatch);

        if (nextState != StateEnum.VIRTUAL) {
            // send mq or execute async order state change event
        }
        long cost = System.currentTimeMillis() - start;
        log.info("end execute processor {} orderId {} cost {}", className, stateContext.getOrderId(), cost);
        return saveResult;
    }

    private ServiceResult<T> check(StateContext stateContext, Checkable<T> checkable) {
        if (checkable.syncChecker() != null && checkable.syncChecker().size() > 0) {
            for (Checker<T> c : checkable.syncChecker()) {
                ServiceResult<T> check = c.check(stateContext);
                if (!check.isSuccess()) {
                    return check;
                }
            }
        }
        if (checkable.asyncChecker() != null && checkable.asyncChecker().size() == 1) {
            return checkable.asyncChecker().get(0).check(stateContext);
        }

        if (checkable.asyncChecker() != null && checkable.asyncChecker().size() > 1) {
            CountDownLatch latch = new CountDownLatch(checkable.asyncChecker().size());
            List<Future<ServiceResult<T>>> list = Lists.newArrayList();
            for (Checker<T> c : checkable.asyncChecker()) {
                Future<ServiceResult<T>> resultFuture = executor.submit(() -> {
                    try {
                        return c.check(stateContext);
                    } finally {
                        latch.countDown();
                    }
                });
                list.add(resultFuture);
            }
            try {
                latch.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("latch await time out for order {}", stateContext.getOrderId(), e);
                throw new RuntimeException(e);
            }
            for (Future<ServiceResult<T>> f : list) {
                try {
                    ServiceResult<T> checkResult = f.get();
                    if (!checkResult.isSuccess()) {
                        return checkResult;
                    }
                } catch (Exception e) {
                    log.error("async checker execute error {}", stateContext.getOrderId(), e);
                }
            }
        }
        return new ServiceResult<>();
    }
}
