package ask.url.filter.app;

import ask.model.TextLine;
import ask.model.TextLineRepository;
import ask.model.UrlFileProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ComponentScan("ask.file.upload, ask.model")
@EntityScan( basePackages = {"ask.model"} )
class DbTransactionTest {

	private final String url1 = "http://somecompany.com/TestURL";

	@Autowired
	private TextLineRepository tlr;


	@Test
	void checkTestLineSaved() {
		TextLine tl0 = new TextLine(url1);
	    tlr.save(tl0);

	    Optional<TextLine> tl1 = tlr.findById(url1.toUpperCase());
	    assert (tl1.isPresent()) : "failed to save";
	}

	/**
	 * This test validates the database is working
	 */
	@Test
	void checkTestLineDelete() {
		Optional<TextLine> tl0 = tlr.findById(url1.toUpperCase());
		tl0.ifPresent(tlr::delete);

		Optional<TextLine> tl1 = tlr.findById(url1.toUpperCase());
		assert (! tl1.isPresent()) : "failed to delete";
	}

}
