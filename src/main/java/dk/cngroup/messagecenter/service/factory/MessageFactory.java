package dk.cngroup.messagecenter.service.factory;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.entity.DeviceService;
import dk.cngroup.messagecenter.service.entity.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static dk.cngroup.messagecenter.service.api.RegisterApiService.checkIfEntityIsRegistered;

@Service
public class MessageFactory {

	private DeviceService deviceService;
	private GroupService groupService;

	@Autowired
	public MessageFactory(DeviceService deviceService, GroupService groupService) {
		this.deviceService = deviceService;
		this.groupService = groupService;
	}

	public Message createPerToPeerMessage(String senderId, String receiverId, String content) {
		Device sender = deviceService.findByName(senderId);
		Device receiver = deviceService.findByName(receiverId);
		checkIfEntityIsRegistered(Device.class, senderId, sender);
		checkIfEntityIsRegistered(Device.class, receiverId, receiver);
		return new Message(sender, Collections.singleton(receiver), content);
	}

	public Message createBroadcastMessage(String senderId, String content) {
		Device sender = deviceService.findByName(senderId);
		checkIfEntityIsRegistered(Device.class, senderId, sender);
		List<Device> receivers = deviceService.findAll();
		return new Message(sender, new HashSet<>(receivers), content);
	}

	public Message createMulticastMessage(String senderId, String groupId, String content) {
		Device sender = deviceService.findByName(senderId);
		Group group = groupService.findByName(groupId);
		checkIfEntityIsRegistered(Device.class, senderId, sender);
		checkIfEntityIsRegistered(Group.class, groupId, group);
		Set<Device> receivers = group.getDevices();
		return new Message(sender, new HashSet<>(receivers), content);
	}
}
