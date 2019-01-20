package dk.cngroup.messagecenter.service.messenger;

import dk.cngroup.messagecenter.model.Message;

public interface Messenger {
	public void send(Message message);
}
