package ru.itis.lessonservlet.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.XmlWebApplicationContext;
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
        ServletContext servletContext = sce.getServletContext();
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:spring-context.xml");
        context.setServletContext(servletContext);
        context.refresh();
        servletContext.setAttribute("springContext", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("-=-=-=-=-=-=-=-=- CONTEXT DESTROYED -==-=-=-=-=-=-=-=-=");
    }

}
