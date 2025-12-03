package com.jerry.webappdemojpa.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.jerry.webappdemojpa.paydevDB",
        entityManagerFactoryRef = "payDevEntityManagerFactory",
        transactionManagerRef = "payDevTransactionManager"
)
public class PayDevJpaConfig {

    @Autowired
    @Qualifier("payDevDataSource")
    private DataSource dataSource;

    @Bean(name = "payDevEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean payDevEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        
        return builder
                .dataSource(dataSource)
                .packages("com.jerry.webappdemojpa.paydevDB")
                .persistenceUnit("payDev")
                .properties(properties)
                .build();
    }

    @Bean(name = "payDevTransactionManager")
    PlatformTransactionManager payDevTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(payDevEntityManagerFactory(builder).getObject()));
    }
//    public PlatformTransactionManager payDevTransactionManager(
//            @Qualifier("payDevEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
}