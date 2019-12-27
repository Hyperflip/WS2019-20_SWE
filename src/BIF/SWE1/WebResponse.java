package BIF.SWE1;

import BIF.SWE1.interfaces.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class WebResponse implements Response {

    private int statusCode;

    private static Map<Integer, String> validStatusCodes;
    static {
        validStatusCodes = new HashMap<>();
        validStatusCodes.put(200, "OK");
        validStatusCodes.put(404, "Not Found");
        validStatusCodes.put(500, "Internal Server Error");
    }

    WebResponse() {
        System.out.println("constructing WebResponse...");
        this.statusCode = -1;
    }

    @Override
    public Map<String, String> getHeaders() {
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
    public void setContentType(String contentType) {

    }

    @Override
    public int getStatusCode() {
        if(this.statusCode == -1) {
            throw new IllegalArgumentException("No status code set");
        }
        else return this.statusCode;
    }

    @Override
    public void setStatusCode(int status) {
        if(!validStatusCodes.containsKey(status)) {
            System.out.println("Not setting invalid status code");
            return;
        }

        System.out.println("Setting status code " + status);
        this.statusCode = status;
    }

    @Override
    public String getStatus() {
        int status = this.getStatusCode();
        return String.valueOf(status) + " " + validStatusCodes.get(status);
    }

    @Override
    public void addHeader(String header, String value) {

    }

    @Override
    public String getServerHeader() {
        return null;
    }

    @Override
    public void setServerHeader(String server) {

    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void setContent(byte[] content) {

    }

    @Override
    public void setContent(InputStream stream) {

    }

    @Override
    public void send(OutputStream network) {

    }
}
