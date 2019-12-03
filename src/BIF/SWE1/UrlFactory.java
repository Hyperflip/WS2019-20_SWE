package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

public class UrlFactory {

    private String path;
    private String filename;
    private String extension;

    public Url getWebUrl(String url) {

        if(url == null) {
            return new WebUrl();
        }

        return new WebUrl(url, parsePath(url), parseFilename(url), parseExtension(url), parseParameters(url), parseFragment(url), parseSegments());
    }

    private String parsePath(String url) {
        String path;

        if(url == null) return "";

        path = url.substring(0, url.lastIndexOf("/"));

        this.path = path;

        return path;
        /*
        example: "https://moodle.technikum-wien.at/course/view.php?id=10177"
        returns  "/moodle.technikum-wien.at/course/"
         */
    }

    private String parseFilename(String url) {

        String filename;

        if(url == null) return "";

        filename = url.substring(url.lastIndexOf('/'), url.indexOf('.'));
        this.filename = filename;

        return filename;
    }

    private String parseExtension(String url) {

        String extension;

        if(url == null) return "";

        extension = "." + url.split("\\.")[1].split("\\?")[0];
        // remove fragment if it exists
        if(extension.contains("#")) {
            extension = extension.substring(0, extension.indexOf("#"));
        }

        this.extension = extension;

        return extension;
        /*
        example string:                 "/test.jpg#foo?x=y"
        1st split:                      "/test" + "jpg#foo?x=y"
        2nd split with
        result[1] of 1st split:         "jpg#foo" + "x=y"
        return result[0] of 2nd split WITHOUT #foo:  "jpg"
         */
    }

    private Map<String, String> parseParameters(String url) {

        HashMap<String, String> parameters = new HashMap<>();

        if(!url.contains("?")) return parameters;

        // get part of url containing parameter strings
        String subUrl = url.substring(url.lastIndexOf('?') + 1);

        /* for each parameter string that can be split off, do ...
        (if there is only 1 parameter, hence no "&" delimiting multiple
        parameter strings, do it once with the only parameter string ...) */
        for(String param : subUrl.split("&")) {
            /* split the param string at "=" and put key-value pair
            (at pos [0] and [1] of the split result) into the Map */
            parameters.put(param.split("=")[0], param.split("=")[1]);
        }

        return parameters;
    }

    private String parseFragment(String url) {
        String fragment = url.substring(url.indexOf("#") + 1);

        return fragment;
    }

    private String[] parseSegments() {
        String[] segments;
        // concat to full path
        String fullPath = this.path + this.filename + this.extension;
        // remove first / to avoid split problems
        fullPath = fullPath.substring(1);
        // split into array
        segments = fullPath.split("/");

        return segments;
    }

}
