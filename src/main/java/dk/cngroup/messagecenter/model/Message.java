package dk.cngroup.messagecenter.model;

import java.util.List;

public class Message {
	private Device sender;
	private List<Device> receivers;
	private String content;
	private List<String> keywords; // TODO fill or remove to different class

	public Device getSender() {
		return sender;
	}

	public void setSender(Device sender) {
		this.sender = sender;
	}

	public List<Device> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<Device> receivers) {
		this.receivers = receivers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
}
