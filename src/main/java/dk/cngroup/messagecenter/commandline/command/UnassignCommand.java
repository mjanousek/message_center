package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class UnassignCommand extends Command {

	public UnassignCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		String groupId = params.get(0);
		String devicesString = params.get(1);
		String[] deviceIds = devicesString.split(",");
		registerApiService.unassignDevicesFromGroup(groupId, deviceIds);
	}
}
