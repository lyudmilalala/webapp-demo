package com.jerry.webappdemojpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = CouponRepository.class,
        entityManagerFactoryRef = "payDevEntityManagerFactory",
        transactionManagerRef = "payDevTransactionManager"
)
public class PayDevJpaConfig {

    @Bean(name = "payDevEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean payDevEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("payDevDataSource") DataSource dataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        
        return builder
                .dataSource(dataSource)
                .packages("com.jerry.webappdemojpa")
                .persistenceUnit("payDev")
                .properties(properties)
                .build();
    }

    @Bean(name = "payDevTransactionManager")
    public PlatformTransactionManager payDevTransactionManager(
            @Qualifier("payDevEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}