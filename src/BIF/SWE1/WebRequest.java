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
    private String httpVersion;
    private Map<String, String> headers = new HashMap<>();
    private int headerCount;


    WebRequest(boolean valid, String method, Url url, String httpVersion, Map<String, String> headers, int headerCount) {
        this.valid = valid;
        this.method = method;
        this.url = url;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.headerCount = headerCount;
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
