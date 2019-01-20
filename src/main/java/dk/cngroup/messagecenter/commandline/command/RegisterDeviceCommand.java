package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class RegisterDeviceCommand extends Command {

	public RegisterDeviceCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		registerApiService.registerDevice(params.get(0));
	}
}
