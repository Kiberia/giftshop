package ru.war.robots.giftshop.config;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;


@Configuration
public class DslConfig {
    @Value("${spring.datasource.url:}")
    String dataSourceUrl;

    @Value("${spring.datasource.username:}")
    String dataSourceUsername;

    @Value("${spring.datasource.password:}")
    String dataSourcePassword;

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider
                   (new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);
        jooqConfiguration.set(connection());
        jooqConfiguration.set(connectionProvider());

        return jooqConfiguration;
    }

    private Connection connection() {
        String userName = dataSourceUsername;
        String password = dataSourcePassword;
        String url = dataSourceUrl;

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            return conn;
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   /* @Bean
    public void initConnections() {

    }*/
}
