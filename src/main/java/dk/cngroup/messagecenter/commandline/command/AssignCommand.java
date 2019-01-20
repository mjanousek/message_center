package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class AssignCommand extends Command {

	public AssignCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		String groupId = params.get(0);
		String devicesString = params.get(1);
		String[] deviceIds = devicesString.split(",");
		registerApiService.assignDevicesToGroup(groupId, deviceIds);
	}
}
