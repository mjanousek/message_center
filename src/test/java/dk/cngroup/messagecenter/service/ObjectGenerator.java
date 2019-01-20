package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import dk.cngroup.messagecenter.model.Keyword;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ObjectGenerator {

	public List<Device> generateDeviceList(int number, String prefix) {
		List<Device> devices = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			devices.add(new Device(prefix + i));
		}
		return devices;
	}

	public List<Group> generateGroupList(int number, String prefix) {
		List<Group> groups = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			groups.add(new Group(prefix + i));
		}
		return groups;
	}

	public List<Keyword> generateKeywordList(int number, String prefix) {
		List<Keyword> keywords = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			keywords.add(new Keyword(prefix + i));
		}
		return keywords;
	}

	public List<String> generateNameList(int number, String prefix) {
		List<String> names = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			names.add(prefix + i);
		}
		return names;
	}

	public Set<Device> generateDeviceSet(int number, String prefix) {
		return new HashSet<>(generateDeviceList(number, prefix));
	}

	public Set<Group> generateGroupSet(int number, String prefix) {
		return new HashSet<>(generateGroupList(number, prefix));
	}
}
