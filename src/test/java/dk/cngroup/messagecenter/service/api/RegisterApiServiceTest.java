package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.exception.RegistrationException;
import dk.cngroup.messagecenter.model.Group;
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

import java.util.List;
import java.util.function.Consumer;

import static dk.cngroup.messagecenter.TestUtils.*;
import static org.junit.Assert.*;

/**
 * This is integration test simulates registration of devices and groups
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class RegisterApiServiceTest {

	@Autowired
	private RegisterApiService service;

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

		service.assignDevicesToGroup(getGroupName(0), getDeviceName(0), getDeviceName(1));
		service.assignDevicesToGroup(getGroupName(0), getDeviceName(2));
		service.assignDevicesToGroup(getGroupName(1), getDeviceName(2));

		Group groupOne = groupRepository.findByName(getGroupName(0));
		Group groupTwo = groupRepository.findByName(getGroupName(1));

		assertEquals(3, groupOne.getDevices().size());
		assertEquals(1, groupTwo.getDevices().size());
	}

	@Test(expected = RegistrationException.class)
	public void assignDeviceToUnregisteredGroup() {
		register(1, DEVICE_NAME, service::registerDevice);
		service.assignDevicesToGroup("Undegistered device", getDeviceName(1));
	}

	@Test(expected = RegistrationException.class)
	public void assignUnregisteredDevicesToGroup() {
		register(1, GROUP_NAME, service::registerGroup);
		service.assignDevicesToGroup(GROUP_NAME, DEVICE_NAME);
	}

	private void register(int number, String prefix, Consumer<? super String> method) {
		List<String> deviceNames = generator.generateNameList(number, prefix);
		deviceNames.forEach(method);
	}
}