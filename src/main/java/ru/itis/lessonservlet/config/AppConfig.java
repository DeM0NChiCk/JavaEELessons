package ru.itis.lessonservlet.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.itis.lessonservlet.filter.AuthFilter;
import ru.itis.lessonservlet.filter.LoggingFilter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@PropertySource("classpath:app.properties")
@EnableWebMvc
@EnableJpaRepositories(basePackages = "ru.itis.lessonservlet.repository")
@ComponentScan("ru.itis.lessonservlet")
@EnableTransactionManagement
public class AppConfig {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USER}")
    private String dbUser;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Value("${PROTECTED_URIS}")
    private String protectedUris;

    @Value("${PROTECTED_ADMIN_URIS}")
    private String protectedAdminUris;

    @Value("${NOTAUTH_URIS}")
    private String notAuthUris;

    @Value("${PROTECTED_REDIRECT}")
    private String protectedRedirect;

    @Value("${PROTECTED_ADMIN_REDIRECT}")
    private String protectedAdminRedirect;

    @Value("${NOTAUTH_REDIRECT}")
    private String notAuthRedirect;

    @Value("${AUTHORIZATION}")
    private String authorizationHeader;

    @Value("${IS_ADMIN}")
    private String isAdminFlag;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("ru.itis.lessonservlet.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        properties.setProperty("hibernate.show_sql", "true"); // показать SQL
        properties.setProperty("hibernate.format_sql", "true"); // красиво форматировать
        properties.setProperty("hibernate.use_sql_comments", "true"); // комменты от Hibernate (опционально)

        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "PROTECTED_URIS")
    public List<String> protectedUris() {
        return Arrays.asList(protectedUris.split(","));
    }

    @Bean(name = "PROTECTED_ADMIN_URIS")
    public List<String> protectedAdminUris() {
        return Arrays.asList(protectedAdminUris.split(","));
    }

    @Bean(name = "NOTAUTH_URIS")
    public List<String> notAuthUris() {
        return Arrays.asList(notAuthUris.split(","));
    }

    @Bean(name = "PROTECTED_REDIRECT")
    public String protectedRedirect() {
        return protectedRedirect;
    }

    @Bean(name = "PROTECTED_ADMIN_REDIRECT")
    public String protectedAdminRedirect() {
        return protectedAdminRedirect;
    }

    @Bean(name = "NOTAUTH_REDIRECT")
    public String notAuthRedirect() {
        return notAuthRedirect;
    }

    @Bean(name = "AUTHORIZATION")
    public String authorizationHeader() {
        return authorizationHeader;
    }

    @Bean(name = "IS_ADMIN")
    public String isAdminFlag() {
        return isAdminFlag;
    }

    @Bean(name = "SECRET_KEY")
    public String secretKey() {
        return secretKey;
    }

}