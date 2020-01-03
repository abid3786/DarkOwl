package ask.url.filter.app;

import ask.model.UrlFileProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@ActiveProfiles("test")
@ComponentScan("ask.file.upload, ask.model")
@EntityScan( basePackages = {"ask.model"} )
public class UrlFileProcessorTest {

    @Autowired
    private UrlFileProcessor urlFileProcessor;

    /**
     * This test pretty much test every thing in this process
     */
    @Test
    public void testUploadFile() {
        final String fileName = "Urls.txt";
        URL fullyQualifiedFile = UrlFileProcessorTest.class.getClassLoader().getResource(fileName);
        try {
            this.urlFileProcessor.deleteAll();
            Path p = Paths.get(fullyQualifiedFile.toURI());
            this.urlFileProcessor.processFile(p);
            long recCount = this.urlFileProcessor.writeFile();
            assert (recCount == 15) : "Expected record count is 15";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
