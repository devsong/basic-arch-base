package io.github.devsong.base.common.datasource;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 数据源切换处理
 *
 * @author guanzhisong
 */
// @Slf4j
public class DynamicDataSourceContextHolder {
    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本， 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<Deque<String>> CONTEXT_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType) {
        Deque<String> queue = CONTEXT_HOLDER.get();
        if (queue == null) {
            queue = new ArrayDeque<>();
            CONTEXT_HOLDER.set(queue);
        }
        queue.push(dsType);
    }

    /**
     * 获得数据源的变量
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get().peek();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType() {
        Deque<String> q = CONTEXT_HOLDER.get();
        q.pop();
        if (q.isEmpty()) {
            CONTEXT_HOLDER.remove();
        }
    }
}
