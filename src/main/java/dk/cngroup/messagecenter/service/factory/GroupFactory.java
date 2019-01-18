package dk.cngroup.messagecenter.service.factory;

import dk.cngroup.messagecenter.model.Group;

public class GroupFactory {
	public static Group createGroup(String name) {
		return new Group(name);
	}
}
