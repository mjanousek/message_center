package dk.cngroup.messagecenter.service.entity;

import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService implements Register<Device> {

	DeviceRepository repository;

	@Autowired
	public DeviceService(DeviceRepository repository) {
		this.repository = repository;
	}

	@Override
	public Device register(Device device) {
		if (repository.existsById(device.getName())) {
			throw new IllegalArgumentException("Device '" + device.getName() + "' is already registered");
		}
		return repository.save(device);
	}

	public Device findByName(String name) {
		return repository.findByName(name);
	}

	public List<Device> findByNames(String... names) {
		return repository.findByNameIn(names);
	}

	public List<Device> findAll() {
		return repository.findAll();
	}


}
