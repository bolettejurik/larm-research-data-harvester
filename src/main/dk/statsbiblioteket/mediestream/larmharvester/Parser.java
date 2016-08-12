package dk.statsbiblioteket.mediestream.larmharvester;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ListIterator;

/**
 * Created by baj on 8/12/16.
 */
public class Parser {

    private static Logger log = LoggerFactory.getLogger(Parser.class);

    public String parseSessionCreateToSessionGuid(String jsonStr) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        JSONObject body = jsonObject.getJSONObject("Body");
        log.debug("Body", body);
        JSONArray results = body.getJSONArray("Results");
        log.debug("results.size() = " + results.size());
        ListIterator resultsIterator = results.listIterator();
        JSONObject result = (JSONObject) resultsIterator.next();
        String guid = result.getString("Guid");
        return guid;
    }

    public String filter(String jsonStr) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        JSONObject body = jsonObject.getJSONObject("Body");
        log.debug("Body", body);
        JSONArray results = body.getJSONArray("Results");
        log.debug("results.size() = " + results.size());

        ListIterator resultsIterator = results.listIterator();
        JSONObject asset;
        while (resultsIterator.hasNext()) {
            asset = (JSONObject) resultsIterator.next();
            if (asset.has("Annotations")) {
                log.debug("Bib");
            }
        }
        return "";
    }


}
