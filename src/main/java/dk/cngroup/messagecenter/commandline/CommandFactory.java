package dk.cngroup.messagecenter.commandline;

import dk.cngroup.messagecenter.commandline.command.*;
import dk.cngroup.messagecenter.service.api.KeywordApiService;
import dk.cngroup.messagecenter.service.api.MessageApiService;
import dk.cngroup.messagecenter.service.api.RegisterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommandFactory {

	@Autowired
	protected KeywordApiService keywordApiService;

	@Autowired
	protected MessageApiService messageApiService;

	@Autowired
	protected RegisterApiService registerApiService;

	public Command createCommand(String input) {
		String[] parts = input.split(" ");
		String command = parts[0];
		List<String> params = getParams(parts);
		if (command.equalsIgnoreCase("HELP")) {
			return initCommand(new HelpCommand(params));
		} else if (command.equalsIgnoreCase("REG_D") && params.size() == 1) {
			return initCommand(new RegisterDeviceCommand(params));
		} else if (command.equalsIgnoreCase("REG_G") && params.size() == 1) {
			return initCommand(new RegisterGroupCommand(params));
		} else if (command.equalsIgnoreCase("ASG") && params.size() == 2) {
			return initCommand(new AssignCommand(params));
		} else if (command.equalsIgnoreCase("KEY") && params.size() == 1) {
			return initCommand(new KeywordCommand(params));
		} else if (command.equalsIgnoreCase("MSG_P2P") && params.size() >= 3) {
			return initCommand(new PeerToPeerCommand(params));
		} else if (command.equalsIgnoreCase("MSG_BRO") && params.size() >= 2) {
			return initCommand(new BroadcastCommand(params));
		} else if (command.equalsIgnoreCase("MSG_MUL") && params.size() >= 3) {
			return initCommand(new MulticastCommand(params));
		} else {
			return initCommand(new UnsupportedCommand(params));
		}
	}

	private Command initCommand(Command command) {
		command.setDependencies(keywordApiService, messageApiService, registerApiService);
		return command;
	}

	private List<String> getParams(String[] commandWithParams) {
		List<String> params = new LinkedList<>(Arrays.asList(commandWithParams));
		if (params.size() > 0) {
			params.remove(0);
		}
		return params;
	}
}
