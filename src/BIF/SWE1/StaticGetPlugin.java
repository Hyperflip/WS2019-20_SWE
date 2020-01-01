package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StaticGetPlugin implements Plugin {

    /*
    this plugin only handles GET requests without parameters or fragments in the URL
    */

    public static Response constructErrorResponse(int code) {
        Response resp = new WebResponse();
        String content = "<html><body><h1>" +
                String.valueOf(code) + " " + WebResponse.validStatusCodes.get(code) +
                "</h1></body></html>";

        resp.setStatusCode(code);
        resp.setContent(content);
        resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
        resp.addHeader("Content-Type", "text/html");
        resp.addHeader("Connection", "Closed");

        return resp;
    }

    @Override
    public float canHandle(Request req) {
        if(req.getMethod().toUpperCase().equals("GET") && req.getUrl().getParameterCount() == 0 && req.getUrl().getFragment() == null)
            return 1;
        else
            return 0;
    }

    @Override
    public Response handle(Request req) {
        ResourceCollector rc = new ResourceCollector(req.getUrl().getPath());
        Response resp = new WebResponse();

        try {
            rc.getFileReader();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            // return error code 404 response
            return constructErrorResponse(404);
        }

        try {
            String content = rc.getContentAsString();
            resp.setStatusCode(200);
            resp.setContent(content);

            // TODO: detect extension properly and set content-type accordingly

            String ext = req.getUrl().getExtension();
            String type = "text/" + ext;

            resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
            resp.addHeader("Content-Type", type);
            resp.addHeader("Connection", "Closed");

            return resp;

        } catch (IOException e) {
            e.printStackTrace();

            // return error code 500 response
            return constructErrorResponse(500);
        }
    }
}
