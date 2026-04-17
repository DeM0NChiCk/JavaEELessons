package ru.itis.lessonservlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component("authFilter")
public class AuthFilter implements Filter {

    private final List<String> PROTECTED_URIS;
    private final List<String> NOTAUTH_URIS;
    private final List<String> PROTECTED_ADMIN_URIS;

    private final String PROTECTED_REDIRECT;
    private final String PROTECTED_ADMIN_REDIRECT;
    private final String NOTAUTH_REDIRECT;

    private final String AUTHORIZATION;
    private final String IS_ADMIN;

    public AuthFilter(@Qualifier("PROTECTED_URIS") List<String> PROTECTED_URIS, @Qualifier("NOTAUTH_URIS") List<String> NOTAUTH_URIS, @Qualifier("PROTECTED_ADMIN_URIS") List<String> PROTECTED_ADMIN_URIS, @Qualifier("PROTECTED_REDIRECT") String PROTECTED_REDIRECT, @Qualifier("PROTECTED_ADMIN_REDIRECT") String PROTECTED_ADMIN_REDIRECT, @Qualifier("NOTAUTH_REDIRECT") String NOTAUTH_REDIRECT, @Qualifier("AUTHORIZATION") String AUTHORIZATION, @Qualifier("IS_ADMIN") String IS_ADMIN) {
        this.PROTECTED_URIS = PROTECTED_URIS;
        this.NOTAUTH_URIS = NOTAUTH_URIS;
        this.PROTECTED_ADMIN_URIS = PROTECTED_ADMIN_URIS;
        this.PROTECTED_REDIRECT = PROTECTED_REDIRECT;
        this.PROTECTED_ADMIN_REDIRECT = PROTECTED_ADMIN_REDIRECT;
        this.NOTAUTH_REDIRECT = NOTAUTH_REDIRECT;
        this.AUTHORIZATION = AUTHORIZATION;
        this.IS_ADMIN = IS_ADMIN;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if (isUserAuth(request)) {
            if (isNotAuthUri(uri)) {
                response.sendRedirect(NOTAUTH_REDIRECT);
            } else if (isProtectedAdminUri(uri) && !isUserAdmin(request)) {
                response.sendRedirect(PROTECTED_ADMIN_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

        } else {
            if (isProtectedUri(uri) || isProtectedAdminUri(uri)) {
                response.sendRedirect(PROTECTED_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean isProtectedUri(String uri) {
        return PROTECTED_URIS.contains(uri);
    }

    private boolean isProtectedAdminUri(String uri) {
        return PROTECTED_ADMIN_URIS.contains(uri);
    }

    private boolean isNotAuthUri(String uri) {
        return NOTAUTH_URIS.contains(uri);
    }

    private boolean isUserAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Boolean flag = (Boolean) session.getAttribute(AUTHORIZATION);
        return flag != null && flag;
    }

    private boolean isUserAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Boolean flag = (Boolean) session.getAttribute(IS_ADMIN);
        return flag != null && flag;
    }
}
