package dk.cngroup.messagecenter.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Message {
	private Device sender;
	private Set<Device> receivers;
	private String content;
	private List<String> keywords; // TODO fill or remove to different class

	public Message() {
	}

	public Message(Device sender, Set<Device> receivers, String content) {
		this.sender = sender;
		this.receivers = receivers;
		this.content = content;
	}

	public Device getSender() {
		return sender;
	}

	public void setSender(Device sender) {
		this.sender = sender;
	}

	public Set<Device> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<Device> receivers) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return Objects.equals(sender, message.sender) &&
				Objects.equals(receivers, message.receivers) &&
				Objects.equals(content, message.content) &&
				Objects.equals(keywords, message.keywords);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sender, receivers, content, keywords);
	}
}
