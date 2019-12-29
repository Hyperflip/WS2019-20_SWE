package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import org.junit.Test;

import java.io.*;

public class TestPlugin implements Plugin {

    private float score;
    private Url url;

    TestPlugin() {
        this.score = -1;
    }

    private float updateScoreBasedOnUrl(Url url) {
        /*
        based on UE3 unit tests:
        URLs with only 1 segment (only file) can't be handled
        UNLESS there are parameters
        (?)
        */

        // special case where url = "/"
        if(url.getPath().equals("/")) return 0.1f;

        // if there are more than 1 segments of path
        if(url.getSegments().length > 1) {
            return 1;
        }
        // else if parameters exist
        else if(url.getParameterCount() > 0) {
            return 0.5f;
        }
        else {
            return 0;
        }
    }

    private String getRequestedFilePath() {
        String workingDir = System.getProperty("user.dir");
        String filePath = "/test/main.html";

        if(this.score == 1) {
            filePath = this.url.getPath();
        }

        return workingDir + filePath;
    }

    private String getFileContentString(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public float canHandle(Request req) {

        System.out.println("determining score of testplugin...");
        this.url = req.getUrl();

        this.score = this.updateScoreBasedOnUrl(this.url);

        return this.score;
    }

    @Override
    public Response handle(Request req) {
        // TestPlugin returns a file, returns "special.html" if url = "/"

        // set score if if hasn't been yet
        if(this.score == -1) this.score = this.canHandle(req);

        // get content of file
        String content = this.getFileContentString(this.getRequestedFilePath());

        // create response with content
        Response resp = new WebResponse();
        resp.setStatusCode(200);
        resp.setContent(content);

        return resp;
    }
}
