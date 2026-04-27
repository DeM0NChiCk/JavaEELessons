package ru.itis.semestr;

import jakarta.servlet.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import ru.itis.semestr.config.AppConfig;
import ru.itis.semestr.config.WebConfig;

public class ShopAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter(
                "encodingFilter", new CharacterEncodingFilter("UTF-8", true)
        );
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic securityFilter = servletContext.addFilter(
                "springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain")
        );
        securityFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic loggingFilter = servletContext.addFilter(
                "loggingFilter", new DelegatingFilterProxy("loggingFilter")
        );
        loggingFilter.addMappingForUrlPatterns(null, false, "/*");

    }
}
