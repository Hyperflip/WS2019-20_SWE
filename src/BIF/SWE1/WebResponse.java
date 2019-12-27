package BIF.SWE1;

import BIF.SWE1.interfaces.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WebResponse implements Response {

    private static Map<Integer, String> validStatusCodes;
    static {
        validStatusCodes = new HashMap<>();
        validStatusCodes.put(200, "OK");
        validStatusCodes.put(404, "Not Found");
        validStatusCodes.put(500, "Internal Server Error");
    }

    private String version;
    private int statusCode;
    private Map<String, String> headers;
    private String contentType;
    private String serverHeader;
    private String content;
    private int contentLength;

    WebResponse() {
        System.out.println("constructing WebResponse...");

        this.version = "HTTP/1.1";
        this.statusCode = -1;
        this.headers = new HashMap<>();
        this.serverHeader = "BIF-SWE1-Server";
        this.content = null;
    }

    private void setContentLength() throws UnsupportedEncodingException {
        this.contentLength = this.content.getBytes("UTF-8").length;
    }

    private ByteArrayOutputStream constructResponseStream() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.out.println("constructing HTTP response...");

        // writing a valid HTTP response
        int i;

        // first line

        // write HTTP version
        for(i = 0; i < this.version.length(); i++) {
            out.write(this.version.charAt(i));
        }
        // write space
        out.write(' ');
        // write status code
        String status = String.valueOf(this.statusCode);
        for(i = 0; i < status.length(); i++) {
            out.write(status.charAt(i));
        }
        // write space
        out.write(' ');
        // write status text
        String statusText = validStatusCodes.get(this.statusCode);
        for(i = 0; i < statusText.length(); i++) {
            out.write(statusText.charAt(i));
        }
        // write newline
        out.write('\n');

        // write server header
        // key
        String keyHead = "Server: ";
        for(i = 0; i < keyHead.length(); i++) {
            out.write(keyHead.charAt(i));
        }
        // value
        for(i = 0; i < this.serverHeader.length(); i++) {
            out.write(this.serverHeader.charAt(i));
        }
        // write newline
        out.write('\n');

        // write header lines
        for(Map.Entry<String, String> key : this.headers.entrySet()) {
            String keyStr = key.getKey();
            String valueStr = key.getValue();

            // write key
            for(i = 0; i < keyStr.length(); i++) {
                out.write(keyStr.charAt(i));
            }
            // write ": "
            out.write(':');
            out.write(' ');
            // write value
            for(i = 0; i < valueStr.length(); i++) {
                out.write(valueStr.charAt(i));
            }
            // write newline
            out.write('\n');
        }

        // TODO: writing the rest :^)


        return out;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getContentLength() {
        if(this.content == null) return 0;

        return this.contentLength;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
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
        this.headers.put(header, value);
    }

    @Override
    public String getServerHeader() {
        return this.serverHeader;
    }

    @Override
    public void setServerHeader(String server) {
        this.serverHeader = server;
    }

    @Override
    public void setContent(String content) {
        this.content = content;

        try {
            this.setContentLength();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContent(byte[] content) {

    }

    @Override
    public void setContent(InputStream stream) {

    }

    @Override
    public void send(OutputStream network) {

        ByteArrayOutputStream out = this.constructResponseStream();

        String outStr = out.toString();
        System.out.println(outStr);

        byte[] arr = out.toByteArray();
        try {
            network.write(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
