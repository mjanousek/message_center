package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	Device findByName(String name);
}
