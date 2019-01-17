package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService implements Register<Device> {

	DeviceRepository repository;

	@Autowired
	public DeviceService(DeviceRepository repository) {
		this.repository = repository;
	}

	@Override
	public Device register(Device device) {
		return repository.save(device);
	}

	public Device findByName(String name) {
		return repository.findByName(name);
	}
}
