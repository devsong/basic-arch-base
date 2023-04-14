package io.github.devsong.base.entity;

public interface GlobalConstant {
    String SYSTEM_PACKAGE_PREFIX = "io.github.devsong";

    String SYSTEM_LOG_PREFIX_SERIAL = "ser";

    String SYSTEM_LOG_PREFIX_PAYMENT = "pay";

    String SYSTEM_LOG_PREFIX_ADMIN = "adm";

    String DATE_FORMAT = "yyyy-MM-dd";
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 集成测试专用的profile
     */
    String PROFILE_TEST_ENV = "test";

    /**
     * 取反
     */
    String NOT_PROFILE_TEST_ENV = "!" + PROFILE_TEST_ENV;
}
