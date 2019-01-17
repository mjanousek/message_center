package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageBroker {
	// TODO toto asi ted nedava smysl. Spis udelat MessageFactory

	private ReceiverResolver receiverResolver;
	private Shipper shipper;

	@Autowired
	public MessageBroker(ReceiverResolver receiverResolver, Shipper shipper) {
		this.receiverResolver = receiverResolver;
		this.shipper = shipper;
	}

//	public send(Group group, String message) {
//
//	}
//
//	public send(Group group, String message) {
//
//	}
}
