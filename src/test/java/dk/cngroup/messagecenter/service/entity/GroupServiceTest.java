package dk.cngroup.messagecenter.service.entity;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.exception.RegistrationException;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import dk.cngroup.messagecenter.service.entity.GroupService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static dk.cngroup.messagecenter.TestUtils.DEVICE_NAME;
import static dk.cngroup.messagecenter.TestUtils.GROUP_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {MessageCenterApplication.class, ObjectGenerator.class})
public class GroupServiceTest {

	private static final String REGISTERED_GROUP_NAME = "Registered group";

	@InjectMocks
	GroupService groupService;

	@Mock
	GroupRepository groupRepository;

	@Autowired
	ObjectGenerator generator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findByNameReturnsGroupInstanceTest() {
		Group group = new Group(GROUP_NAME);
		when(groupRepository.findByName(anyString())).thenReturn(group);

		Group result = groupService.findByName(GROUP_NAME);

		assertEquals(result, group);
	}

	@Test
	public void assignOneDeviceToGroupTest() {
		Group group = new Group(GROUP_NAME);
		when(groupRepository.save(group)).thenReturn(group);

		Device device = new Device(DEVICE_NAME);
		Group actualGroup = groupService.assign(group, Collections.singletonList(device));

		assertEquals(1, actualGroup.getDevices().size());
	}

	@Test
	public void assignMoreDevicesToGroupTest() {
		Group group = new Group(GROUP_NAME);
		when(groupRepository.save(group)).thenReturn(group);

		List<Device> devices = generator.generateDeviceList(2, DEVICE_NAME);
		Group actualGroup = groupService.assign(group, devices);

		assertEquals(2, actualGroup.getDevices().size());
	}

	@Test
	public void registerUnregisteredGroupTest() {
		when(groupRepository.existsById(any())).thenReturn(false);
		when(groupRepository.existsById(REGISTERED_GROUP_NAME)).thenReturn(true);

		groupService.register(new Group(GROUP_NAME));
	}

	@Test(expected = RegistrationException.class)
	public void registerRegisteredGroupTest() {
		when(groupRepository.existsById(any())).thenReturn(false);
		when(groupRepository.existsById(REGISTERED_GROUP_NAME)).thenReturn(true);

		groupService.register(new Group(REGISTERED_GROUP_NAME));
	}
}