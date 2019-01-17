package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class})
public class GroupRepositoryTest {

	@Autowired
	GroupRepository groupRepository;

	@Test
	@Transactional
	public void countEmptyDatabaseTest() {
		assertEquals(0, groupRepository.count());
	}

	@Test
	public void countTwoSavedGroupsBookTest() {

		Group firstGroup = new Group("Group 1");
		Group secondGroup = new Group("Group 2");
		groupRepository.save(firstGroup);
		groupRepository.save(secondGroup);

		assertEquals(2, groupRepository.count());
	}

	@Test
	public void saveAndRetrieveEntityTest() {
		Group originalGroup = new Group("Group 1");
		groupRepository.save(originalGroup);
		Group actualGroup = groupRepository.getOne(originalGroup.getId());

		assertNotNull(actualGroup);
		assertEquals(originalGroup.getName(), actualGroup.getName());
	}

	@Test
	public void saveGroupWithDevices() {
		Device deviceOne = new Device("Device 1");
		Device deviceTwo = new Device("Device 2");
		Group originalGroup = new Group("Group 1");
		originalGroup.assignDevice(deviceOne);
		originalGroup.assignDevice(deviceTwo);

		groupRepository.save(originalGroup);
		Group actualGroup = groupRepository.getOne(originalGroup.getId());

		assertNotNull(actualGroup);
		assertEquals(2, actualGroup.getDevices().size());
	}

	public void saveAssignDevicesToExistingGroup() {
		Device deviceOne = new Device("Device 1");
		Device deviceTwo = new Device("Device 2");
		Group originalGroup = new Group("Group 1");
		groupRepository.save(originalGroup);

		Group retrievedGroup = groupRepository.getOne(originalGroup.getId());
		retrievedGroup.assignDevice(deviceOne);
		retrievedGroup.assignDevice(deviceTwo);
		groupRepository.save(retrievedGroup);

		Group actualGroup = groupRepository.getOne(originalGroup.getId());

		assertNotNull(actualGroup);
		assertEquals(2, actualGroup.getDevices().size());
	}

	@Before
	@After
	public void cleanDb() {
		groupRepository.deleteAll();
	}
}