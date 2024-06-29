package com.example.backend_assingment.configs;

import java.io.IOException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.example.backend_assingment")
public class ApplicationConfiguration {

    @Autowired
    private DbConfigs dbConfigs;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbConfigs.getDatabaseUrl());
        dataSource.setUsername(dbConfigs.getDatabaseUsername());
        dataSource.setPassword(dbConfigs.getDatabasePassword());
        dataSource.setDriverClassName(dbConfigs.getDatabaseDriver());
        log.info("Datasource created successfully, URL: {}, Driver : {}, ", dbConfigs.getDatabaseUrl(), dbConfigs.getDatabaseDriver());
        return dataSource;
    }



    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer(ApplicationContext applicationContext)
        throws IOException {

        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        propertySourcesPlaceholderConfigurer.setLocations(resolver.getResources("classpath*:/*.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

}
