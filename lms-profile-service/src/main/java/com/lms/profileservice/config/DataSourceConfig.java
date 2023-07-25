package com.lms.profileservice.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.lms.profileservice.repo", entityManagerFactoryRef = "localContainerEntityManagerFactoryBean", transactionManagerRef = "profileServiceTransactionManager")
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.configuration")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder){
        return builder
                .dataSource(dataSource(dataSourceProperties()))
                .packages("com.lms.profileservice.entity")
                .persistenceUnit("profile-service")
                .build();
    }

    @Bean(name = "profileServiceTransactionManager")
    public JpaTransactionManager profileServiceTransactionManager(@Qualifier("localContainerEntityManagerFactoryBean") EntityManagerFactory profileServiceEntityManagerFactory ) {
        return new JpaTransactionManager(profileServiceEntityManagerFactory);
    }
}
