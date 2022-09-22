package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Value("${connectionString}")
    String url;

    @Value("${usernameDB}")
    String userNameDB;

    @Value("${passwordDB}")
    String passwordDB;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;


    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource geDataSource()
    {
        System.out.println("userNameDB =>"+userNameDB);
        System.out.println("passwordDB =>"+passwordDB);
        System.out.println("driverClassName =>"+driverClassName);
        System.out.println("url =>"+url);
        
        

        System.out.println("\n\n  AQUI......................");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(userNameDB);
        dataSourceBuilder.password(passwordDB);
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        return dataSourceBuilder.build();
    }
}
