package dk.cngroup.messagecenter.commandline;

import dk.cngroup.messagecenter.commandline.Command.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommandFactory {

	public Command createCommand(String input) {
		String[] parts = input.split(" ");
		String command = parts[0];
		List<String> params = getParams(parts);
		if (command.equalsIgnoreCase("HELP")) {
			return new HelpCommand();
		} else if (command.equalsIgnoreCase("REG_D") && params.size() == 1) {
			return new RegisterDeviceCommand(params);
		} else if (command.equalsIgnoreCase("REG_G") && params.size() == 1) {
			return new RegisterGroupCommand(params);
		} else if (command.equalsIgnoreCase("ASG") && params.size() == 2) {
			return new AssignCommand(params);
		} else if (command.equalsIgnoreCase("KEY") && params.size() == 2) {
			return new KeywordCommand(params);
		} else if (command.equalsIgnoreCase("MSG_P2P") && params.size() == 3) {
			return new PeerToPeerCommand(params);
		} else if (command.equalsIgnoreCase("MSG_BRO") && params.size() == 2) {
			return new BroadcastCommand(params);
		} else if (command.equalsIgnoreCase("MSG_MUL") && params.size() == 3) {
			return new MulticastCommand(params);
		} else {
			return new UnsupportedCommand();
		}
	}

	private List<String> getParams(String[] parts) {
		List<String> params = Arrays.asList(parts);
		if (params.size() > 0) {
			params.remove(0);
		}
		return params;
	}
}
