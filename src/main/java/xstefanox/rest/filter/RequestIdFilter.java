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
import org.springframework.util.IdGenerator;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Automatically set a random generated ID for the current request.
 */
@Service
public class RequestIdFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdFilter.class);

    public static final String REQUEST_ID = "REQUEST_ID";
    private static final String MDC_REQUEST_ID_VALUE_FORMAT = "%s=%s";

    private final IdGenerator idGenerator;

    @Autowired
    public RequestIdFilter(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            UUID requestId = idGenerator.generateId();

            // generate the id of the current request
            request.setAttribute(REQUEST_ID, requestId);

            // save the request id in the mdc, so it can be logged
            MDC.put(REQUEST_ID, String.format(MDC_REQUEST_ID_VALUE_FORMAT, REQUEST_ID, requestId));

            filterChain.doFilter(request, response);

        } catch (Throwable t) {

            // log every exception that has not been intercepted by any exception handler
            LOGGER.error("unhandled exception {} : {}", t.getClass(), t.getMessage());

        } finally {

            // clean the current request id from the thread...
            request.removeAttribute(REQUEST_ID);

            // ...and from the mdc
            MDC.remove(REQUEST_ID);
        }
    }
}
