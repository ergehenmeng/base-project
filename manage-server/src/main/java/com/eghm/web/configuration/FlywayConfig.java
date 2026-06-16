package com.eghm.web.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Flyway 手动配置
 * 用于兼容 MySQL 5.7 和 Flyway 7.15.0
 *
 * @author eghm
 * @since 2026/6/16
 */
@Configuration
public class FlywayConfig {
    
    @Value("${spring.flyway.enabled:true}")
    private boolean enabled;
    
    @Value("${spring.flyway.locations:classpath:db/migration}")
    private String locations;
    
    @Value("${spring.flyway.baseline-on-migrate:true}")
    private boolean baselineOnMigrate;
    
    @Value("${spring.flyway.baseline-version:0}")
    private String baselineVersion;
    
    @Value("${spring.flyway.table:flyway_schema_history}")
    private String table;
    
    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        if (!enabled) {
            return null;
        }
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(locations)
                .baselineOnMigrate(baselineOnMigrate)
                .baselineVersion(baselineVersion)
                .placeholderReplacement(false)
                .table(table)
                .load();
    }
}
