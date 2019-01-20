package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class PeerToPeerCommand extends Command {

	public PeerToPeerCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		String sender = params.get(0);
		String receiver = params.get(1);
		String message = String.join(" ", params.subList(2, params.size()));
		messageApiService.peerToPeer(sender, receiver, message);
	}
}
