package BIF.SWE1;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WebRequest implements Request {

    private boolean valid;
    private String method;
    private Url url;
    private String version;
    private int headerCount;
    private Map<String, String> headers = new HashMap<>();
    private String userAgent;

    // TODO: adjust constructor parameters once RequestFactory is finished

    // on invalid request, no header information is passed
    WebRequest(boolean valid, String method, Url url, String version) {
        this.valid = valid;
        this.method = method;
        this.url = url;
        this.version = version;
    }

    WebRequest(boolean valid, String method, Url url, String version, Map<String, String> headers, int headerCount, String userAgent) {
        this.valid = valid;
        this.method = method;
        this.url = url;
        this.version = version;
        this.headerCount = headerCount;
        this.headers = headers;
        this.userAgent = userAgent;
    }

    @Override
    public boolean isValid() {
        return this.valid;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public Url getUrl() {
        return this.url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getHeaderCount() {
        return this.headerCount;
    }

    @Override
    public String getUserAgent() {
        return null;
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public InputStream getContentStream() {
        return null;
    }

    @Override
    public String getContentString() {
        return null;
    }

    @Override
    public byte[] getContentBytes() {
        return new byte[0];
    }
}
