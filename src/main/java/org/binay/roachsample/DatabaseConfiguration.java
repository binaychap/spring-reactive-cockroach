package org.binay.roachsample;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


//@Configuration
//@EnableR2dbcRepositories(basePackages = "org.binay.roachsample.repository")
public class DatabaseConfiguration  {
   /* @Override
    public ConnectionFactory connectionFactory() {
        io.r2dbc.postgresql.PostgresqlConnectionFactory
        return ConnectionFactories.get("r2dbc:postgresql://localhost:5432/sample");
    }*/

   /* @Override
    public ConnectionFactory connectionFactory() {
        io.r2dbc.pool.ConnectionPool.
        return ConnectionFactories.get(new io.r2dbc.postgresql.PostgresqlConnectionFactory(
                io.r2dbc.postgresql.PostgresqlConnectionFactory.builder()
                .host("localhost")
                .port(5432)
                .username("postgres")
                .password("thirumal")
                .database("sample")
                .build()););
    }*/
}
