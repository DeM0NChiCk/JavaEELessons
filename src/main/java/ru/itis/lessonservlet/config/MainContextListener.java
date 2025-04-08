package ru.itis.lessonservlet.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.mapper.OrderMapper;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.mapper.UserMapper;
import ru.itis.lessonservlet.mapper.impl.CategoryMapperImpl;
import ru.itis.lessonservlet.mapper.impl.OrderMapperImpl;
import ru.itis.lessonservlet.mapper.impl.ProductMapperImpl;
import ru.itis.lessonservlet.mapper.impl.UserMapperImpl;
import ru.itis.lessonservlet.repository.*;
import ru.itis.lessonservlet.repository.impl.*;
import ru.itis.lessonservlet.service.*;
import ru.itis.lessonservlet.service.impl.*;
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

        OrderMapper orderMapper = new OrderMapperImpl();
        context.setAttribute("orderMapper", orderMapper);

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(jdbcTemplate, categoryMapper);
        context.setAttribute("categoryRepository", categoryRepository);

        FavouritesRepository favouritesRepository = new FavouritesRepositoryImpl(jdbcTemplate, productMapper, categoryRepository);
        context.setAttribute("favouritesRepository", favouritesRepository);

        ProductRepository productRepository = new ProductRepositoryImpl(jdbcTemplate,categoryRepository, productMapper, favouritesRepository);
        context.setAttribute("productRepository", productRepository);

        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, userMapper);
        context.setAttribute("userRepository", userRepository);

        OrdersRepository ordersRepository = new OrdersRepositoryImpl(jdbcTemplate, orderMapper);

        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
        context.setAttribute("categoryService", categoryService);

        ProductService productService = new ProductServiceImpl(productRepository, productMapper);
        context.setAttribute("productService", productService);

        UserService userService = new UserServiceImpl(userRepository, userMapper);
        context.setAttribute("userService", userService);

        FavouritesService favouritesService = new FavouritesServiceImpl(favouritesRepository, productMapper);
        context.setAttribute("favouritesService", favouritesService);

        OrdersService ordersService = new OrderServiceImpl(ordersRepository);
        context.setAttribute("ordersService", ordersService);

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
