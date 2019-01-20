package dk.cngroup.messagecenter;

public class TestUtils {

	public static final String GROUP_NAME = "Group";
	public static final String DEVICE_NAME = "Device";

	public static String getName(String name, int number) {
		return name + number;
	}

	public static String getGroupName(int number) {
		return getName(GROUP_NAME, number);
	}

	public static String getDeviceName(int number) {
		return getName(DEVICE_NAME, number);
	}
}
