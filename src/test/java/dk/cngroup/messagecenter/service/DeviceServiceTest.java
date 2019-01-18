package dk.cngroup.messagecenter.service;

import dk.cngroup.messagecenter.data.DeviceRepository;
import dk.cngroup.messagecenter.model.Device;
import dk.cngroup.messagecenter.service.entity.DeviceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DeviceServiceTest {

	@InjectMocks
	DeviceService deviceService;

	@Mock
	DeviceRepository deviceRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findByNameReturnsDeviceInstanceTest(){
		Device dummyDevice = new Device("Dummy device");
		when(deviceRepository.findByName(anyString())).thenReturn(dummyDevice);

		Device result = deviceService.findByName("Dummy device");

		assertEquals(result, dummyDevice);
	}

	@Test
	public void registerUnregisteredDeviceTest(){
		when(deviceRepository.existsById(any())).thenReturn(false);
		when(deviceRepository.existsById("Registered device")).thenReturn(true);

		deviceService.register(new Device("Dummy device"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void registerRegisteredDeviceTest(){
		when(deviceRepository.existsById(any())).thenReturn(false);
		when(deviceRepository.existsById("Registered device")).thenReturn(true);

		deviceService.register(new Device("Registered device"));
	}
}