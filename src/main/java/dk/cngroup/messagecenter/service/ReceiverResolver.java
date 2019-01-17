package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReceiverResolver {

	public List<Device> resolve(Device device) {
		return Arrays.asList(device);
	}

	public List<Device> resolve(Group group) {
		return new ArrayList<>(group.getDevices());
	}
}
