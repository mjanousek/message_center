package dk.cngroup.messagecenter.service.factory;

import dk.cngroup.messagecenter.model.Device;

public class DeviceFactory {
	public static Device createDevice(String name) {
		return new Device(name);
	}
}
