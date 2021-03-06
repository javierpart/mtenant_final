package com.mycompany.myapp.config;

import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.binary.Base64InputStream;
import com.mycompany.myapp.config.multitenant.TenantAwareDataSource;
import com.mycompany.myapp.domain.User;
import com.zaxxer.hikari.HikariDataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.sql.DataSource;
import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableConfigurationProperties({ DataSourceTruststoreProperties.class })
public class DataSourceConfiguration {

    private final boolean tlsEnabled;

    public DataSourceConfiguration(@Value("${spring.datasource.tlsEnabled:false}") boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }

    @Bean
    @ConfigurationProperties("multitenancy.master.datasource")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @LiquibaseDataSource
    @ConfigurationProperties("multitenancy.master.datasource.hikari")
    public DataSource masterDataSource(DataSourceTruststoreProperties trustStoreProperties) throws Exception {
        HikariDataSource dataSource = masterDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setAutoCommit(false);
        dataSource.setReadOnly(false);
        dataSource.setPoolName("masterDataSource");
        if (tlsEnabled) {
            try (
                InputStream in = new Base64InputStream(
                    new ByteArrayInputStream(trustStoreProperties.getContent().getBytes(StandardCharsets.UTF_8.name()))
                )
            ) {
                Files.copy(in, Paths.get(trustStoreProperties.getLocation()), StandardCopyOption.REPLACE_EXISTING);
            }
            dataSource.addDataSourceProperty("javax.net.ssl.trustStore", trustStoreProperties.getLocation());
            dataSource.addDataSourceProperty("javax.net.ssl.trustStoreType", trustStoreProperties.getType());
            dataSource.addDataSourceProperty("javax.net.ssl.trustStorePassword", trustStoreProperties.getPassword());
        }
        return dataSource;
    }

    @Bean
    @Primary
    @ConfigurationProperties("multitenancy.tenant.datasource")
    public DataSourceProperties tenantDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("multitenancy.tenant.datasource.hikari")
    public DataSource tenantDataSource(DataSourceTruststoreProperties trustStoreProperties) throws Exception {
        HikariDataSource dataSource = tenantDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setAutoCommit(false);
        dataSource.setReadOnly(false);
        dataSource.setPoolName("tenantDataSource");
        if (tlsEnabled) {
            try (
                InputStream in = new Base64InputStream(
                    new ByteArrayInputStream(trustStoreProperties.getContent().getBytes(StandardCharsets.UTF_8.name()))
                )
            ) {
                Files.copy(in, Paths.get(trustStoreProperties.getLocation()), StandardCopyOption.REPLACE_EXISTING);
            }
            dataSource.addDataSourceProperty("javax.net.ssl.trustStore", trustStoreProperties.getLocation());
            dataSource.addDataSourceProperty("javax.net.ssl.trustStoreType", trustStoreProperties.getType());
            dataSource.addDataSourceProperty("javax.net.ssl.trustStorePassword", trustStoreProperties.getPassword());
        }
        return new TenantAwareDataSource(dataSource);
    }
}
