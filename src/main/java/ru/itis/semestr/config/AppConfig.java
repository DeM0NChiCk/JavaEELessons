package ru.itis.semestr.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.EntityManagerFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:app.properties")
@EnableJpaRepositories(basePackages = "ru.itis.semestr.repository")
@ComponentScan("ru.itis.semestr")
@EnableTransactionManagement
public class AppConfig {

    // ==========================================
    // ИНЖЕКЦИЯ СВОЙСТВ ИЗ APPLICATION.PROPERTIES
    // ==========================================

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    // --- Настройки для ваших компонентов ---
    @Value("${app.security.secret-key}")
    private String appSecretKey;

    @Value("${app.github.client-id}")
    private String githubClientId;

    @Value("${app.github.client-secret}")
    private String githubClientSecret;

    @Value("${app.github.client-uri}")
    private String githubClientUri;

    @Value("${app.google.api-key}")
    private String googleApiKey;

    @Value("${app.google.sheets-id}")
    private String googleSheetsId;

    @Value("${app.google.url}")
    private String googleUrl;


    // ==========================================
    // BEANS: БАЗА ДАННЫХ И JPA
    // ==========================================

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setDriverClassName(dbDriver);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("ru.itis.semestr.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");

        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    // ==========================================
    // BEANS: КОМПОНЕНТЫ SPRING BOOT АНАЛОГИ
    // ==========================================

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Shop Spring")
                        .version("1.0.0")
                        .description("Платформа управления заказами и аналитикой")
                        .contact(new Contact()
                                .name("Tech Support")
                                .email("support@example.com")
                                .url("https://example.com")))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // ==========================================
    // ЗАМЕНА @ConfigurationProperties
    // ==========================================

    @Bean
    public Security securityKey() {
        return Security.builder()
                .secretKey(appSecretKey)
                .build();
    }

    @Bean
    public Github githubConfig() {
        return Github.builder()
                .clientId(githubClientId)
                .clientSecret(githubClientSecret)
                .clientUri(githubClientUri)
                .build();
    }

    @Bean
    public Google googleConfig() {
        return Google.builder()
                .apiKey(googleApiKey)
                .sheetsId(googleSheetsId)
                .url(googleUrl)
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class Security {
        private String secretKey;
    }

    @Getter
    @Setter
    @Builder
    public static class Github {
        private String clientId;
        private String clientSecret;
        private String clientUri;
    }

    @Getter
    @Setter
    @Builder
    public static class Google {
        private String apiKey;
        private String sheetsId;
        private String url;
    }
}