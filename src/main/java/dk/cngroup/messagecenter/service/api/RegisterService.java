package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.service.entity.DeviceService;
import dk.cngroup.messagecenter.service.entity.GroupService;
import dk.cngroup.messagecenter.service.factory.DeviceFactory;
import dk.cngroup.messagecenter.service.factory.GroupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

	@Autowired
	DeviceService deviceService;

	@Autowired
	GroupService groupService;

	public void registerDevice(String deviceId){
		Device device = DeviceFactory.createDevice(deviceId);
		deviceService.register(device);
	}

	public void registerGroup(String groupId){
		Group group = GroupFactory.createGroup(groupId);
		groupService.register(group);
	}

	public void assignDeviceToGroup(String groupId, String... deviceIds){
		Group group = groupService.findByName(groupId);
		List<Device> devices = deviceService.findByNames(deviceIds);
		groupService.assign(group, devices);
	}
}
