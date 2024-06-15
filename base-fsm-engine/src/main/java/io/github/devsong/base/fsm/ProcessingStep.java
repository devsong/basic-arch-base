package io.github.devsong.base.fsm;

import io.github.devsong.base.fsm.check.Checkable;
import io.github.devsong.base.fsm.enums.StateEnum;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
public interface ProcessingStep<T> {
    /**
     * 状态机流转至下一个状态
     *
     * @param stateContext
     * @return
     */
    StateEnum transmitNextState(StateContext stateContext);

    /**
     * 准备数据
     *
     * @param stateContext
     * @return
     */
    ServiceResult<T> prepare(StateContext stateContext);

    /**
     * 获取检查验证对象
     *
     * @return
     */
    Checkable<T> getCheckable();

    /**
     * @param nextState
     * @param stateContext
     * @return
     */
    ServiceResult<T> process(StateEnum nextState, StateContext stateContext);

    /**
     * 保存数据，执行事物类型的操作,偏DB业务逻辑
     * @param stateContext
     * @return
     */
    ServiceResult<T> save(StateContext stateContext);

    void afterProcess(StateContext stateContext);
}
