package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * This is integration test simulates registration of devices and groups
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class RegisterServiceTest {

	private static final String DEVICE_NAME = "Device";
	private static final String GROUP_NAME = "Group";

	@Autowired
	private RegisterService service;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ObjectGenerator generator;

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	@Test
	public void registerThreeDevicesTest() {
		register(3, DEVICE_NAME, service::registerDevice);
		assertEquals(3, deviceRepository.findAll().size());
	}

	@Test
	public void registerThreeGroupsTest() {
		register(3, GROUP_NAME, service::registerGroup);
		assertEquals(3, groupRepository.findAll().size());
	}

	@Test
	public void assignDevicesToTwoGroupsTest() {
		register(3, DEVICE_NAME, service::registerDevice);
		register(2, GROUP_NAME, service::registerGroup);

		service.assignDeviceToGroup(GROUP_NAME + "_0", DEVICE_NAME + "_0", DEVICE_NAME + "_1");
		service.assignDeviceToGroup(GROUP_NAME + "_0", DEVICE_NAME + "_2");
		service.assignDeviceToGroup(GROUP_NAME + "_1", DEVICE_NAME + "_2");

		Group groupOne = groupRepository.findByName(GROUP_NAME + "_0");
		Group groupTwo = groupRepository.findByName(GROUP_NAME + "_1");

		assertEquals(3, groupOne.getDevices().size());
		assertEquals(1, groupTwo.getDevices().size());
	}

	private void register(int number, String prefix, Consumer<? super String> method) {
		List<String> deviceNames = generator.generateNameList(number, prefix);
		deviceNames.forEach(method);
	}
}