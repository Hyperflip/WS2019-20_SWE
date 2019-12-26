package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

public class UrlFactory_NEW {

    enum UrlType {
        ONLY_PATH,
        NO_QUERY,
        NO_FRAGMENT,
        FULL_URL
    }

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

    public Url getWebUrl(String url_raw) {

        if(url_raw == null || url_raw.isEmpty()) {
            return new WebUrl_NEW();
        }

        /* TODO: parse url and call WebUrl(_NEW) constructor according to each URL type
        finished for: UrlType.ONLY_PATH */

        // print raw url
        System.out.println(url_raw);
        // detect type of URL
        UrlType type = this.detectUrlType(url_raw);
        System.out.print("Url is of type ");
        switch(type) {
            case ONLY_PATH:
                System.out.print(UrlType.ONLY_PATH + "\n");

                this.path_full = url_raw;
                this.parseFullPath(url_raw);
                this.parseSegments();

                return new WebUrl_NEW(url_raw, this.path_full, this.filename, this.extension, new HashMap<String, String>(), "", this.segments);

            case NO_QUERY:
                System.out.print(UrlType.NO_QUERY + "\n");

                this.splitAtFragmentDelimiter(url_raw);
                this.path_full = this.url_without_fragment;
                this.parseFullPath(this.path_full);
                this.parseSegments();

                return new WebUrl_NEW(url_raw, this.path_full, this.filename, this.extension, new HashMap<String, String>(), this.fragment, this.segments);

            case NO_FRAGMENT:
                System.out.print(UrlType.NO_FRAGMENT + "\n");
                // TODO: splitting key + value
                this.splitAtQueryDelimiter(url_raw);
                this.parseFullPath(this.path_full);
                this.parseSegments();
                // this.parseParameters

                return new WebUrl_NEW(url_raw, this.path_full, this.filename, this.extension, new HashMap<String, String>(), "", this.segments);

            case FULL_URL:
                System.out.print(UrlType.FULL_URL + "\n");

                // TODO: (after splitting key + value) everything over here

                break;
            default:
                System.out.println("Error detecting URL type");
                break;
        }

        /*
        // do the parsing according to type of URL
        this.splitAtFragmentDelimiter(url_raw);
        this.splitAtQueryDelimiter(this.url_without_fragment);
        this.parseFullPath(this.path_full);
        */

        return new WebUrl();
    }

    // detect the type of URL, so the parsing can be done correctly
    private UrlType detectUrlType(String url) {
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

    private void parseFullPath(String url) {
        this.path = url.substring(0, url.lastIndexOf("/"));

        String substrResult;
        String[] splitResult;

        this.file = substrResult = url.substring(url.lastIndexOf("/"));
        splitResult = substrResult.split("\\.");
        this.filename = splitResult[0].substring(1);
        this.extension = splitResult[1];

        System.out.println("path: " + this.path);
        System.out.println("file: " + this.file);
        System.out.println("filename: " + this.filename);
        System.out.println("extension: " + this.extension);
    }

    private void parseSegments() {
        this.segments = this.path_full.split("/");
    }

}
