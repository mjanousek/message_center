package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Message;

public interface Shipper {
	public void send(Message message);
}
