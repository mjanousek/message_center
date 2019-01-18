package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
	Device findByName(String name);
	List<Device> findByNameIn(String[] names);
}
