package dk.statsbiblioteket.mediestream.larmharvester;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
//import org.testng.log4testng.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by baj on 7/28/16.
 */
public class Harvester{

    //private static Logger log = LoggerFactory.getLogger(Harvester.class);

    private String urlStrStart;
    private String searchStr = "Search/Get?q=*";
    private String sessionCreateStr = "Session/Create?";
    private String pageIndexStr = "&pageIndexStr=";
    private String formatStr = "&formatStr=json2";
    private String sessionGUIDStr = "&sessionGUIDStr=";
    private String sessionGUID;

    private String userHTTPStatusCodes = "&userHTTPStatusCodes=False";
    private String pageSize;

    public Harvester() throws IOException {
        urlStrStart = "https://dev.api.dighumlab.org/v6/";
        pageSize = "&pageSize=100";

        sessionGUID = httpGet(urlStrStart + sessionCreateStr + formatStr + userHTTPStatusCodes);
        //log.debug("sessionGUID = " + sessionGUID);
        System.out.println(sessionGUID);
        sessionGUID = "7ad87c25\u00AD1174\u00AD4622\u00ADb1e4\u00ADf5020cbe10e1";

    }

    public Harvester(String urlStrStart) throws IOException {
        this();
        this.urlStrStart = urlStrStart;

    }

    public String harvest() throws IOException {
        String jsonStr = "";
        for (int i=0; i<10; i++) {
            jsonStr = harvest(i);
            JsonObject jsonObj = new Gson().fromJson(jsonStr, JsonObject.class);
            //if (jsonObj.has())
        }
        return jsonStr;
    }
    /**
     * harvest all assets page number i.
     *
     * https://dev.api.dighumlab.org/v6/Search/Get?q=*&pageIndexStr=0&pageSize=100&sessionGUIDStr=c19480c5-ed5c-4a99-b758-a3c05fdbb2c5&formatStr=json2&userHTTPStatusCodes=False
     *
     * @i page number
     */
    public String harvest(int i) throws IOException {

        return httpGet(urlStrStart + searchStr + pageIndexStr + i + pageSize + sessionGUIDStr + sessionGUID
                + formatStr + userHTTPStatusCodes);
    }

    public static String httpGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }
}
