package com.jerry.webappdemojpa.config;

import com.jerry.webappdemojpa.qcloudDB.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.jerry.webappdemojpa.qcloudDB",
        entityManagerFactoryRef = "qcloudEntityManagerFactory",
        transactionManagerRef = "qcloudTransactionManager"
)
public class QCloudJpaConfig {

    @Autowired
    @Qualifier("qcloudDataSource")
    private DataSource dataSource;

    @Primary
    @Bean(name = "qcloudEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean qcloudEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        
        return builder
                .dataSource(dataSource)
                .packages("com.jerry.webappdemojpa.qcloudDB")
                .persistenceUnit("qcloud")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "qcloudTransactionManager")
    PlatformTransactionManager qcloudTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(qcloudEntityManagerFactory(builder).getObject()));
    }

//    public PlatformTransactionManager qcloudTransactionManager(
//            @Qualifier("qcloudEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
}