package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static dk.cngroup.messagecenter.service.ConsoleShipper.DEVICE_RECEIVED_MESSAGE_TEMPLATE;
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
		String message = "Hello World";

		consoleShipper.send(Arrays.asList(device), message);

		String expected = String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, device, message);
		assertEquals(expected + "\n", outContent.toString());
	}

	@Test
	public void sendMessageToMoreDevicesTest() {
		Device deviceOne = new Device("Device 1");
		Device deviceTwo = new Device("Device 2");
		String message = "Hello World";

		consoleShipper.send(Arrays.asList(deviceOne, deviceTwo), message);

		String expected = String.join(
				"\n",
				String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, deviceOne, message),
				String.format(DEVICE_RECEIVED_MESSAGE_TEMPLATE, deviceTwo, message));

		assertEquals(expected + "\n", outContent.toString());
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