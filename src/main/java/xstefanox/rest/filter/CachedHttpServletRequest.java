package xstefanox.rest.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

/**
 * A {@link HttpServletRequest} that transparently caches a request body.
 */
public class CachedHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] bytes;

    public CachedHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (bytes != null) {
            return new ByteArrayServletInputStream(new ByteArrayInputStream(bytes));
        } else {
            return super.getInputStream();
        }
    }

    public void cacheBody() {

        if (bytes == null) {
            try {
                bytes = StreamUtils.copyToByteArray(super.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
