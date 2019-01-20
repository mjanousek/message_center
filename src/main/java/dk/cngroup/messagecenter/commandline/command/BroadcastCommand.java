package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class BroadcastCommand extends Command {

	public BroadcastCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		String sender = params.get(0);
		String message = String.join(" ", params.subList(1, params.size()));
		messageApiService.broadcast(sender, message);
	}
}
