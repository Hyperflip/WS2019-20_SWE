package BIF.SWE1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ResourceCollector {

    // absolute path
    private String pathAbs;
    private FileReader fr;

    ResourceCollector(String path) {
        if(path.equals("/")) path = "/main.html";
        this.pathAbs = System.getProperty("user.dir") + "/http" + path;
    }

    public void getFileReader() throws FileNotFoundException {
        this.fr = new FileReader(this.pathAbs);
    }

    public String getContentAsString() throws IOException {
        StringBuilder sb = new StringBuilder();

        this.getFileReader();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        this.fr.close();

        return sb.toString();
    }

}