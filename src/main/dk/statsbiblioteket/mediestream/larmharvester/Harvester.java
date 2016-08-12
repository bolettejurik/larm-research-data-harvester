package dk.statsbiblioteket.mediestream.larmharvester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by baj on 7/28/16.
 */
public class Harvester{

    private static Logger log = LoggerFactory.getLogger(Harvester.class);

    private String urlStrStart;
    private String searchStr = "Search/Get?q=*";
    private String sessionCreateStr = "Session/Create?";
    private String pageIndexStr = "&pageIndexStr=";
    private String formatStr = "formatStr=json2";
    private String sessionGUIDStr = "&sessionGUIDStr=";
    private String sessionGUID;

    private String userHTTPStatusCodes = "&userHTTPStatusCodes=False";
    private String pageSize = "&pageSize=100";

    public static final String ampersand = "&";

    public Harvester() throws IOException {
        this("https://dev.api.dighumlab.org/v6/");
    }

    public Harvester(String urlStrStart) throws IOException {
        this.urlStrStart = urlStrStart;
        sessionCreate();
        log.debug("sessionGUID = " + sessionGUID);
        System.out.println(sessionGUID);
        sessionGUID = "7ad87c25\u00AD1174\u00AD4622\u00ADb1e4\u00ADf5020cbe10e1";
    }

    public String harvest() throws IOException {
        String jsonStr = "";
        for (int i=0; i<10; i++) {
            jsonStr = harvest(i);

        }
        return jsonStr;
    }

    public void sessionCreate() throws IOException {
        String jsonStr = httpGet(urlStrStart + sessionCreateStr + formatStr + userHTTPStatusCodes);
        log.debug(urlStrStart + sessionCreateStr + formatStr + userHTTPStatusCodes);
        log.debug("sessionCreate jsonStr = ", jsonStr);
        sessionGUID = new Parser().parseSessionCreateToSessionGuid(jsonStr);
    }

    /**
     * harvest all assets page number i.
     *
     * https://dev.api.dighumlab.org/v6/Search/Get?q=*&pageIndexStr=0&pageSize=100&sessionGUIDStr=c19480c5-ed5c-4a99-b758-a3c05fdbb2c5&formatStr=json2&userHTTPStatusCodes=False
     *
     * @i page number
     */
    public String harvest(int i) throws IOException {
        String urlStr = urlStrStart + searchStr + pageIndexStr + i + pageSize + sessionGUIDStr + sessionGUID
                + ampersand + formatStr + userHTTPStatusCodes;
        log.debug("urlStr = " + urlStr);
        return httpGet(urlStr);
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

    public static String httpPost(String urlStr, String[] paramName,
                                  String[] paramVal) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        // Create the form content
        OutputStream out = conn.getOutputStream();
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        for (int i = 0; i < paramName.length; i++) {
            writer.write(paramName[i]);
            writer.write("=");
            writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
            writer.write("&");
        }
        writer.close();
        out.close();

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
