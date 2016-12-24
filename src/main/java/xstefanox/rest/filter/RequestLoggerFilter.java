package xstefanox.rest.filter;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Log the client IP address for every request.
 */
@Service
public class RequestLoggerFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggerFilter.class);
    private static final String MDC_CLIENT_ADDRESS_VALUE_FORMAT = "%s=%s";

    public static final String CLIENT_ADDRESS = "CLIENT_ADDRESS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            // save the client address in the mdc, so it can be logged
            MDC.put(CLIENT_ADDRESS, String.format(MDC_CLIENT_ADDRESS_VALUE_FORMAT, CLIENT_ADDRESS, request.getRemoteAddr()));

            StringBuilder requestUri = new StringBuilder(request.getRequestURI());

            Optional.ofNullable(request.getQueryString())
                    .filter(queryString -> !queryString.isEmpty())
                    .ifPresent(queryString -> requestUri.append('?').append(queryString));

            LOGGER.info("incoming request {} {}", request.getMethod(), requestUri);

            filterChain.doFilter(request, response);

        } finally {

            LOGGER.info("response status {}", response.getStatus());

            // clean the mdc
            MDC.remove(CLIENT_ADDRESS);
        }
    }
}
