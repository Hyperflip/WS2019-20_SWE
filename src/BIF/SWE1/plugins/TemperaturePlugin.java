package BIF.SWE1.plugins;

import BIF.SWE1.WebResponse;
import BIF.SWE1.enums.MethodType;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import org.json.JSONObject;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TemperaturePlugin implements Plugin {

    private JSONObject temperatureData;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN);

    public TemperaturePlugin() {
        super();
        System.out.println("Starting Temperature Plugin...");

        //this.generateData();

        new Thread(this::run).start();
    }

    private void run() {
        String jsonString = "";
        try {
            jsonString = new BufferedReader(new FileReader("tempValues.json")).lines().collect(Collectors.joining());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        temperatureData = new JSONObject(jsonString);

        String[] result = new TreeSet<String>(temperatureData.keySet()).last().split("-");

        ZonedDateTime date = ZonedDateTime.of(Integer.parseInt(result[0]),Integer.parseInt(result[1]),Integer.parseInt(result[2]),1,1,1,1,ZoneId.of("Europe/London"));

        for (int i = 0; true; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String dateStr = dtf.format(date.plusDays(i));

            double rand = Math.random() * 1000;
            double sine = Math.sin(System.currentTimeMillis() + rand);
            float temperature = (float) (Math.abs(sine) * 40);

            // Synchronized
            temperatureData.put(dateStr, temperature);
        }
    }

    private void generateData() {
        JSONObject object = new JSONObject();

        ZonedDateTime nowGMT = ZonedDateTime.now(ZoneId.of("Europe/London"));

        for (int i = 0; i < 10000; i++) {
            double rand = Math.random() * 1000;
            double sine = Math.sin(System.currentTimeMillis() + rand);
            float temperature = (float) (Math.abs(sine) * 40);

            System.out.print("Temperature: ");
            System.out.printf("%.2f", temperature);
            System.out.println(" °C");

            String dateStr = dtf.format(nowGMT.plusDays(i - 10000));

            object.put(dateStr, temperature);
        }

        try {
            Writer writer = new FileWriter("tempValues.json");
            object.write(writer,4,0);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildXML(String key, float temp) {
        return "<?xml version=\"1.0\"?>" +
                "<TemperatureEntry>" +
                "<Date>" + key + "</Date>" +
                "<Temp>" + String.format("%2.2f", temp) + "</Temp>" +
                "</TemperatureEntry>";
    }

    private String buildTempPage() {
        String content = "<html>" +
                "<head>" +
                "<link rel=\"shortcut icon\" type=\"image/jpg\" href=\"/favicon.ico?v=2\"/>" +
                "</head>" +
                "<body>" +
                "<table style=\"width:auto\">" +
                "<tr><th>Date</th><th>Temperature</th></tr>";

        // temperatureData will be null on unit test cases
        if(temperatureData != null) {
            for (String key : new TreeSet<String>(temperatureData.keySet())) {

                content += "<tr><td align=\"center\">" + key + "</td>" +
                        "<td align=\"center\">" + String.format("%05.2f", temperatureData.getFloat(key)) + "</td>" +
                        "</tr>";
            }
        }

        return content += "</table></body></html>";
    }

    @Override
    public float canHandle(Request req) {
        // exit condition
        if (!req.isValid()) return 0;

        if (MethodType.valueOf(req.getMethod()) == MethodType.GET && req.getUrl().getPath().startsWith("/GetTemperature"))
            return 1;

        return 0;
    }

    @Override
    public Response handle(Request req) {
        // GET /GetTemperature
        // Website mit allen Werten

        // GET /GetTemperature/2019/01/01
        // XML mit den Werten

        String urlStr = req.getUrl().getPath();

        String queryDate = "";

        try {
            queryDate = urlStr.substring(16);
        }
        catch(StringIndexOutOfBoundsException e) {
            System.out.println("no arguments passed. returning page");
        }

        System.out.println("query date: " + queryDate);

        Response resp = new WebResponse();
        if(queryDate.equals("")) {
            String content = this.buildTempPage();

            resp.setStatusCode(200);
            resp.setContent(content);

            resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
            resp.addHeader("Content-Type", "text/html");
            resp.addHeader("Connection", "Closed");

            return resp;
        }

        String[] dateContent = queryDate.split("/");
        if(dateContent.length != 3) {
            return WebResponse.constructErrorResponse(404);
        }

        String year = dateContent[0];
        String month = dateContent[1];
        String day = dateContent[2];

        String key = year + "-" + month + "-" + day;

        float temperature = 0;

        // temperatureData will be null on unit test cases
        if(temperatureData != null)
        temperature = temperatureData.getFloat(key);

        System.out.println(temperature);

        resp.setStatusCode(200);
        resp.setContent(this.buildXML(key, temperature));

        resp.addHeader("Content-Length", String.valueOf(resp.getContentLength()));
        resp.addHeader("Content-Type", "text/xml");
        resp.addHeader("Connection", "Closed");

        return resp;
    }
}
