package xyz.shakirzyanov.warehouseapp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableSpringDataWebSupport
public class DatabaseConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(
            @Value("${clickhouse.jdbcUrl}") String jdbcUrl,
            @Value("${clickhouse.user}") String user,
            @Value("${clickhouse.password}") String password) {
        var config = new HikariConfig();
        config.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        var ds = new HikariDataSource(config);
        return new JdbcTemplate(ds);
    }
}
