package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class})
public class DeviceRepositoryTest {

	@Autowired
	DeviceRepository deviceRepository;

	@Test
	@Transactional
	public void countEmptyDatabaseTest() {
		assertEquals(0, deviceRepository.count());
	}

	@Test
	public void countTwoSavedDevicesBookTest() {
		Device first = new Device("Device 1");
		Device second = new Device("Device 2");
		deviceRepository.save(first);
		deviceRepository.save(second);

		assertEquals(2, deviceRepository.count());
	}

	@Test
	public void saveAndRetrieveEntityTest() {
		Device originalDevice = new Device("Device 1");
		deviceRepository.save(originalDevice);
		Device actualDevice = deviceRepository.findByName(originalDevice.getName());

		assertNotNull(actualDevice);
		assertEquals(originalDevice, actualDevice);
	}

	@Test
	public void findExistingDeviceByNameTest() {
		String deviceName = "Device";
		Device originalDevice = new Device(deviceName);
		deviceRepository.save(originalDevice);

		Device actualDevice = deviceRepository.findByName(deviceName);

		assertNotNull(actualDevice);
		assertEquals(originalDevice, actualDevice);
	}

	@Test
	public void findNonExistingDeviceByNameTest() {
		String deviceName = "Device";
		Device originalDevice = new Device(deviceName);
		deviceRepository.save(originalDevice);

		Device actualDevice = deviceRepository.findByName("Non existing device");

		assertNull(actualDevice);
	}

	@Test
	public void findAllDevicesByNamesTest() {
		Device originalDeviceOne = new Device("Device 1");
		Device originalDeviceTwo = new Device("Device 2");
		List<Device> originalDevices = Arrays.asList(originalDeviceOne, originalDeviceTwo);
		deviceRepository.save(originalDeviceOne);
		deviceRepository.save(originalDeviceTwo);

		List<Device> actualDevices = deviceRepository.findByNameIn(new String[]{"Device 1", "Device 2"});

		assertEquals(2, actualDevices.size());
		assertEquals(originalDevices, actualDevices);
	}

	@Before
	@After
	public void cleanDb() {
		deviceRepository.deleteAll();
	}
}