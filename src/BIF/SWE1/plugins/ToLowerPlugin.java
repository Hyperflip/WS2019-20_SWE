package BIF.SWE1.plugins;

import BIF.SWE1.WebResponse;
import BIF.SWE1.enums.MethodType;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class ToLowerPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        // exit condition
        if(!req.isValid()) return 0;

        if(MethodType.valueOf(req.getMethod()) == MethodType.POST && req.getUrl().getPath().equals("/tolower.html"))
            return 1;
        else
            return 0.1f;
    }

    @Override
    public Response handle(Request req) {
        String postData = req.getContentString();

        if (postData.isEmpty())
            postData = "Error: The ToLower Plugin needs to be accessed by a Http-Post-Request!";
        else
            postData = postData.substring(postData.indexOf("=") + 1).toLowerCase();

        Response resp = new WebResponse();

        resp.setStatusCode(200);
        resp.setContent(postData);

        resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
        resp.addHeader("Content-Type", "text/html");
        resp.addHeader("Connection", "Closed");

        return resp;
    }
}
