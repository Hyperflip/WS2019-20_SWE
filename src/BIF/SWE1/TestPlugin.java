package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;

import java.io.*;

public class TestPlugin implements Plugin {

    private float score;

    TestPlugin() {
        this.score = 1;
    }

    @Override
    public float canHandle(Request req) {
        /*
        based on UE3 unit tests:
        URLs with only 1 segment (only file) can't be handled
        UNLESS there are parameters
        (?)
        */
        System.out.println("determining score of testplugin...");

        Url url = req.getUrl();

        // if there are more than 1 segments
        if(url.getSegments().length > 1) {
            this.score = 1;
        }
        // else if parameters exist
        else if(url.getParameterCount() > 0) {
            this.score = 0.5f;
        }
        else {
            this.score = 0;
        }

        // special case where url = "/"
        if(url.getPath().equals("/")) this.score = 1;

        return this.score;
    }

    @Override
    public Response handle(Request req) {
        Response resp = new WebResponse();

        // get url
        Url url = req.getUrl();
        // get working directory
        String currentDir = System.getProperty("user.dir");

        // only open files when score == 1 (could be 1 segment url with parameters)
        if(this.score == 1) {
            // get path of file
            String filePath = currentDir + url.getPath();
            // special case where url = "/"
            filePath = currentDir + "/test/special.html";

            StringBuffer sb = new StringBuffer();

            try {
                FileReader fr = new FileReader(filePath);
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

            resp.setStatusCode(200);
            resp.setContent(sb.toString());
        }

        return resp;
    }
}
