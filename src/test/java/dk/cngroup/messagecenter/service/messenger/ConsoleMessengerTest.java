package dk.cngroup.messagecenter.service.messenger;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Message;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Set;

import static dk.cngroup.messagecenter.service.messenger.ConsoleMessenger.DEVICE_RECEIVED_MESSAGE_TEMPLATE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, ObjectGenerator.class})
public class ConsoleMessengerTest {

	private static final String DEVICE_NAME = "Device";
	private static final String MESSAGE_CONTENT = "Hello World";

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Autowired
	ConsoleMessenger consoleShipper;

	@Autowired
	ObjectGenerator generator;

	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStream() {
		System.setOut(originalOut);
	}

	@Test
	public void sendMessageToOneDeviceTest() {
		Device device = new Device(DEVICE_NAME);
		Message message = new Message(null, Collections.singleton(device), MESSAGE_CONTENT);
		consoleShipper.send(message);

		String expected = getMessage(device, message);
		assertThat(outContent.toString(), containsString(expected));
	}

	@Test
	public void sendMessageToMoreDevicesTest() {
		Set<Device> devices = generator.generateDeviceSet(2, DEVICE_NAME);
		Message message = new Message(null, devices, MESSAGE_CONTENT);
		consoleShipper.send(message);

		for (Device device : devices) {
			String expectedMessage = getMessage(device, message);
			assertThat(outContent.toString(), containsString(expectedMessage));
		}
	}

	private String getMessage(Device receiver, Message message) {
		return String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, receiver, message.getContent(), message.getSender());
	}
}