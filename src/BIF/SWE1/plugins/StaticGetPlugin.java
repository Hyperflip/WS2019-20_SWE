package BIF.SWE1.plugins;

import BIF.SWE1.Server;
import BIF.SWE1.WebResponse;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StaticGetPlugin implements Plugin {

    /*
    this plugin only handles GET requests without parameters or fragments in the URL
    */

    private static List<String> imgExtensions = Arrays.asList("ico", "jpg", "jpeg", "gif", "png", "tif");

    private boolean checkIfImageFile(String ext) {
        return imgExtensions.contains(ext);
    }

    private byte[] getContentAsByteArray(String path) throws IOException {
        Path fileLocation = Paths.get(path);
        return Files.readAllBytes(fileLocation);
    }

    @Override
    public float canHandle(Request req) {
        // exit condition
        if(!req.isValid()) return 0;

        Url url = req.getUrl();
        String method = req.getMethod().toUpperCase();

        // check method
        if(!method.equals("GET")) return 0;

        float score = 0.9f;

        // scoring based on fragment
        if(url.getFragment() != null) score = 0.5f;

        // scoring based on query
        if(url.getParameterCount() != 0) score = 0.1f;

        return score;
    }

    @Override
    public Response handle(Request req) {
        String path = req.getUrl().getPath();
        Response resp = new WebResponse();

        byte[] content;
        try {
            String pathAbs = System.getProperty("user.dir") + File.separator + Server.siteDirName + path;
            content = this.getContentAsByteArray(pathAbs);

            resp.setStatusCode(200);
            resp.setContent(content);

            String ext = req.getUrl().getExtension();
            String type;

            if(this.checkIfImageFile(ext))
                type = "image/";
            else
                type = "text/";

            type += ext;

            resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
            resp.addHeader("Content-Type", type);
            resp.addHeader("Connection", "Closed");

            return resp;

        } catch (IOException e) {

            e.printStackTrace();
            return WebResponse.constructErrorResponse(404);

        }
    }
}
