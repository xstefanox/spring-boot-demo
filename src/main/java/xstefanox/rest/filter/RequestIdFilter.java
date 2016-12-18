package xstefanox.rest.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import xstefanox.rest.RequestIdProvider;

/**
 * Automatically set a random generated ID for the current request.
 */
@Service
public class RequestIdFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdFilter.class);

    public static final String MDC_REQUEST_ID_KEY = "REQUEST_ID";
    public static final String MDC_REQUEST_ID_VALUE_FORMAT = "%s=%s";

    private RequestIdProvider requestIdProvider;

    @Autowired
    public RequestIdFilter(RequestIdProvider requestIdProvider) {
        this.requestIdProvider = requestIdProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            // generate the id of the current request
            requestIdProvider.set(UUID.randomUUID().toString());

            // save the request id in the mdc, so it can be logged
            MDC.put(MDC_REQUEST_ID_KEY, String.format(MDC_REQUEST_ID_VALUE_FORMAT, MDC_REQUEST_ID_KEY, requestIdProvider.get()));

            filterChain.doFilter(request, response);

        } catch (Throwable t) {

            // log every exception that has not been intercepted by any exception handler
            LOGGER.error("unhandled exception {} : {}", t.getClass(), t.getMessage());

        } finally {

            // clean the current request id from the thread...
            requestIdProvider.unset();

            // ...and from the mdc
            MDC.remove(MDC_REQUEST_ID_KEY);
        }
    }
}
