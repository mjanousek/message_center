package dk.cngroup.messagecenter.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;

	@Id
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "groups_devices",
			joinColumns = {@JoinColumn(name = "group_id")},
			inverseJoinColumns = {@JoinColumn(name = "device_id")})
	private Set<Device> devices = new HashSet<>();

	public Group() {
	}

	public Group(String name) {
		this.name = name;
	}

	public Group(String name, Set<Device> devices) {
		this.name = name;
		this.devices = devices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public void assignDevice(Device deviceOne) {
		devices.add(deviceOne);
	}

	public void assignDevices(Set<Device> devices) {
		this.devices.addAll(devices);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Group group = (Group) o;
		return Objects.equals(name, group.name) &&
				Objects.equals(devices, group.devices);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, devices);
	}
}
