package io.github.devsong.base.fsm.check;

import java.util.List;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
public interface Checkable<T> {
    /**
     * 异步检查器
     *
     * @return
     */
    List<Checker> asyncChecker();

    /**
     * 同步检查器
     *
     * @return
     */
    List<Checker> syncChecker();
}
