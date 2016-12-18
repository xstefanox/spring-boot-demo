package xstefanox.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Store the ID of the current request.
 */
@Service
public final class RequestIdProvider {

    /**
     * The name of the request attribute that holds the request ID in the
     * {@link org.springframework.web.servlet.support.RequestContext}.
     */
    public static final String REQUEST_ATTRIBUTE_NAME = "REQUEST_ID";

    /**
     * Get the request ID.
     *
     * @return The request ID
     */
    public String get() {
        return (String) RequestContextHolder.currentRequestAttributes()
                .getAttribute(REQUEST_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Set the request ID.
     *
     * @param requestId The request ID
     */
    public void set(final String requestId) {
        RequestContextHolder
                .currentRequestAttributes()
                .setAttribute(REQUEST_ATTRIBUTE_NAME, requestId, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Remove the currently set request ID, if any.
     */
    public void unset() {
        RequestContextHolder
                .currentRequestAttributes()
                .removeAttribute(REQUEST_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
    }
}
