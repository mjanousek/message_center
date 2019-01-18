package dk.cngroup.messagecenter.service.factory;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.entity.DeviceService;
import dk.cngroup.messagecenter.service.entity.GroupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MessageFactoryTest {

	@InjectMocks
	MessageFactory messageFactory;

	@Mock
	private DeviceService deviceService;

	@Mock
	private GroupService groupService;

	private Device deviceOne;
	private Device deviceTwo;
	private Set<Device> devices;
	private String content;
	private Group groupOne;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		initData();
		when(deviceService.findByName("Device 1")).thenReturn(deviceOne);
		when(deviceService.findByName("Device 2")).thenReturn(deviceTwo);
		when(deviceService.findAll()).thenReturn(new ArrayList<>(devices));
		when(groupService.findByName("Group 1")).thenReturn(groupOne);
	}
	@Test
	public void createPerToPeerMessage() {
		Message expected = new Message(deviceOne, Collections.singleton(deviceTwo), content);
		Message actual = messageFactory.createPerToPeerMessage("Device 1", "Device 2", content);
		assertEquals(expected, actual);
	}

	@Test
	public void createBroadcastMessage() {
		Message expected = new Message(deviceOne, new HashSet<>(devices), content);
		Message actual = messageFactory.createBroadcastMessage("Device 1", content);
		assertEquals(expected, actual);
	}

	@Test
	public void createMulticastMessage() {
		Message expected = new Message(deviceOne, groupOne.getDevices(), content);
		Message actual = messageFactory.createMulticastMessage("Device 1", "Group 1", content);
		assertEquals(expected, actual);
	}

	private void initData() {
		deviceOne = new Device("Device 1");
		deviceTwo = new Device("Device 2");
		devices = new HashSet<>();
		devices.add(deviceOne);
		devices.add(deviceTwo);
		groupOne = new Group("Group 1", devices);
		content = "Hello World";
	}
}