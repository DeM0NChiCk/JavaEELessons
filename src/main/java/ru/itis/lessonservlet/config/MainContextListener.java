package ru.itis.lessonservlet.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.mapper.UserMapper;
import ru.itis.lessonservlet.mapper.impl.CategoryMapperImpl;
import ru.itis.lessonservlet.mapper.impl.ProductMapperImpl;
import ru.itis.lessonservlet.mapper.impl.UserMapperImpl;
import ru.itis.lessonservlet.repository.CategoryRepository;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.repository.UserRepository;
import ru.itis.lessonservlet.repository.impl.CategoryRepositoryImpl;
import ru.itis.lessonservlet.repository.impl.ProductRepositoryImpl;
import ru.itis.lessonservlet.repository.impl.UserRepositoryImpl;
import ru.itis.lessonservlet.service.CategoryService;
import ru.itis.lessonservlet.service.ProductService;
import ru.itis.lessonservlet.service.UserService;
import ru.itis.lessonservlet.service.impl.CategoryServiceImpl;
import ru.itis.lessonservlet.service.impl.ProductServiceImpl;
import ru.itis.lessonservlet.service.impl.UserServiceImpl;
import ru.itis.lessonservlet.utils.PropertyReader;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@WebListener
public class MainContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        DataSource dataSource = dataSource();
        context.setAttribute("dataSource", dataSource);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        context.setAttribute("jdbcTemplate", jdbcTemplate);

        ProductMapper productMapper = new ProductMapperImpl();
        context.setAttribute("productMapper", productMapper);

        CategoryMapper categoryMapper = new CategoryMapperImpl();
        context.setAttribute("categoryMapper", categoryMapper);

        UserMapper userMapper = new UserMapperImpl();
        context.setAttribute("userMapper", userMapper);

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(jdbcTemplate, categoryMapper);
        context.setAttribute("categoryRepository", categoryRepository);

        ProductRepository productRepository = new ProductRepositoryImpl(jdbcTemplate,categoryRepository, productMapper);
        context.setAttribute("productRepository", productRepository);

        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, userMapper);
        context.setAttribute("userRepository", userRepository);

        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
        context.setAttribute("categoryService", categoryService);

        ProductService productService = new ProductServiceImpl(productRepository, productMapper);
        context.setAttribute("productService", productService);

        UserService userService = new UserServiceImpl(userRepository, userMapper);
        context.setAttribute("userService", userService);

        List<String> PROTECTED_URIS = List.of(PropertyReader.getProperty("PROTECTED_URIS").split(","));
        context.setAttribute("PROTECTED_URIS", PROTECTED_URIS);
        List<String> PROTECTED_ADMIN_URIS = List.of(PropertyReader.getProperty("PROTECTED_ADMIN_URIS").split(","));
        context.setAttribute("PROTECTED_ADMIN_URIS", PROTECTED_ADMIN_URIS);
        List<String> NOTAUTH_URIS = List.of(PropertyReader.getProperty("NOTAUTH_URIS").split(","));
        context.setAttribute("NOTAUTH_URIS", NOTAUTH_URIS);


        String PROTECTED_REDIRECT = PropertyReader.getProperty("PROTECTED_REDIRECT");
        context.setAttribute("PROTECTED_REDIRECT", PROTECTED_REDIRECT);
        String PROTECTED_ADMIN_REDIRECT = PropertyReader.getProperty("PROTECTED_ADMIN_REDIRECT");
        context.setAttribute("PROTECTED_ADMIN_REDIRECT", PROTECTED_ADMIN_REDIRECT);
        String NOTAUTH_REDIRECT = PropertyReader.getProperty("NOTAUTH_REDIRECT");
        context.setAttribute("NOTAUTH_REDIRECT", NOTAUTH_REDIRECT);

        String AUTHORIZATION = PropertyReader.getProperty("AUTHORIZATION");
        context.setAttribute("AUTHORIZATION", AUTHORIZATION);

        String IS_ADMIN = PropertyReader.getProperty("IS_ADMIN");
        context.setAttribute("IS_ADMIN", IS_ADMIN);

        String SECRET_KEY = PropertyReader.getProperty("SECRET_KEY");
        context.setAttribute("SECRET_KEY", SECRET_KEY);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("-=-=-=-=-=-=-=-=- CONTEXT DESTROYED -==-=-=-=-=-=-=-=-=");
    }

    private DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(PropertyReader.getProperty("DB_URL"));
        dataSource.setUser(PropertyReader.getProperty("DB_USER"));
        dataSource.setPassword(PropertyReader.getProperty("DB_PASSWORD"));
        return dataSource;
    }

}
