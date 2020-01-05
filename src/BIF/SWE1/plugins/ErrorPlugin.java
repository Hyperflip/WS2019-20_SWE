package BIF.SWE1.plugins;

import BIF.SWE1.WebResponse;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class ErrorPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        return 0.01f;
    }

    @Override
    public Response handle(Request req) {
        return WebResponse.constructErrorResponse(500);
    }
}
