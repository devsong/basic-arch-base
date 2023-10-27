package io.github.devsong.base.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import org.slf4j.TtlMDCAdapter;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
public class TtlMdcListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    @Override
    public void start() {
        addInfo("load TtlMDCAdapter...");
        TtlMDCAdapter.getInstance();
    }

    @Override
    public void stop() {}

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {}

    @Override
    public void onReset(LoggerContext loggerContext) {}

    @Override
    public void onStop(LoggerContext loggerContext) {}

    @Override
    public void onLevelChange(Logger logger, Level level) {}
}
