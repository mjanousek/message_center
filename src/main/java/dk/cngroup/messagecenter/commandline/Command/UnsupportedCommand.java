package dk.cngroup.messagecenter.commandline.Command;

import java.util.List;

public class UnsupportedCommand extends Command {

	public UnsupportedCommand(List<String> params) {
		super(params);
	}

	public UnsupportedCommand() {
		super();
	}

	@Override
	public void execute() {
		System.out.println("Command is not supported. Run HELP for list of supported commands.");
	}
}
