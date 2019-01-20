package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.exception.RegistrationException;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.service.entity.DeviceService;
import dk.cngroup.messagecenter.service.entity.GroupService;
import dk.cngroup.messagecenter.service.factory.DeviceFactory;
import dk.cngroup.messagecenter.service.factory.GroupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterApiService {

	@Autowired
	DeviceService deviceService;

	@Autowired
	GroupService groupService;

	public void registerDevice(String deviceId) {
		Device device = DeviceFactory.createDevice(deviceId);
		deviceService.register(device);
	}

	public void registerGroup(String groupId) {
		Group group = GroupFactory.createGroup(groupId);
		groupService.register(group);
	}

	public void assignDevicesToGroup(String groupId, String... deviceIds) {
		Group group = groupService.findByName(groupId);
		checkIfEntityIsRegistered(Group.class, groupId, group);
		List<Device> devices = deviceService.findByNames(deviceIds);
		checkIfDevicesAreRegistered(devices, deviceIds);
		groupService.assign(group, devices);
	}

	private void checkIfDevicesAreRegistered(List<Device> devices, String[] deviceIds) {
		if (deviceIds.length != devices.size()) {
			List<String> unregisteredDevices = Arrays.stream(deviceIds)
					.filter(id -> !devices.contains(new Device(id)))
					.collect(Collectors.toList());
			throw new RegistrationException("Devices '" + unregisteredDevices + "' are not registered");
		}
	}

	public static void checkIfEntityIsRegistered(Class<?> type, String id, Object entity) {
		if (entity == null) {
			throw new RegistrationException(type.getSimpleName() + " '" + id + "' is not registered");
		}
	}
}
