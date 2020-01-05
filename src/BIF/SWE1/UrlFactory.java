package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;

public class UrlFactory {

    public enum UrlType {
        ONLY_PATH,
        NO_QUERY,
        NO_FRAGMENT,
        FULL_URL,
        MAIN_PAGE,
        UNDEFINED
    }

    public UrlType urlType;

    // results of split at "#"
    private String url_without_fragment;
    private String fragment;
    // results of split at "?"
    private String path_full;
    private String query;
    // results of parsing full_path
    private String path;
    private String filename;
    private String extension;
    private String file;
    // results of parsing segments
    private String[] segments;
    // result of parsing parameters
    private HashMap<String, String> parameters;

    public Url getWebUrl(String url_raw) {

        if(url_raw == null || url_raw.isEmpty()) {
            return new WebUrl();
        }

        // print raw url
        System.out.println("PARSING URL " + url_raw);
        // detect type of URL
        UrlType type = this.detectUrlType(url_raw);
        System.out.print("Url is of type ");
        switch(type) {
            case ONLY_PATH:
                this.urlType = UrlType.ONLY_PATH;
                System.out.print(UrlType.ONLY_PATH + "\n");

                this.path_full = url_raw;
                this.parseFullPath(this.path_full);
                this.parseSegments();

                return new WebUrl(url_raw, this.path_full, this.filename, this.extension, null, null, this.segments);

            case NO_QUERY:
                this.urlType = UrlType.NO_QUERY;
                System.out.print(UrlType.NO_QUERY + "\n");

                this.splitAtFragmentDelimiter(url_raw);
                this.path_full = this.url_without_fragment;
                this.parseFullPath(this.path_full);
                this.parseSegments();

                return new WebUrl(url_raw, this.path_full, this.filename, this.extension, null, this.fragment, this.segments);

            case NO_FRAGMENT:
                this.urlType = UrlType.NO_FRAGMENT;
                System.out.print(UrlType.NO_FRAGMENT + "\n");

                this.splitAtQueryDelimiter(url_raw);
                this.parseFullPath(this.path_full);
                this.parseSegments();
                this.parseParameters(this.query);

                return new WebUrl(url_raw, this.path_full, this.filename, this.extension, this.parameters, null, this.segments);

            case FULL_URL:
                this.urlType = UrlType.FULL_URL;
                System.out.print(UrlType.FULL_URL + "\n");

                this.splitAtFragmentDelimiter(url_raw);
                this.splitAtQueryDelimiter(this.url_without_fragment);
                this.parseFullPath(this.path_full);
                this.parseSegments();
                this.parseParameters(this.query);

                return new WebUrl(url_raw, this.path_full, this.filename, this.extension, this.parameters, this.fragment, this.segments);
            case MAIN_PAGE:
                this.urlType = UrlType.MAIN_PAGE;
                System.out.println(UrlType.MAIN_PAGE + "\n");

                this.path_full = "/index.html";
                this.parseFullPath(this.path_full);
                this.parseSegments();

                return new WebUrl(url_raw, this.path_full, this.filename, this.extension, null, null, this.segments);
            default:
                this.urlType = UrlType.UNDEFINED;
                System.out.println("Error detecting URL type");
                break;
        }

        return new WebUrl();
    }

    /**
     * important:
     * first split at fragment delimiter, THEN at query delimiter
     * e.g:
     * /path/file.jpg?x=1&y=2#foo
     * first split
     * /path/file.jpg AND x=1&y=2#foo
     * second split
     * /path/file.jpg AND x=1&y=2 AND foo
     */

    // detect the type of URL, so the parsing can be done correctly
    private UrlType detectUrlType(String url) {
        if(url.length() == 1) {
            return UrlType.MAIN_PAGE;
        }

        if(!url.contains("?") && !url.contains("#")) {
            return UrlType.ONLY_PATH;
        }
        else if(!url.contains("?")) {
            return UrlType.NO_QUERY;
        }
        else if(!url.contains("#")) {
            return UrlType.NO_FRAGMENT;
        }
        else return UrlType.FULL_URL;
    }

    private void splitAtFragmentDelimiter(String url) {
        String[] result = url.split("#");
        this.url_without_fragment = result[0];
        this.fragment = result[1];

        System.out.println("without fragment: " + this.url_without_fragment);
        System.out.println("fragment: " + this.fragment);
    }

    private void splitAtQueryDelimiter(String url) {
        String[] result = url.split("\\?");
        this.path_full = result[0];
        this.query = result[1];

        System.out.println("full path: " + this.path_full);
        System.out.println("query: " + this.query);
    }

    private void parseFullPath(String path_only) {
        this.path = path_only.substring(0, path_only.lastIndexOf("/"));

        String substrResult;
        String[] splitResult;

        this.file = substrResult = path_only.substring(path_only.lastIndexOf("/") + 1);
        splitResult = substrResult.split("\\.");
        this.filename = splitResult[0];
        if(splitResult.length > 1) {
            this.extension = splitResult[1];
        }

        System.out.println("path: " + this.path);
        System.out.println("file: " + this.file);
        System.out.println("filename: " + this.filename);
        System.out.println("extension: " + this.extension);
    }

    private void parseSegments() {
        this.segments = this.path_full.substring(1).split("/");

        System.out.println("segments:");
        for(String segment : this.segments) {
            System.out.println(segment);
        }
    }

    private void parseParameters(String query) {
        HashMap<String, String> parameters = new HashMap<>();
        String[] result;

        for(String param : query.split("&")) {
            result = param.split("=");
            String key = result[0];
            String value = result[1];
            parameters.put(key, value);

            System.out.println("key: " + key + ", value: " + value);
        }

        this.parameters = parameters;
    }

}
