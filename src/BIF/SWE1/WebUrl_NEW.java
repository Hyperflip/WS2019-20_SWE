package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

public class WebUrl_NEW implements Url {

    private String rawUrl;
    private String filename;
    private String path;
    private String extension;
    private Map<String, String> parameters;
    private String fragment;
    private String[] segments;

    public WebUrl_NEW() {
        this("", "", "", "", new HashMap<>(), "", new String[0]);
    }

    public WebUrl_NEW(String rawUrl, String path, String filename, String extension, Map<String, String> parameters, String fragment, String[] segments) {
        this.rawUrl = rawUrl;
        this.filename = filename;
        this.path = path;
        this.extension = extension;
        this.parameters = parameters;
        this.fragment = fragment;
        this.segments = segments;

        //this.printGetters();
    }

    private void printGetters() {
        System.out.println(
                "Url parsing:\n" +
                "getRawUrl: " + this.getRawUrl() + "\n" + "getPath: " + this.getPath() + "\n"
        );

        for(Map.Entry<String, String> entry : this.getParameter().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            System.out.println("key: " + key + "\n" + "value: " + value);
        }
    }

    @Override
    public String getRawUrl() {
        return rawUrl;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Map<String, String> getParameter() {
        return parameters;
    }

    @Override
    public int getParameterCount() {
        return parameters.size();
    }

    // purpose unknown
    @Override
    public String[] getSegments() {

        return this.segments;
    }

    @Override
    public String getFileName() {
        return filename;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    // purpose unknown
    @Override
    public String getFragment() {
        return fragment;
    }

}
