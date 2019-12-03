package BIF.SWE1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
* class which parses InputStream request information and
* constructs and then returns a WebRequest object
*/

public class RequestFactory {

    public WebRequest getWebRequest(InputStream is) {

        try {
            this.printRequestStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new WebRequest();
    }

    private void printRequestStream(InputStream is) throws IOException {
        InputStreamReader isReader = new InputStreamReader(is);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        System.out.println(sb.toString());
    }
}
