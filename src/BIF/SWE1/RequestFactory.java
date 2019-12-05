package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
* class which parses InputStream request information and
* constructs and then returns a WebRequest object
*/

public class RequestFactory {

    private String method = "";
    private Url url = new UrlFactory().getWebUrl("");
    private String httpVersion = "";
    private Map<String, String> headers = new HashMap<>();
    private int headerCount = 0;
    /** getUserAgent() redundant here as it's stored in the Map
    rest to be implemented ... */

    public WebRequest getWebRequest(InputStream is) {

        try {
            parseRequestStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean valid = validate();

        return new WebRequest(valid, this.method, this.url, this.httpVersion, this.headers, this.headerCount);
    }

    private void parseRequestStream(InputStream is) throws IOException {
        // Creating an InputStreamReader object from InputStream
        InputStreamReader isReader = new InputStreamReader(is);
        // Creating a BufferedReader object from InputStreamReader
        BufferedReader reader = new BufferedReader(isReader);

        // read request line and pass to parser
        String requestLine = reader.readLine();

        if(requestLine.trim().isEmpty()) {
            throw new IOException("Request is empty");
        }

        parseRequestLine(requestLine);

        // read each header string and pass to parser
        String headerStr;
        while((headerStr = reader.readLine()) != null) {
            if(headerStr.trim().isEmpty()) break;
            parseHeaders(headerStr);
        }
    }

    private void parseRequestLine(String line) throws IOException {
        String[] contents = line.split(" ");

        if(contents.length != 3) {
            throw new IOException("Request is invalid (RequestLine incomplete)");
        }

        String urlString = contents[1];
        this.method = contents[0];
        this.url = new UrlFactory().getWebUrl(urlString);
        this.httpVersion = contents[2];
    }

    private void parseHeaders(String str) {
        String key = str.substring(0, str.indexOf(":"));
        String value = str.substring(str.indexOf(":") + 2);

        this.headers.put(key, value);
        this.headerCount++;
    }

    private boolean validate() {
        // TODO: validate HTTP request
        return false;
    }
}
