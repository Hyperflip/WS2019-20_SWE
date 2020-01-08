package BIF.SWE1.plugins;

import BIF.SWE1.WebResponse;
import BIF.SWE1.enums.MethodType;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

/**
 * plugin that handles POST requests on url "/tolower.html"
 * converts form text to lowercase
 */

public class ToLowerPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        // exit condition
        if(!req.isValid()) return 0;

        if(MethodType.valueOf(req.getMethod()) == MethodType.POST && req.getUrl().getPath().equals("/tolower.html"))
            return 1;
        else
            return 0;
    }

    @Override
    public Response handle(Request req) {
        String postData = req.getContentString();

        String result;
        if (postData.isEmpty())
            result = "Error: The ToLower Plugin needs to be accessed by a Http-Post-Request!";
        else
            result = postData.substring(postData.indexOf("=") + 1).toLowerCase();

        Response resp = new WebResponse();

        resp.setStatusCode(200);
        resp.setContent(result);

        resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
        resp.addHeader("Content-Type", "text/html");
        resp.addHeader("Connection", "Closed");

        return resp;
    }
}
