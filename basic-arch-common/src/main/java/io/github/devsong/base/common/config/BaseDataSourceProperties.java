package io.github.devsong.base.common.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

/**
 * 公共的datasource配置
 *
 * @author guanzhisong
 */
@Data
public class BaseDataSourceProperties {
    protected static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    // protected static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DEFAULT_MYSQL_CONNECT_PARAMS = "serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false&zeroDateTimeBehavior=convertToNull";

    protected String driverClassName = DEFAULT_DRIVER;

    protected Integer initialSize = 2;

    protected Integer minIdle = 1;

    protected Integer maxActive = 20;

    protected Integer maxWait = 60000;

    protected Integer timeBetweenEvictionRunsMillis = 60000;

    protected Integer minEvictableIdleTimeMillis = 300000;

    protected String validationQuery = "SELECT 'x'";

    protected Boolean testWhileIdle = true;

    protected Boolean testOnBorrow = false;

    protected Boolean testOnReturn = false;

    protected Boolean poolPreparedStatements = true;

    protected Integer maxPoolPreparedStatementPerConnectionSize = 20;

    protected String filters = "stat";

    protected HikariDataSource config(HikariDataSource dataSource) {
        return dataSource;
    }
}
