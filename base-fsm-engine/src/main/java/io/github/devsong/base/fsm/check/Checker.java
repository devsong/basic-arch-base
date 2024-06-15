package io.github.devsong.base.fsm.check;

import io.github.devsong.base.fsm.ServiceResult;
import io.github.devsong.base.fsm.StateContext;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
public interface Checker<T> {
    ServiceResult<T> check(StateContext context);
}
