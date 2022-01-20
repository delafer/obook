package net.j7.ebook.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.*;

/**
 * Filter for enabling CORS support.
 */
@Component
public class CorsFilter extends OncePerRequestFilter implements Filter {

    private String allow;

    public CorsFilter(String allow) {
		this.allow = allow;
    }

    public CorsFilter() {
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
        response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Cache-Control");
        response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.addHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.addIntHeader(ACCESS_CONTROL_MAX_AGE, 3600);
        filterChain.doFilter(request, response);
    }
}