package dk.cngroup.messagecenter.service.entity;

import dk.cngroup.messagecenter.data.GroupRepository;
import dk.cngroup.messagecenter.exception.RegistrationException;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupService implements Register<Group> {

	GroupRepository repository;

	@Autowired
	public GroupService(GroupRepository repository) {
		this.repository = repository;
	}

	@Override
	public Group register(Group group) {
		if (repository.existsById(group.getName())) {
			throw new RegistrationException("Group '" + group.getName() + "' is already registered");
		}
		return repository.save(group);
	}

	public Group findByName(String name) {
		return repository.findByName(name);
	}

	public Group assign(Group group, List<Device> devices) {
		Set<Device> deviceSet = new HashSet<>(devices);
		group.assignDevices(deviceSet);
		return repository.save(group);
	}
}
