package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class MulticastCommand extends Command {

	public MulticastCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		String sender = params.get(0);
		String group = params.get(1);
		String message = String.join(" ", params.subList(2, params.size()));
		messageApiService.multicast(sender, group, message);
	}
}
