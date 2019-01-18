package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Message;
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
import java.util.HashSet;
import java.util.Set;

import static dk.cngroup.messagecenter.service.ConsoleShipper.DEVICE_RECEIVED_MESSAGE_TEMPLATE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageCenterApplication.class)
public class ConsoleShipperTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Autowired
	ConsoleShipper consoleShipper;

	@Test
	public void sendMessageToOneDeviceTest() {
		Device device = new Device("Device 1");
		String content = "Hello World";
		Message message = new Message(null, Collections.singleton(device), content);

		consoleShipper.send(message);

		String expected = getMessage(device, message);
		assertThat(outContent.toString(), containsString(expected));
	}

	@Test
	public void sendMessageToMoreDevicesTest() {
		Device deviceOne = new Device("Device 1");
		Device deviceTwo = new Device("Device 2");
		Set<Device> devices = new HashSet<>();
		devices.add(deviceOne);
		devices.add(deviceTwo);
		String content = "Hello World";
		Message message = new Message(null, devices, content);

		consoleShipper.send(message);

		String expectedMessageOne = getMessage(deviceOne, message);
		String expectedMessageTwo = getMessage(deviceTwo, message);

		assertThat(outContent.toString(), containsString(expectedMessageOne));
		assertThat(outContent.toString(), containsString(expectedMessageTwo));
	}

	private String getMessage(Device receiver, Message message) {
		return String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, receiver, message.getContent(), message.getSender());
	}

	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStream() {
		System.setOut(originalOut);
	}
}