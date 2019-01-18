package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.processor.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageCenter {

	@Autowired
	/* The second option how to implement this is usage of Acpects but
	* I have to limit usage of Spring as it is writtent in the assignment*/
	private Set<MessageProcessor> processors;

	@Autowired
	private Shipper shipper;

	public void process(Message message) {
		for (MessageProcessor processor : processors) {
			processor.process(message);
		}
		shipper.send(message);
	}
}
