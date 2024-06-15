package io.github.devsong.base.fsm.scanner;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.devsong.base.fsm.AbstractProcessor;
import io.github.devsong.base.fsm.annotation.StateDefinition;
import io.github.devsong.base.fsm.enums.BusinessTypeEnum;
import io.github.devsong.base.fsm.enums.ChannelTypeEnum;
import io.github.devsong.base.fsm.enums.FsmEventEnum;
import io.github.devsong.base.fsm.enums.StateEnum;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * date:  2024/6/16
 * author:guanzhisong
 */
@Component
@Slf4j
public class ProcessorBeanScanner implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 结构为 状态-事件-processor 三层
     */
    private Map<StateEnum, Map<FsmEventEnum, SingleProcessor>> container = Maps.newConcurrentMap();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(StateDefinition.class);
        if (beanMap == null || beanMap.isEmpty()) {
            log.warn("can not find any state processor");
            return;
        }
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            AbstractProcessor bean = (AbstractProcessor) entry.getValue();
            StateDefinition definition = bean.getClass().getAnnotation(StateDefinition.class);
            StateEnum[] states = definition.state();
            BusinessTypeEnum[] businessTypes = definition.biz();
            ChannelTypeEnum[] channels = definition.channel();
            SingleProcessor singleProcessor = SingleProcessor.builder()
                    .processor(bean)
                    .channels(
                            channels == null || channels.length == 0
                                    ? Sets.newHashSet(ChannelTypeEnum.values())
                                    : Sets.newHashSet(channels))
                    .businessTypes(
                            businessTypes == null || businessTypes.length == 0
                                    ? Sets.newHashSet(BusinessTypeEnum.values())
                                    : Sets.newHashSet(businessTypes))
                    .build();
            FsmEventEnum fsmEvent = definition.event();
            for (StateEnum s : states) {
                Map<FsmEventEnum, SingleProcessor> fsmEventMap = Maps.newHashMap();
                fsmEventMap.put(fsmEvent, singleProcessor);
                container.put(s, fsmEventMap);
            }
        }
    }

    public SingleProcessor getSingleProcessor(StateEnum stateEnum, FsmEventEnum fsmEvent) {
        Map<FsmEventEnum, SingleProcessor> fsmEventMap = container.get(stateEnum);
        if (fsmEventMap != null) {
            return fsmEventMap.get(fsmEvent);
        }
        return null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SingleProcessor {
        private AbstractProcessor<?> processor;
        private Set<BusinessTypeEnum> businessTypes;
        private Set<ChannelTypeEnum> channels;
    }
}
