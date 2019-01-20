package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.MessageCenter;
import dk.cngroup.messagecenter.service.factory.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageApiService {

	@Autowired
	MessageCenter messageCenter;

	@Autowired
	MessageFactory messageFactory;

	public void peerToPeer(String senderId, String receiverId, String content) {
		Message message = messageFactory.createPerToPeerMessage(senderId, receiverId, content);
		messageCenter.process(message);
	}

	public void broadcast(String senderId, String content) {
		Message message = messageFactory.createBroadcastMessage(senderId, content);
		messageCenter.process(message);
	}

	public void multicast(String senderId, String groupId, String content) {
		Message message = messageFactory.createMulticastMessage(senderId, groupId, content);
		messageCenter.process(message);
	}
}
