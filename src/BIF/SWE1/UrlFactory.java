package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

public class UrlFactory {

    public Url getWebUrl(String url) {

        if(url == null) {
            return new WebUrl();
        }

        return new WebUrl(url, parsePath(url), parseFilename(url), parseExtension(url), parseParameters(url));
    }

    private String parsePath(String url) {

        if(url == null) return "";

        return url.substring(0, url.lastIndexOf('/'));
        /*
        example: "https://moodle.technikum-wien.at/course/view.php?id=10177"
        returns  "/moodle.technikum-wien.at/course/"
         */
    }

    private String parseFilename(String url) {

        if(url == null) return "";

        return url.substring(url.lastIndexOf('/'), url.indexOf('.'));
    }

    private String parseExtension(String url) {

        if(url == null) return "";

        return "." + url.split("\\.")[1].split("\\?")[0];
        /*
        example string:                 "/test.jpg?x=y"
        1st split:                      "/test" + "jpg?x=y"
        2nd split with
        result[1] of 1st split:         "jpg" + "x=y"
        return result[0] of 2nd split:  "jpg"
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

}
