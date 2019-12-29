package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

public class WebUrl implements Url {

    private String rawUrl;
    private String filename;
    private String path;
    private String extension;
    private Map<String, String> parameters;
    private String fragment;
    private String[] segments;

    public WebUrl() {
        this("", "", "", "", new HashMap<>(), "", new String[0]);
    }

    public WebUrl(String rawUrl, String path, String filename, String extension, Map<String, String> parameters, String fragment, String[] segments) {
        this.rawUrl = rawUrl;
        this.filename = filename;
        this.path = path;
        this.extension = extension;
        this.parameters = parameters;
        this.fragment = fragment;
        this.segments = segments;

        //this.printGetters();
    }

    @Override
    public String getRawUrl() {
        return this.rawUrl;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Map<String, String> getParameter() {
        return this.parameters;
    }

    @Override
    public int getParameterCount() {
        if(this.parameters == null) return 0;
        return parameters.size();
    }

    // purpose unknown
    @Override
    public String[] getSegments() {

        return this.segments;
    }

    @Override
    public String getFileName() {
        return this.filename;
    }

    @Override
    public String getExtension() {
        return this.extension;
    }

    // purpose unknown
    @Override
    public String getFragment() {
        return this.fragment;
    }

}
