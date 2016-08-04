package dk.statsbiblioteket.mediestream.larmharvester;

import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by baj on 8/1/16.
 */
public class HarvesterTest {

    @Test
    public void testHarvest() throws IOException {
        System.out.println(new Harvester().harvest());
    }

    @Test
    public void testHttpGet() throws IOException {
        System.out.println(Harvester.httpGet("https://dev.api.dighumlab.org/v6/Search/Get?q=*&pageIndexStr=0" +
                "&pageSize=100&sessionGUIDStr=c19480c5-ed5c-4a99-b758-a3c05fdbb2c5&formatStr=json2&userHTTPStatusCodes=False"));
    }
}
