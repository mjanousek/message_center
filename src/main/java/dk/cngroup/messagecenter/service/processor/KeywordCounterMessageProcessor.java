package dk.cngroup.messagecenter.service.processor;

import dk.cngroup.messagecenter.model.Keyword;
import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.entity.KeywordService;
import dk.cngroup.messagecenter.service.entity.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class KeywordCounterMessageProcessor implements MessageProcessor {

	private static final String KEYWORDS_LOG_MESSAGE = "Keywords: %s was detected in message: '%s'";

	@Autowired
	KeywordService keywordService;

	@Autowired
	LogService logService;

	@Override
	public void process(Message message) {
		List<Keyword> keywordsFound = findKeywords(message);
		if (keywordsFound.size() > 0) {
			String logMessage = createLogMessage(keywordsFound, message);
			logService.log(logMessage);
		}
	}

	protected List<Keyword> findKeywords(Message message) {
		List<Keyword> keywords = keywordService.findAll();
		String regex = createRegexForMatchingAllKeywords(keywords);
		List<String> keywordsFound = findKeywordsByRegex(message.getContent(), regex);
		return keywordService.findByWordIn(keywordsFound);
	}

	protected String createRegexForMatchingAllKeywords(List<Keyword> keywords) {
		String keywordsDividedByVerticalBar = keywords.stream()
				.map(Keyword::getWord)
				.collect(Collectors.joining("|"));

		return String.format("(%s)", keywordsDividedByVerticalBar);
	}

	protected List<String> findKeywordsByRegex(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		List<String> keywordsFound = new LinkedList<>();
		while (matcher.find()) {
			keywordsFound.add(matcher.group(1));
		}
		return keywordsFound;
	}

	private String createLogMessage(List<Keyword> keywordsFound, Message message) {
		return String.format(KEYWORDS_LOG_MESSAGE, keywordsFound, message);
	}
}
