package ru.itis.lessonservlet;

import jakarta.servlet.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import ru.itis.lessonservlet.config.AppConfig;
import ru.itis.lessonservlet.config.WebConfig;
import ru.itis.lessonservlet.filter.AuthFilter;
import ru.itis.lessonservlet.filter.LoggingFilter;

import java.util.EnumSet;

public class ShopAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Корневой контекст (общие бины: сервисы, репозитории, JPA)
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // Web-контекст (MVC: контроллеры, view resolver)
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);

        // Диспетчер сервлета
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // Регистрируем фильтры через DelegatingFilterProxy
        servletContext.addFilter("loggingFilter", new DelegatingFilterProxy("loggingFilter"))
                .addMappingForUrlPatterns(null, false, "/*");

        servletContext.addFilter("authFilter", new DelegatingFilterProxy("authFilter"))
                .addMappingForUrlPatterns(null, false, "/*");

    }
}
