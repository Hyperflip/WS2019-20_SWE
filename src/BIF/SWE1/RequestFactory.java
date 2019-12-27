package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RequestFactory {

    private List<String> validMethods = Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE");

    // BufferedReader from request InputStream
    private BufferedReader reader;
    // list of request lines
    private List<String> lines = new ArrayList<String>();
    // parsed information from first line
    private String method;
    private Url url;
    private String version;
    // check validity after parsing first line + validating method
    private boolean valid;
    // parsing the header lines
    private int headerCount;
    private Map<String, String> headers = new HashMap<>();
    private String userAgent;


    public WebRequest getWebRequest(InputStream is) {

        // create BufferedReader from InputStream
        this.getBufferedReader(is);

        // parse and store all lines of the request
        try {
            this.parseRequestLines(this.reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // parse first line and check validity of method
        if((this.valid = this.parseFirstLine())) this.valid = validateMethod();

        // if invalid, return here without headers, etc.
        if(!this.valid) {
            return new WebRequest(false, this.method, this.url, this.version);
        }

        // parse headers and store User-Agent in separate variable
        this.parseHeaders();

        return new WebRequest(this.valid, this.method, this.url, this.version, this.headers, this.headerCount, this.userAgent);
    }

    private void getBufferedReader(InputStream is) {
        InputStreamReader isReader = new InputStreamReader(is);
        this.reader = new BufferedReader(isReader);
    }

    private void parseRequestLines(BufferedReader reader) throws IOException {
        String line;

        System.out.println("PARSING request lines");
        for(int i = 0; (line = reader.readLine()) != null; i++) {
            this.lines.add(line);
            System.out.println(i + ": " + line);
        }
    }

    private boolean parseFirstLine() {
        String[] content = this.lines.get(0).split(" ");
        if(content.length != 3) return false;

        String urlString;
        if((urlString = content[1]).isEmpty()) return false;

        if((this.method = content[0]).isEmpty()) return false;
        if((this.version = content[2]).isEmpty()) return false;

        this.url = new UrlFactory().getWebUrl(urlString);

        System.out.println("PARSING first line");
        System.out.println("method: " + this.method);
        System.out.println("url: " + this.url.getRawUrl());
        System.out.println("version: " + this.version);

        return true;
    }

    private boolean validateMethod() {
        return this.validMethods.contains(this.method.toUpperCase());
    }

    private void parseHeaders() {
        System.out.println("PARSING headers");

        String line, key, value;
        for(int i = 1; !(line = this.lines.get(i).trim()).isEmpty(); i++) {
            this.headerCount++;

            key = line.substring(0, line.indexOf(":"));
            value = line.substring(line.indexOf(":") + 2);

            if(key.equals("User-Agent")) this.userAgent = value;

            this.headers.put(key.toLowerCase(), value);
            System.out.println(i + ": key = " + key + ", value = " + value);
        }
    }
}
