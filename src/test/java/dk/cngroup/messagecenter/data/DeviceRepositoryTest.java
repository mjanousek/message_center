package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dk.cngroup.messagecenter.TestUtils.DEVICE_NAME;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class, ObjectGenerator.class})
public class DeviceRepositoryTest {

	@Autowired
	DeviceRepository deviceRepository;

	@Autowired
	private ObjectGenerator generator;

	@Before
	@After
	public void cleanDb() {
		deviceRepository.deleteAll();
	}

	@Test
	public void countEmptyDatabaseTest() {
		assertEquals(0, deviceRepository.count());
	}

	@Test
	public void countTwoSavedDevicesBookTest() {
		List<Device> devices = generator.generateDeviceList(2, DEVICE_NAME);
		devices.forEach(deviceRepository::save);

		assertEquals(2, deviceRepository.count());
	}

	@Test
	public void findExistingDeviceByNameTest() {
		Device originalDevice = new Device(DEVICE_NAME);
		deviceRepository.save(originalDevice);
		Device actualDevice = deviceRepository.findByName(originalDevice.getName());

		assertNotNull(actualDevice);
		assertEquals(originalDevice, actualDevice);
	}

	@Test
	public void findNonExistingDeviceByNameTest() {
		Device originalDevice = new Device(DEVICE_NAME);
		deviceRepository.save(originalDevice);
		Device actualDevice = deviceRepository.findByName("Non existing device");

		assertNull(actualDevice);
	}

	@Test
	public void findAllDevicesByNamesTest() {
		List<Device> originalDevices = generator.generateDeviceList(2, DEVICE_NAME);
		String[] deviceNames = originalDevices.stream().map(Device::getName).toArray(String[]::new);
		originalDevices.forEach(deviceRepository::save);
		List<Device> actualDevices = deviceRepository.findByNameIn(deviceNames);

		assertEquals(2, actualDevices.size());
		assertEquals(originalDevices, actualDevices);
	}
}