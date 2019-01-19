package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class, ObjectGenerator.class})
public class GroupRepositoryTest {

	private static final String GROUP_NAME = "Group";
	private static final String DEVICE_NAME = "Device";

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	DeviceRepository deviceRepository;

	@Autowired
	private ObjectGenerator generator;

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
	}

	@Test
	public void countEmptyDatabaseTest() {
		assertEquals(0, groupRepository.count());
	}

	@Test
	public void countTwoSavedGroupsBookTest() {
		List<Group> groups = generator.generateGroupList(2, GROUP_NAME);
		groups.forEach(groupRepository::save);

		assertEquals(2, groupRepository.count());
	}

	@Test
	public void saveAndRetrieveEntityTest() {
		Group originalGroup = new Group(GROUP_NAME);
		groupRepository.save(originalGroup);
		Group actualGroup = groupRepository.findByName(originalGroup.getName());

		assertNotNull(actualGroup);
		assertEquals(originalGroup, actualGroup);
	}

	@Test
	public void findExistingGroupByNameTest() {
		Group originalGroup = new Group(GROUP_NAME);
		groupRepository.save(originalGroup);
		Group actualGroup = groupRepository.findByName(GROUP_NAME);

		assertNotNull(actualGroup);
		assertEquals(originalGroup, actualGroup);
	}

	@Test
	public void findNonExistingGroupByNameTest() {
		Group originalGroup = new Group(GROUP_NAME);
		groupRepository.save(originalGroup);
		Group actualGroup = groupRepository.findByName("Non existing group");

		assertNull(actualGroup);
	}

	@Test
	public void saveGroupWithDevicesTest() {
		List<Device> devices = generator.generateDeviceList(2, DEVICE_NAME);
		devices.forEach(deviceRepository::save);
		Group originalGroup = new Group(GROUP_NAME);
		devices.forEach(originalGroup::assignDevice);
		groupRepository.save(originalGroup);
		Group actualGroup = groupRepository.findByName(originalGroup.getName());

		assertNotNull(actualGroup);
		assertEquals(2, actualGroup.getDevices().size());
	}

	@Test
	public void saveAssignDevicesToExistingGroupTest() {
		List<Device> devices = generator.generateDeviceList(2, DEVICE_NAME);
		devices.forEach(deviceRepository::save);
		Group originalGroup = new Group(GROUP_NAME);
		groupRepository.save(originalGroup);

		Group retrievedGroup = groupRepository.findByName(originalGroup.getName());
		devices.forEach(retrievedGroup::assignDevice);
		groupRepository.save(retrievedGroup);
		Group actualGroup = groupRepository.findByName(originalGroup.getName());

		assertNotNull(actualGroup);
		assertEquals(2, actualGroup.getDevices().size());
	}
}