package BIF.SWE1;

import BIF.SWE1.interfaces.Response;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * implementation of the Response interface
 * holds the data of an HTTP response
 */

public class WebResponse implements Response {

    public static Map<Integer, String> validStatusCodes;
    static {
        validStatusCodes = new HashMap<>();
        validStatusCodes.put(200, "OK");
        validStatusCodes.put(404, "Not Found");
        validStatusCodes.put(500, "Internal Server Error");
    }

    private String version;
    private int statusCode;

    private String serverHeader;
    private Map<String, String> headers;

    private String contentType;
    private String content;
    private ByteArrayOutputStream contentStream;

    public WebResponse() {
        System.out.println("constructing WebResponse...");

        this.version = "HTTP/1.1";
        this.statusCode = -1;
        this.headers = new HashMap<>();
        this.serverHeader = "BIF-SWE1-Server";
        this.content = null;
        this.contentStream = new ByteArrayOutputStream();
    }

    private void writeVersionToStream(ByteArrayOutputStream out) {
        // check validity
        if(!this.version.startsWith("HTTP/1.")) throw new IllegalArgumentException("invalid HTTP version");
        // write version
        for(int i = 0; i < this.version.length(); i++) {
            out.write(this.version.charAt(i));
        }
        // write space
        out.write(' ');
    }

    private void writeStatusCodeToStream(ByteArrayOutputStream out) {
        // get status code
        int code = this.getStatusCode();
        // get code and text as string
        String codeStr = String.valueOf(code);
        String statusText = String.valueOf(validStatusCodes.get(code));

        for(int i = 0; i < codeStr.length(); i++) {
            out.write(codeStr.charAt(i));
        }
        // write space
        out.write(' ');
        // write status text
        for(int i = 0; i < statusText.length(); i++) {
            out.write(statusText.charAt(i));
        }
        // write newline
        out.write('\r');
        out.write('\n');
    }

    private void writeDateHeaderToStream(ByteArrayOutputStream out) {
        ZonedDateTime nowGMT = ZonedDateTime.now(ZoneId.of("Europe/London"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.UK);
        String dateStr = "Date: " + dtf.format(nowGMT);

        for(int i = 0; i < dateStr.length(); i++) {
            out.write(dateStr.charAt(i));
        }
        out.write('\r');
        out.write('\n');
    }

    private void writeServerHeaderToStream(ByteArrayOutputStream out) {
        String header = "Server: " + this.serverHeader;
        // write server header
        for(int i = 0; i < header.length(); i++) {
            out.write(header.charAt(i));
        }
        // write newline
        out.write('\r');
        out.write('\n');
    }

    private void writeHeadersToStream(ByteArrayOutputStream out) {
        for(Map.Entry<String, String> key : this.headers.entrySet()) {
            String keyStr = key.getKey();
            String valueStr = key.getValue();
            // write key
            for(int i = 0; i < keyStr.length(); i++) {
                out.write(keyStr.charAt(i));
            }
            // write ": "
            out.write(':');
            out.write(' ');
            // write value
            for(int i = 0; i < valueStr.length(); i++) {
                out.write(valueStr.charAt(i));
            }
            // write newline
            out.write('\r');
            out.write('\n');
        }
    }

    private void writeContentToStream(ByteArrayOutputStream out) {
        try {
            out.write(this.contentStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ByteArrayOutputStream constructResponseStream() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.out.println("constructing HTTP response...\n");

        this.writeVersionToStream(out);
        this.writeStatusCodeToStream(out);
        this.writeDateHeaderToStream(out);
        this.writeServerHeaderToStream(out);
        this.writeHeadersToStream(out);
        out.write('\r');
        out.write('\n');    // header end line

        if(this.getContentType() != null && this.getContentLength() == 0) {
            throw new IOException("No content, yet content type was set");
        }
        else if(this.getContentLength() > 0) {
            this.writeContentToStream(out);
        }

        return out;
    }

    public static Response constructErrorResponse(int code) {
        Response resp = new WebResponse();
        String content = "<html><body><h1>" +
                String.valueOf(code) + " " + validStatusCodes.get(code) +
                "</h1></body></html>";

        resp.setStatusCode(code);
        resp.setContent(content);
        resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
        resp.addHeader("Content-Type", "text/html");
        resp.addHeader("Connection", "Closed");

        return resp;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getContentLength() {
        return this.contentStream.size();
    }

    @Override
    public String getContentType() {
        return this.headers.get("Content-Type");
    }

    @Override
    public void setContentType(String contentType) {
        this.headers.put("Content-Type", contentType);
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
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try {
            this.contentStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContent(byte[] content) {
        try {
            this.contentStream.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContent(InputStream stream) {

    }

    @Override
    public void send(OutputStream network) {
        System.out.println("sending response...");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            out = this.constructResponseStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // print for debugging purposes
        String outStr = out.toString();

        System.out.println(
                this.version + " " +
                this.statusCode + " " +
                validStatusCodes.get(this.statusCode) + "\r\n" +
                "Content-Type: " + this.getContentType()
        );

        // write to OutputStream
        byte[] arr = out.toByteArray();
        try {
            network.write(arr);
            network.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
