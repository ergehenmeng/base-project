package com.eghm.interfaces.manage.configuration;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@AllArgsConstructor
@EnableConfigurationProperties(FlywayProperties.class)
public class FlywayConfig {
    
    private final FlywayProperties flywayProperties;
    
    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        if (!flywayProperties.isEnabled()) {
            return null;
        }
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayProperties.getLocations().toArray(new String[0]))
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .baselineVersion(flywayProperties.getBaselineVersion())
                .placeholderReplacement(false)
                .cleanDisabled(true)
                .table(flywayProperties.getTable())
                .load();
    }
}
