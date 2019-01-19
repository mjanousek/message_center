package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ObjectGenerator {

	public List<Device> generateDeviceList(int number, String namePrefix) {
		List<Device> devices = new LinkedList<>();
		namePrefix += "_";
		for (int i = 0; i < number; i++) {
			devices.add(new Device(namePrefix + i));
		}
		return devices;
	}

	public List<Group> generateGroupList(int number, String namePrefix) {
		List<Group> groups = new LinkedList<>();
		namePrefix += "_";
		for (int i = 0; i < number; i++) {
			groups.add(new Group(namePrefix + i));
		}
		return groups;
	}

	public List<String> generateNameList(int number, String prefix) {
		List<String> names = new LinkedList<>();
		prefix += "_";
		for (int i = 0; i < number; i++) {
			names.add(prefix + i);
		}
		return names;
	}

	public Set<Device> generateDeviceSet(int number, String namePrefix) {
		return new HashSet<>(generateDeviceList(number, namePrefix));
	}

	public Set<Group> generateGroupSet(int number, String namePrefix) {
		return new HashSet<>(generateGroupList(number, namePrefix));
	}
}
