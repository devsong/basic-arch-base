package io.github.devsong.base.fsm.annotation;

import io.github.devsong.base.fsm.enums.BusinessTypeEnum;
import io.github.devsong.base.fsm.enums.ChannelTypeEnum;
import io.github.devsong.base.fsm.enums.FsmEventEnum;
import io.github.devsong.base.fsm.enums.StateEnum;
import java.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface StateDefinition {

    StateEnum[] state() default {};

    BusinessTypeEnum[] biz() default {};

    ChannelTypeEnum[] channel() default {};

    FsmEventEnum event() default FsmEventEnum.NULL;
}
