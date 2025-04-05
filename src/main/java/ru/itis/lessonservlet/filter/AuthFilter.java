package ru.itis.lessonservlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private List<String> PROTECTED_URIS;
    private List<String> NOTAUTH_URIS;
    private List<String> PROTECTED_ADMIN_URIS;

    private String PROTECTED_REDIRECT;
    private String PROTECTED_ADMIN_REDIRECT;
    private String NOTAUTH_REDIRECT;

    private String AUTHORIZATION;
    private String IS_ADMIN;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ServletContext context = filterConfig.getServletContext();

        PROTECTED_URIS = (List<String>) context.getAttribute("PROTECTED_URIS");
        NOTAUTH_URIS = (List<String>) context.getAttribute("NOTAUTH_URIS");
        PROTECTED_ADMIN_URIS = (List<String>) context.getAttribute("PROTECTED_ADMIN_URIS");

        PROTECTED_REDIRECT = (String) context.getAttribute("PROTECTED_REDIRECT");
        PROTECTED_ADMIN_REDIRECT = (String) context.getAttribute("PROTECTED_ADMIN_REDIRECT");
        NOTAUTH_REDIRECT = (String) context.getAttribute("NOTAUTH_REDIRECT");
        AUTHORIZATION = (String) context.getAttribute("AUTHORIZATION");
        IS_ADMIN = (String) context.getAttribute("IS_ADMIN");
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
