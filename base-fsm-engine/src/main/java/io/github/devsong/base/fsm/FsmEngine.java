package io.github.devsong.base.fsm;

import io.github.devsong.base.common.util.JsonUtil;
import io.github.devsong.base.fsm.scanner.ProcessorBeanScanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FsmEngine {

    private final ProcessorBeanScanner beanScanner;

    public ServiceResult<?> sendEvent(StateContext stateContext) {
        ProcessorBeanScanner.SingleProcessor singleProcessor =
                beanScanner.getSingleProcessor(stateContext.getState(), stateContext.getFsmEvent());
        if (singleProcessor == null) {
            log.info("processor is null {}", stateContext.getOrderId());
            return ServiceResult.buildFail("processor is null");
        }
        AbstractProcessor<?> processor = singleProcessor.getProcessor();
        boolean bizTypeMatch = singleProcessor
                .getBusinessTypes()
                .contains(stateContext.getOrder().biz());
        if (!bizTypeMatch) {
            log.info(
                    "processor {} biz {} {} is not match for order {}",
                    processor.getClass().getSimpleName(),
                    stateContext.getOrder().biz(),
                    JsonUtil.toJSONString(singleProcessor.getBusinessTypes()),
                    stateContext.getOrderId());
            return ServiceResult.buildFail("processor bizType not match");
        }
        boolean channelMatch =
                singleProcessor.getChannels().contains(stateContext.getOrder().channel());
        if (!channelMatch) {
            log.info(
                    "processor {} channel {} {} is not match for order {}",
                    processor.getClass().getSimpleName(),
                    stateContext.getOrder().channel(),
                    JsonUtil.toJSONString(singleProcessor.getChannels()),
                    stateContext.getOrderId());
            return ServiceResult.buildFail("processor channel not match");
        }
        return processor.action(stateContext);
    }
}
