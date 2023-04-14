package io.github.devsong.base.common.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 数据库数据源配置
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@EqualsAndHashCode(callSuper = false)
public class DataSourceProperties extends BaseDataSourceProperties {
    protected String url;

    protected String username;

    protected String password;

    @Override
    public HikariDataSource config(HikariDataSource dataSource) {
        if (getUrl().indexOf("?") == -1) {
            dataSource.setJdbcUrl(getUrl() + "?" + DEFAULT_MYSQL_CONNECT_PARAMS);
        } else {
            dataSource.setJdbcUrl(getUrl());
        }
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        super.config(dataSource);
        return dataSource;
    }
}
