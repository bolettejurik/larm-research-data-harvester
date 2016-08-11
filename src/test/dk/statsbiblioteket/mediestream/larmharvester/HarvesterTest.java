package dk.statsbiblioteket.mediestream.larmharvester;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by baj on 8/1/16.
 */
public class HarvesterTest {


    private String jsonStr = "{\"Header\": {\"Duration\": 1001.3325},\"Body\": {\"Count\": 9,\"TotalCount\": 9,\"Results\": [{\"Identifier\": \"2f2a0a45-2dc8-2a05-46b5-421fab35d741\",\"Fields\": {\"produktions nr.\":\"9597-3-15\",\"original titel\":\"Eksklusion\",\"år\":\"1997\",\"varighed minutter\":\"30\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"e6938561-05b9-199a-8847-06f81f4cecaa\",\"Fields\": {\"produktions nr.\":\"9597-3-14\",\"original titel\":\"Mens vi venter\",\"år\":\"1997\",\"varighed minutter\":\"28\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"4481547d-afa7-871a-fb90-1b39053475f5\",\"Fields\": {\"produktions nr.\":\"9597-3-13\",\"original titel\":\"Clarks\",\"år\":\"1997\",\"varighed minutter\":\"33\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"6871a585-eae7-674c-6a0e-e5fa854e36cd\",\"Fields\": {\"produktions nr.\":\"9597-3-17\",\"original titel\":\"Den usynlige stemme\",\"år\":\"1997\",\"varighed minutter\":\"21\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"2e12d985-a209-afe3-24ea-ce07be05dd23\",\"Fields\": {\"produktions nr.\":\"9597-3-16\",\"original titel\":\"Ronnie Rocket\",\"år\":\"1997\",\"varighed minutter\":\"36\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"5fcaeea2-3a57-802b-29c9-248aaa507ca6\",\"Fields\": {\"produktions nr.\":\"9597-3-18\",\"original titel\":\"En Rigtig William\",\"år\":\"1997\",\"varighed minutter\":\"33\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"cfc8d8ac-ee74-6c92-0b2d-1b417802635f\",\"Fields\": {\"produktions nr.\":\"9597-3-10\",\"original titel\":\"Hotelværelse\",\"år\":\"1997\",\"varighed minutter\":\"38\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"fd0668d1-a754-12e7-b47f-44edc7f4fa38\",\"Fields\": {\"produktions nr.\":\"9597-3-12\",\"original titel\":\"Fremkaldelse\",\"år\":\"1997\",\"varighed minutter\":\"25\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"},{\"Identifier\": \"7b3dd440-6876-523e-7ba4-282b042d36d0\",\"Fields\": {\"produktions nr.\":\"9597-3-11\",\"original titel\":\"Togkupé\",\"år\":\"1997\",\"varighed minutter\":\"24\"},\"FullName\": \"EZArchive.Portal.Module.Api.Results.SearchResult\"}]},\"Error\": {\"Fullname\": null,\"Message\": null,\"InnerException\": null}}";

    @Test
    public void testFilter() throws IOException {
        System.out.println(new Harvester().filter(jsonStr));
    }

    @Test
    public void testHarvest() throws IOException {
        System.out.println(new Harvester().harvest());
    }

    @Test
    public void testJson() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
        String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";
        JSONObject jsonObject = JSONObject.fromObject( json );
        Object bean = JSONObject.toBean( jsonObject );
        assertEquals( jsonObject.get( "name" ), PropertyUtils.getProperty( bean, "name" ) );
        assertEquals( jsonObject.get( "bool" ), PropertyUtils.getProperty( bean, "bool" ) );
        assertEquals( jsonObject.get( "int" ), PropertyUtils.getProperty( bean, "int" ) );
        assertEquals( jsonObject.get( "double" ), PropertyUtils.getProperty( bean, "double" ) );
        assertEquals( jsonObject.get( "func" ), PropertyUtils.getProperty( bean, "func" ) );
        List expected = JSONArray.toList( jsonObject.getJSONArray( "array" ) );
        System.out.println(jsonObject);

        json = jsonStr;
        jsonObject = JSONObject.fromObject( json );
        System.out.println(jsonObject);

        String content = new String(Files.readAllBytes(Paths.get("src/resources/rasmus.json")));
        json = content;
        jsonObject = JSONObject.fromObject( json );
        System.out.println(jsonObject);

    }

    @Test
    public void testHttpGet() throws Exception {
        //dev systemet
        System.out.println(Harvester.httpGet("https://dev.api.dighumlab.org/v6/Search/Get?q=1997" +
                "&pageIndex=0&pageSize=10&sessionGUID=c19480c5-ed5c-4a99-b758-a3c05fdbb2c5" +
                "&format=json2&userHTTPStatusCodes=False"));
        System.out.println();

        //stage systemet
        System.out.println(Harvester.httpGet("http://api.stage.larm.fm/v6/EZSearch/Get?q=2011" +
                "&pageIndex=0&pageSize=20&sessionGUID=572f010b-a416-48ce-baa3-590768a641e2" +
                "&format=json2&userHTTPStatusCodes=False"));
        System.out.println(Harvester.httpGet("http://api.stage.larm.fm/v6/User/" +
                "Get?sessionGUID=572f010b-a416-48ce-baa3-590768a641e2&format=json2&userHTTPStatusCodes=False"));
        //Rasmus Klump og Futkarl / DMiP test (indeholder annotation)
        //http://stage.larm.fm/Asset/06e5a2a8-0f70-4b06-af9c-2d79522be8a8
        System.out.println(Harvester.httpGet("http://api.stage.larm.fm/v6/EZAsset/" +
                "Get?id=06e5a2a8-0f70-4b06-af9c-2d79522be8a8" +
                "&sessionGUID=572f010b-a416-48ce-baa3-590768a641e2&format=json2&userHTTPStatusCodes=False"));
        System.out.println();

        //prod systemet
        System.out.println(Harvester.httpGet("http://api.prod.larm.fm/v6/EZAsset/" +
                "Get?id=04c51ee1-1b6c-4e50-b93b-79206c8a3eb6&sessionGUID=030055bd-30d8-46d1-baa4-2873c50dcfaa" +
                "&format=json2&userHTTPStatusCodes=False"));

        //EmailPassword Login
        //System.out.println(Harvester.httpPost("http://api.prod.larm.fm/v6/baj@statsbiblioteket.dk/Login",
          //      new String[0], new String[0]));

        //STATSMINISTER POUL NYRUP RASMUSSEN 'S NYTÅRSTALE 1994/1995
        //http://stage.larm.fm/Asset/67306300-2fde-814d-807b-7720a5f89ed5

    }
}
