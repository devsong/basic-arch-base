package io.github.devsong.base.fsm;

import com.google.common.collect.Maps;
import io.github.devsong.base.fsm.context.FsmOrder;
import io.github.devsong.base.fsm.context.FsmOrderDetail;
import io.github.devsong.base.fsm.enums.FsmEventEnum;
import io.github.devsong.base.fsm.enums.StateEnum;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateContext {
    /**
     * 订单状态
     */
    private StateEnum state;
    /**
     * 单号
     */
    private Long orderId;
    /**
     * 主单信息
     */
    private FsmOrder order;
    /**
     * 订单明细数据
     */
    private FsmOrderDetail orderDetail;
    /**
     * 事件类型
     */
    private FsmEventEnum fsmEvent;
    /**
     * 扩展参数
     */
    private Map<String, Object> contextMap = Maps.newHashMap();

    public void addContextData(String key, Object data) {
        contextMap.put(key, data);
    }

    public Object getContextData(String key) {
        return contextMap.get(key);
    }
}
