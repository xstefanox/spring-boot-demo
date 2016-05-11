package xstefanox;

import org.springframework.stereotype.Service;

/**
 * Store the ID of the current request.
 */
@Service
public class RequestIdProvider {

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    public String get() {
        return REQUEST_ID.get();
    }

    public void set(String requestId) {
        REQUEST_ID.set(requestId);
    }

    public void unset() {
        REQUEST_ID.remove();
    }
}
