package io.github.devsong.base.test;


import io.github.devsong.base.test.truncate.TruncateService;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class IntegrationBaseTest extends BaseTest {
    public abstract ApplicationContext getContext();

    protected List<Object> mockbeans = new ArrayList<>();

    protected void setUp() {
        truncate();
        resetMocks();
    }

    protected void truncate() {
        ApplicationContext context = getContext();
        Map<String, TruncateService> truncateServiceMap = context.getBeansOfType(TruncateService.class);
        for (Map.Entry<String, TruncateService> entry : truncateServiceMap.entrySet()) {
            String key = entry.getKey();
            TruncateService service = entry.getValue();
            try {
                service.truncate();
            } catch (Exception e) {
                log.error("truncate key {} do truncate resources error", key, e);
            }
        }
    }

    protected void resetMocks() {
        ApplicationContext context = getContext();
        if (mockbeans.size() == 0) {
            for (String name : context.getBeanDefinitionNames()) {
                try {
                    Object bean = context.getBean(name);
                    if (MockUtil.isMock(bean)) {
                        mockbeans.add(bean);
                    }
                } catch (Exception e) {
                    log.error("reset mock bean {} error", name, e);
                }
            }
        }
        for (Object bean : mockbeans) {
            Mockito.reset(bean);
        }
    }
}
