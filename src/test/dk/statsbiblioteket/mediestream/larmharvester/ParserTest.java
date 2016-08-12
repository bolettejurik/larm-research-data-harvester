package dk.statsbiblioteket.mediestream.larmharvester;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;

/**
 * Created by baj on 8/12/16.
 */
public class ParserTest {

    @Test
    public void testParseSessionCreateToSessionGuid() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/resources/sessionCreate.json")));
        String guid = new Parser().parseSessionCreateToSessionGuid(content);
        assertEquals(guid, "2f95f1ba-ac17-49c8-b7fa-add7a4da3964");
    }

    @Test
    public void testFilter() throws IOException {
        System.out.println(new Parser().filter(HarvesterTest.jsonStr));
    }

}
