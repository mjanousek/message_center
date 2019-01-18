package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static dk.cngroup.messagecenter.service.ConsoleShipper.DEVICE_RECEIVED_MESSAGE_TEMPLATE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * This is integration test simulates sending messages between devices
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class MessageServiceTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Autowired
	private MessageService messageService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private RegisterService registerDevice;

	@Autowired
	private ObjectGenerator generator;

	@Test
	public void peerToPeer() {
		Device sender = new Device("Device 1");
		Device receiver = new Device("Device 2");
		String content = "Hi my friend!";
		Message message = new Message(sender, Collections.singleton(receiver), content);

		registerDevice.registerDevice("Device 1");
		registerDevice.registerDevice("Device 2");

		messageService.peerToPeer("Device 1", "Device 2", content);

		String expected = getMessage(receiver, message);
		assertThat(outContent.toString(), containsString(expected));
	}

	@Test
	public void broadcast() {
		String content = "Hi my friends!";
		Device sender = new Device("Sender");
		Set<Device> receivers = generator.generateDeviceSet(3, "Device");
		Message message = new Message(sender, receivers, content);
		List<String> expectedMessages = new LinkedList<>();

		registerDevice.registerDevice(sender.getName());
		for (Device device : receivers) {
			registerDevice.registerDevice(device.getName());
			expectedMessages.add(getMessage(device, message));
		}

		messageService.broadcast("Sender", content);

		for (String expected : expectedMessages) {
			assertThat(outContent.toString(), containsString(expected));
		}
	}

	@Test
	public void multicast() {
	}

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStream() {
		System.setOut(originalOut);
	}

	private String getMessage(Device receiver, Message message) {
		return String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, receiver, message.getContent(), message.getSender());
	}
}