package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class HelpCommand extends Command {

	public HelpCommand(List<String> params) {
		super(params);
	}

	public HelpCommand() {
		super();
	}

	@Override
	public void execute() {
		System.out.println("Help - commands:\n" +
				"\t- HELP\n" +
				"\t- EXIT" +
				"\t- REG_G <group_name>\n" +
				"\t- REG_D <device_name>\n" +
				"\t- ASG <group_name> <device_name>[,<device_name>]\n" +
				"\t- KEY <keyword>\n" +
				"\t- MSG_P2P <sender> <receiver> <message>\n" +
				"\t- MSG_BRO <sender> <message>\n" +
				"\t- MSG_MUL <sender> <group> <message>");
	}
}
