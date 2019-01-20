package dk.cngroup.messagecenter.service.processor;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.model.Keyword;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import dk.cngroup.messagecenter.service.entity.KeywordService;
import dk.cngroup.messagecenter.service.entity.LogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, ObjectGenerator.class})
public class KeywordCounterMessageProcessorTest {


	@InjectMocks
	KeywordCounterMessageProcessor processor;

	@Mock
	KeywordService keywordService;

	@Mock
	LogService logService;

	@Autowired
	ObjectGenerator generator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createRegexForMatchingAllKeywordsTest() {
		List<Keyword> keywords = generator.generateKeywordList(3, "word");
		String actual = processor.createRegexForMatchingAllKeywords(keywords);
		String expected = "(word0|word1|word2)";

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void findKeywordsByRegexTest() {
		String regex = "(word0|word1|word2)";
		List<String> actual = processor.findKeywordsByRegex("Some word0 is also word5 and there is word2 as well", regex);
		List<String> expected = Arrays.asList("word0", "word2");

		Assert.assertEquals(expected, actual);
	}
}