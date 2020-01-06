package BIF.SWE1;

import BIF.SWE1.enums.MethodType;
import BIF.SWE1.interfaces.Request;

import java.io.*;

public class RequestFactory {

    WebRequest request;

    public Request getWebRequest(InputStream stream) {
        this.request = new WebRequest();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        System.out.println("Parsing Request Lines...");
        String line;

        // Parsing headers for GET and POST
        try {
            while (reader.ready() && !(line = reader.readLine()).equals("")) {
                this.parseRequestLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Optional if POST
        if(MethodType.valueOf(this.request.getMethod().toUpperCase()) == MethodType.POST){
            System.out.println("Parsing Post Content...");
            try {
                this.request.setContent(this.parsePostContent(reader));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.request;
    }

    public void parseRequestLine(String line){
        System.out.println("Line: >" + line + "<");
        if(this.request.getMethodType() == MethodType.INVALID){
            String[] result = line.split("\\s");
            if(result.length == 3){
                this.request.setMethod(result[0]);
                this.request.setUrl(result[1]);
                this.request.setProtocol(result[2]);
            }
        }else{
            String[] result = line.split(":",2);
            if(result.length == 2){
                this.request.addHeader(result[0],result[1].trim());
            }
        }
    }

    private String parsePostContent(BufferedReader reader) throws IOException {
        // TODO Bob der Baubytetser muss hier dann noch builden
        StringBuilder builder = new StringBuilder();
        int i = 0;

        // ready() checks if something is ready to be read (works with nextChar and nChars fields)
        while (reader.ready() && (i = reader.read()) != -1) {
            builder.append((char) i);
        }

        return builder.toString();
    }
}