package com.jerry.webappdemojpa.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {



//    @ConfigurationProperties("spring.datasource.qcloud")
    @Primary
    @Bean
    public DataSource qcloudDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/qcloud?characterEncoding=utf-8&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false");
        dataSource.setUsername("root");
        dataSource.setPassword("1996S05y22u!");
        return dataSource;
    }


//    @ConfigurationProperties("spring.datasource.paydev")
    @Bean
    public DataSource payDevDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/pay_dev?characterEncoding=utf-8&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false");
        dataSource.setUsername("root");
        dataSource.setPassword("1996S05y22u!");
        return dataSource;
    }
}