package dk.cngroup.messagecenter.service;

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

	@InjectMocks
	DeviceService deviceService;

	@Mock
	DeviceRepository deviceRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void tindByNameReturnsDeviceInstanceTest(){
		Device dummyDevice = new Device("Dummy device");
		when(deviceRepository.findByName(anyString())).thenReturn(dummyDevice);

		Device result = deviceService.findByName("Dummy device");

		assertEquals(result, dummyDevice);
	}
}