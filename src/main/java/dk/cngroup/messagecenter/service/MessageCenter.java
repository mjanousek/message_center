package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.messenger.Messenger;
import dk.cngroup.messagecenter.service.processor.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageCenter {

	@Autowired
	/* The second option how to implement this is usage of Aspects but
	 * I have to limit usage of Spring and Java dependencies to the essentials as
	 * it is written in the assignment*/
	private Set<MessageProcessor> processors;

	@Autowired
	private Messenger messenger;

	public void process(Message message) {
		processors.forEach(p -> p.process(message));
		messenger.send(message);
	}
}
