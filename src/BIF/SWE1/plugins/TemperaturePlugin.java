package BIF.SWE1.plugins;

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

        this.generateData();

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

        for (int i = 0;true;i++) {
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

    @Override
    public float canHandle(Request req) {
        // exit condition
        if (!req.isValid()) return 0;

        if (MethodType.valueOf(req.getMethod()) == MethodType.GET && req.getUrl().getPath().contains("/GetTemperature"))
            return 1;

        return 0;
    }

    @Override
    public Response handle(Request req) {
        // GET /GetTemperature
        // Website mit allen Werten

        // GET /GetTemperature/2019/01/01
        // XML mit den Werten

        String[] result = req.getUrl().getPath().split("/");

        if(result.length == 1){
            // TODO Return all temperatures
        }else if(result.length == 4){
            String day = result[3];
            String month = result[2];
            String year = result[1];

            // Synchronized
            float temp = temperatureData.getFloat(year + "-" + month + "-" + day);


            /*
                <TemperatureEntry>
                    <Date>2020-01-01<\Date>
                    <Temp>32.038478<\Temp>
                <\TemperatureEntry>
             */

            // TODO Return temp as Response
        }else{
            // TODO Wrong Rest Request
        }

        return null;
    }
}
