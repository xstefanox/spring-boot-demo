package xstefanox.rest.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class ByteArrayServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream delegate;

    public ByteArrayServletInputStream(ByteArrayInputStream delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean isFinished() {
        return delegate.available() <= 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    @Override
    public void close() throws IOException {
        super.close();
        delegate.close();
    }
}
