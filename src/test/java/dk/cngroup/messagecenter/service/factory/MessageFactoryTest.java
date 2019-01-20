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

	private static final String GROUP = "Group 1";
	private static final String DEVICE_1 = "Device 1";
	private static final String DEVICE_2 = "Device 2";

	private Device deviceOne;
	private Device deviceTwo;
	private Set<Device> devices;
	private String content;
	private Group groupOne;

	@InjectMocks
	MessageFactory messageFactory;

	@Mock
	private DeviceService deviceService;

	@Mock
	private GroupService groupService;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		initData();
		when(deviceService.findByName(DEVICE_1)).thenReturn(deviceOne);
		when(deviceService.findByName(DEVICE_2)).thenReturn(deviceTwo);
		when(deviceService.findAll()).thenReturn(new ArrayList<>(devices));
		when(groupService.findByName(GROUP)).thenReturn(groupOne);
	}

	@Test
	public void createPerToPeerMessageTest() {
		Message expected = new Message(deviceOne, Collections.singleton(deviceTwo), content);
		Message actual = messageFactory.createPerToPeerMessage(DEVICE_1, DEVICE_2, content);

		assertEquals(expected, actual);
	}

	@Test
	public void createBroadcastMessageTest() {
		Message expected = new Message(deviceOne, new HashSet<>(devices), content);
		Message actual = messageFactory.createBroadcastMessage(DEVICE_1, content);

		assertEquals(expected, actual);
	}

	@Test
	public void createMulticastMessageTest() {
		Message expected = new Message(deviceOne, groupOne.getDevices(), content);
		Message actual = messageFactory.createMulticastMessage(DEVICE_1, GROUP, content);

		assertEquals(expected, actual);
	}

	private void initData() {
		content = "Hello World";
		devices = new HashSet<>();
		deviceOne = new Device(DEVICE_1);
		deviceTwo = new Device(DEVICE_2);
		devices.add(deviceOne);
		devices.add(deviceTwo);
		groupOne = new Group(GROUP, devices);
	}
}