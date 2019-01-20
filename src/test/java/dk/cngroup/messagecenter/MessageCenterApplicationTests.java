package dk.cngroup.messagecenter;

import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.data.KeywordRepository;
import dk.cngroup.messagecenter.data.LogRepository;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import dk.cngroup.messagecenter.service.api.KeywordApiService;
import dk.cngroup.messagecenter.service.api.MessageApiService;
import dk.cngroup.messagecenter.service.api.RegisterApiService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, ObjectGenerator.class})
public class MessageCenterApplicationTests {

	private static final String GROUP_NAME = "Group";
	private static final String DEVICE_NAME = "Device";

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Autowired private MessageApiService messageApiService;
	@Autowired private RegisterApiService registerApiService;
	@Autowired private KeywordApiService keywordApiService;

	@Autowired private GroupRepository groupRepository;
	@Autowired private DeviceRepository deviceRepository;
	@Autowired private KeywordRepository keywordRepository;
	@Autowired private LogRepository logRepository;

	@Autowired private ObjectGenerator generator;

	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStream() {
		writeOutputToFile();
		System.setOut(originalOut);
	}

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
		deviceRepository.deleteAll();
		keywordRepository.deleteAll();
		logRepository.deleteAll();
	}

	@Test
	public void testApplication() {
		register(10, "Device", registerApiService::registerDevice);
		register(3, "Group", registerApiService::registerGroup);
		register(5, "word", keywordApiService::add);

		/*
		Group0: Device0, Device1, Device4
		Group1: Device0, Device5
		Group2: Device0, Device4, Device7, Device8
		*/
		assignDevicesToGroups();

		/*
		Device0 -> Device1
		Device1 -> Device0
		Device5 -> ALL
		Device8 -> Group0
		Device9 -> Group1
		Device3 -> Group2
		 */
		sendMessages();
	}

	private void sendMessages() {
		messageApiService.peerToPeer(getDeviceName(0), getDeviceName(1), "Hi there, welcome! word3 is still there");
		messageApiService.peerToPeer(getDeviceName(1), getDeviceName(0), "Hi there, thanks");
		messageApiService.broadcast(getDeviceName(5), "Welcome everyone! [word0, word3, word9]");
		messageApiService.multicast(getDeviceName(8), getGroupName(0), "Hi group 0! (Devices 0,1,4)");
		messageApiService.multicast(getDeviceName(9), getGroupName(1), "Hi group 1! (Devices 0,5) [word1, word, word4]");
		messageApiService.multicast(getDeviceName(3), getGroupName(2), "Hi group 2! (Devices 0,4,7,8)");

	}

	private void assignDevicesToGroups() {
		registerApiService.assignDeviceToGroup(getGroupName(0), getDeviceName(0), getDeviceName(1), getDeviceName(4));
		registerApiService.assignDeviceToGroup(getGroupName(1), getDeviceName(0), getDeviceName(5));
		registerApiService.assignDeviceToGroup(getGroupName(2), getDeviceName(0), getDeviceName(4), getDeviceName(7), getDeviceName(8));
	}

	private String getName(String name, int number) {
		return name + number;
	}

	private String getGroupName(int number) {
		return getName(GROUP_NAME, number);
	}

	private String getDeviceName(int number) {
		return getName(DEVICE_NAME, number);
	}

	private void register(int number, String prefix, Consumer<? super String> method) {
		List<String> deviceNames = generator.generateNameList(number, prefix);
		deviceNames.forEach(method);
	}

	private void writeOutputToFile() {
		try (PrintWriter writer = new PrintWriter("out/out.txt", "UTF-8")) {
			writer.println(outContent);
		} catch (FileNotFoundException|UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}

