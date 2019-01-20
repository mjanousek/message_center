package dk.cngroup.messagecenter.service.entity;

import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.model.Device;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DeviceServiceTest {

	private static final String DEVICE = "Device";
	private static final String REGISTERED_DEVICE = "Registered device";

	@InjectMocks
	DeviceService service;

	@Mock
	DeviceRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findByNameReturnsDeviceInstanceTest() {
		Device device = new Device(DEVICE);
		when(repository.findByName(anyString())).thenReturn(device);
		Device result = service.findByName(DEVICE);

		assertEquals(result, device);
	}

	@Test
	public void registerUnregisteredDeviceTest() {
		when(repository.existsById(any())).thenReturn(false);
		when(repository.existsById(REGISTERED_DEVICE)).thenReturn(true);

		service.register(new Device(DEVICE));
	}

	@Test(expected = IllegalArgumentException.class)
	public void registerRegisteredDeviceTest() {
		when(repository.existsById(any())).thenReturn(false);
		when(repository.existsById(REGISTERED_DEVICE)).thenReturn(true);

		service.register(new Device(REGISTERED_DEVICE));
	}
}