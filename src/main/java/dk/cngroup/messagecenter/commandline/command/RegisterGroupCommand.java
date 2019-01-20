package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class RegisterGroupCommand extends Command {

	public RegisterGroupCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		registerApiService.registerGroup(params.get(0));
	}
}
