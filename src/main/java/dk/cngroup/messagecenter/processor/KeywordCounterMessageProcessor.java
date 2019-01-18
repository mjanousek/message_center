package dk.cngroup.messagecenter.processor;

import dk.cngroup.messagecenter.model.Message;
import org.springframework.stereotype.Service;

@Service
public class KeywordCounterMessageProcessor implements MessageProcessor {
	@Override
	public Message process(Message message) {
		System.out.println("Count keywords");
		return message;
	}
}
