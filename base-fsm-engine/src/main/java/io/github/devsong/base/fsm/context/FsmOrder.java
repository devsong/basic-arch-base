package io.github.devsong.base.fsm.context;

import io.github.devsong.base.fsm.enums.BusinessTypeEnum;
import io.github.devsong.base.fsm.enums.ChannelTypeEnum;
import io.github.devsong.base.fsm.enums.StateEnum;

/**
 * date:  2024/6/16
 * author:guanzhisong
 */
public interface FsmOrder {
    Long orderId();

    StateEnum state();

    BusinessTypeEnum biz();

    ChannelTypeEnum channel();
}
