package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.model.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * This is integration test simulates registration of devices and groups
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class})
@Transactional
public class RegisterServiceTest {

	@Autowired
	private RegisterService service;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Test
	public void registerDevices() {
		service.registerDevice("Device 1");
		service.registerDevice("Device 2");
		service.registerDevice("Device 3");

		assertEquals(3, deviceRepository.findAll().size());
	}

	@Test
	public void registerGroups() {
		service.registerGroup("Group 1");
		service.registerGroup("Group 2");
		service.registerGroup("Group 3");

		assertEquals(3, groupRepository.findAll().size());
	}

	@Test
	public void assignDevicesToTwoGroups() {
		service.registerDevice("Device 1");
		service.registerDevice("Device 2");
		service.registerDevice("Device 3");
		service.registerGroup("Group 1");
		service.registerGroup("Group 2");
		service.assignDeviceToGroup("Group 1", "Device 1", "Device 2");
		service.assignDeviceToGroup("Group 1", "Device 3");
		service.assignDeviceToGroup("Group 2", "Device 3");

		Group groupOne = groupRepository.findByName("Group 1");
		Group groupTwo = groupRepository.findByName("Group 2");

		assertEquals(3, groupOne.getDevices().size());
		assertEquals(1, groupTwo.getDevices().size());
	}

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
		deviceRepository.deleteAll();
	}
}