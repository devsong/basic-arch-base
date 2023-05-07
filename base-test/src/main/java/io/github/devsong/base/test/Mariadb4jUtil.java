package io.github.devsong.base.test;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import com.zaxxer.hikari.HikariDataSource;
import io.github.devsong.base.common.OSInfo;
import java.util.Random;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.flywaydb.core.Flyway;

/**
 * date:  2023/4/20
 * author:guanzhisong
 */
@Slf4j
public class Mariadb4jUtil {
    public static final String MYSQL_DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";
    public static final int START_PORT = 50000;
    public static final int RANDOM_PORT_RANGE = 1000;

    public static MariaDB4jSpringService mariaDB4jSpringService() {
        MariaDB4jSpringService mariaDB4jSpringService = new MariaDB4jSpringService();
        int port = new Random().nextInt(RANDOM_PORT_RANGE) + START_PORT;
        mariaDB4jSpringService.setDefaultPort(port);
        DBConfigurationBuilder config = mariaDB4jSpringService.getConfiguration();
        config.addArg("--character-set-server=utf8mb4");
        config.addArg("--lower_case_table_names=1");
        config.addArg("--collation-server=utf8mb4_general_ci");
        config.addArg("--user=root");
        config.addArg("--max-connections=512");
        config.setBaseDir(SystemUtils.JAVA_IO_TMPDIR + "/MariaDB4j/base");
        config.setDataDir(SystemUtils.JAVA_IO_TMPDIR + "/MariaDB4j/data");
        config.setDeletingTemporaryBaseAndDataDirsOnShutdown(true);

        if (OSInfo.isMacOSX() || OSInfo.isMacOS()) {
            // MacOS/MacOSX m1芯片可以选择使用本机的mariadb启动
            config.setUnpackingFromClasspath(false);
            config.setBaseDir("/opt/homebrew");
        }
        config.setLibDir(System.getProperty("java.io.tmpdir") + "/MariaDB4j/no-libs");

        log.info("mariadb4j port {}", port);
        mariaDB4jSpringService.start();
        return mariaDB4jSpringService;
    }

    public static DataSource buildDataSource(
            MariaDB4jSpringService mariaDB4jSpringService, String schema, String migrationScriptPath)
            throws ManagedProcessException {
        mariaDB4jSpringService.getDB().createDB(schema);
        DBConfigurationBuilder config = mariaDB4jSpringService.getConfiguration();
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(MYSQL_DRIVER_CLASS_NAME);
        hikariDataSource.setJdbcUrl(config.getURL(schema));
        hikariDataSource.setUsername(USERNAME);
        hikariDataSource.setPassword(PASSWORD);

        Flyway flyway = Flyway.configure()
                .dataSource(hikariDataSource)
                .cleanDisabled(true)
                .locations(migrationScriptPath)
                .table("flyway_schema_history")
                .baselineOnMigrate(true)
                .baselineVersion("1")
                .validateOnMigrate(true)
                .schemas(schema)
                .defaultSchema(schema)
                .encoding("UTF-8")
                .outOfOrder(true)
                .load();
        flyway.migrate();

        return hikariDataSource;
    }
}
