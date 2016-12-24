package xstefanox.rest.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Wrap every request with an object that allows reading the request body multiple times.
 */
@Service
public class RequestWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(new CachedHttpServletRequest(request), response);
    }
}
