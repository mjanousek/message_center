package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Message;
import org.springframework.stereotype.Service;

@Service
public class ConsoleShipper implements Shipper {

	public static final String DEVICE_RECEIVED_MESSAGE_TEMPLATE = "Device '%s' received message '%s' from device '%s'";

	@Override
	public void send(Message message) {
		for (Device device : message.getReceivers()) {
			String outputMessage = String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, device, message.getContent(), message.getSender());
			System.out.println(outputMessage);
		}
	}
}
