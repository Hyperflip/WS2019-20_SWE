package BIF.SWE1;

import BIF.SWE1.enums.MethodType;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WebRequest implements Request {

    private MethodType method;
    private String protocol;
    private String host;
    private Url url;
    private Map<String, String> headers;
    private String content;

    public WebRequest(){
        this.method = MethodType.INVALID;
        this.headers = new HashMap<>();
    }

    @Override
    public boolean isValid() {
        return this.method != MethodType.INVALID;
    }

    @Override
    public String getMethod() {
        return this.method.name();
    }

    public MethodType getMethodType(){
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
        return this.headers.size();
    }

    @Override
    public String getUserAgent() {
        return this.headers.get("user-agent");
    }

    @Override
    public int getContentLength() {
        return Integer.parseInt(this.headers.get("content-length"));
    }

    @Override
    public String getContentType() {
        return this.headers.get("content-type");
    }

    @Override
    public InputStream getContentStream() {
        return new ByteArrayInputStream(this.content.getBytes());
    }

    @Override
    public String getContentString() {
        return this.content;
    }

    @Override
    public byte[] getContentBytes() {
        return this.content.getBytes();
    }

    public void addHeader(String key, String value){
        this.headers.put(key.toLowerCase(), value);
    }

    public void setMethod(String method){
        try{
            MethodType newMethod = MethodType.valueOf(method.toUpperCase());
            this.method = newMethod;
        }catch (IllegalArgumentException e){
            System.err.println("Method " + method + " is not a valid MethodType");
        }
    }

    public void setUrl(String url){
        this.url = new UrlFactory().getWebUrl(url);
    }

    public void setProtocol(String protocol){
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setContent(String content){
        this.content = content;
    }
}