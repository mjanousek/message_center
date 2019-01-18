package dk.cngroup.messagecenter.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "devices")
public class Device {

	@Id
	private String name;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "devices")
	private Set<Group> groups = new HashSet<>();

	public Device() {
	}

	public Device(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "Device{" +
				"name='" + name + '\'' +
				'}';
	}

	@Override
	// We discussed that id is deprecated
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Device device = (Device) o;
		return Objects.equals(name, device.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
