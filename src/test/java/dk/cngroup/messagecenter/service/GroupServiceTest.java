package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.service.entity.GroupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class GroupServiceTest {

	@InjectMocks
	GroupService groupService;

	@Mock
	GroupRepository groupRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findByNameReturnsGroupInstanceTest(){
		Group group = new Group("Dummy group");
		when(groupRepository.findByName(anyString())).thenReturn(group);

		Group result = groupService.findByName("Dummy group");

		assertEquals(result, group);
	}

	@Test
	public void assignOneDeviceToGroupTest(){
		Group group = new Group("Dummy group");
		when(groupRepository.save(group)).thenReturn(group);

		Device device = new Device("Device 1");
		Group actualGroup = groupService.assign(group, device);

		assertEquals(1, actualGroup.getDevices().size());
	}

	@Test
	public void assignMoreDevicesToGroupTest(){
		Group group = new Group("Dummy group");
		when(groupRepository.save(group)).thenReturn(group);

		Device deviceOne = new Device("Device 1");
		Device deviceTwo = new Device("Device 2");
		Group actualGroup = groupService.assign(group, deviceOne, deviceTwo);

		assertEquals(2, actualGroup.getDevices().size());
	}

	@Test
	public void registerUnregisteredDeviceTest(){
		when(groupRepository.existsById(any())).thenReturn(false);
		when(groupRepository.existsById("Registered group")).thenReturn(true);

		groupService.register(new Group("Dummy group"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void registerRegisteredGroupTest(){
		when(groupRepository.existsById(any())).thenReturn(false);
		when(groupRepository.existsById("Registered group")).thenReturn(true);

		groupService.register(new Group("Registered group"));
	}
}