package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.exception.RegistrationException;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static dk.cngroup.messagecenter.TestUtils.DEVICE_NAME;
import static dk.cngroup.messagecenter.TestUtils.GROUP_NAME;
import static dk.cngroup.messagecenter.service.messenger.ConsoleMessenger.DEVICE_RECEIVED_MESSAGE_TEMPLATE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * This is integration test simulates sending messages between devices
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class MessageApiServiceTest {

	private static final String SENDER_NAME = "Sender";
	private static final String RECEIVER_NAME = "Receiver";

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Autowired
	private MessageApiService messageApiService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private RegisterApiService registerApiService;

	@Autowired
	private ObjectGenerator generator;

	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStream() {
		System.setOut(originalOut);
	}

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	@Test
	public void peerToPeerTest() {
		String content = "Hi my friend!";
		Device sender = new Device(SENDER_NAME);
		Device receiver = new Device(RECEIVER_NAME);
		Message message = new Message(sender, Collections.singleton(receiver), content);

		registerApiService.registerDevice(SENDER_NAME);
		registerApiService.registerDevice(RECEIVER_NAME);
		messageApiService.peerToPeer(SENDER_NAME, RECEIVER_NAME, content);

		String expected = getMessage(receiver, message);
		assertThat(outContent.toString(), containsString(expected));
	}

	@Test(expected = RegistrationException.class)
	public void peerToPeerFromUnregisteredDeviceTest() {
		String content = "Hi my friend!";
		registerApiService.registerDevice(RECEIVER_NAME);
		messageApiService.peerToPeer(SENDER_NAME, RECEIVER_NAME, content);
	}

	@Test(expected = RegistrationException.class)
	public void peerToPeerToUnregisteredDeviceTest() {
		String content = "Hi my friend!";
		registerApiService.registerDevice(SENDER_NAME);
		messageApiService.peerToPeer(SENDER_NAME, RECEIVER_NAME, content);
	}

	@Test
	public void broadcastTest() {
		String content = "Hi my friends!";
		Device sender = new Device(SENDER_NAME);
		Set<Device> receivers = generator.generateDeviceSet(3, DEVICE_NAME);
		Message message = new Message(sender, receivers, content);
		List<String> expectedMessages = new LinkedList<>();

		registerApiService.registerDevice(sender.getName());
		for (Device device : receivers) {
			registerApiService.registerDevice(device.getName());
			expectedMessages.add(getMessage(device, message));
		}

		messageApiService.broadcast(SENDER_NAME, content);

		for (String expected : expectedMessages) {
			assertThat(outContent.toString(), containsString(expected));
		}
	}

	@Test(expected = RegistrationException.class)
	public void broadcastFromUnregisteredDeviceTest() {
		String content = "Hi my friend!";
		messageApiService.broadcast(SENDER_NAME, content);
	}

	@Test
	public void multicastTest() {
		String content = "Hi there in my group!";
		Device sender = new Device(SENDER_NAME);
		List<Device> devices = generator.generateDeviceList(4, DEVICE_NAME);
		List<Group> groups = generator.generateGroupList(2, GROUP_NAME);
		Message message = new Message(sender, new HashSet<>(devices), content);
		List<String> expectedMessages = new LinkedList<>();

		for (Group group : groups) {
			registerApiService.registerGroup(group.getName());
		}

		registerApiService.registerDevice(sender.getName());
		for (int i = 0; i < devices.size(); i++) {
			String deviceName = devices.get(i).getName();
			String groupName = groups.get(i % 2).getName();
			registerApiService.registerDevice(deviceName);
			registerApiService.assignDevicesToGroup(groupName, deviceName);
			if (i % 2 == 0) {
				expectedMessages.add(getMessage(devices.get(i), message));
			}
		}

		messageApiService.multicast(SENDER_NAME, groups.get(0).getName(), content);

		for (String expected : expectedMessages) {
			assertThat(outContent.toString(), containsString(expected));
		}
	}

	@Test(expected = RegistrationException.class)
	public void multicastFromUnregisteredDeviceTest() {
		String content = "Hi my friend!";
		registerApiService.registerGroup(GROUP_NAME);
		messageApiService.multicast(SENDER_NAME, GROUP_NAME, content);
	}

	@Test(expected = RegistrationException.class)
	public void multicastToUnregisteredGroupTest() {
		String content = "Hi my friend!";
		registerApiService.registerDevice(SENDER_NAME);
		messageApiService.multicast(SENDER_NAME, GROUP_NAME, content);
	}

	private String getMessage(Device receiver, Message message) {
		return String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, receiver, message.getContent(), message.getSender());
	}
}