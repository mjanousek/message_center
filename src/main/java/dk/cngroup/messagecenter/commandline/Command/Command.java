package dk.cngroup.messagecenter.commandline.Command;

import dk.cngroup.messagecenter.service.api.KeywordApiService;
import dk.cngroup.messagecenter.service.api.MessageApiService;
import dk.cngroup.messagecenter.service.api.RegisterApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class Command {
//	HELP("HELP") {
//		@Override
//		public void execute() {
//			System.out.println("Help - commands:\n" +
//					"\t- HELP\n" +
//					"\t- REG_G <group_name>\n" +
//					"\t- REG_D <device_name>\n" +
//					"\t- ASG <group_name> <device_name>[,<device_name>]\n" +
//					"\t- KEY <keyword>\n" +
//					"\t- MSG_P2P <sender> <receiver> <message>\n" +
//					"\t- MSG_BRO <sender> <message>\n" +
//					"\t- MSG_MUL <sender> <group> <message>");
//		}
//	},
//	REGISTER_GROUP("REG_G") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	REGISTER_DEVICE("REG_D") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	ASSIGN("ASG") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	KEYWORD("KEY") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	MESSAGE_PEER_TO_PEER("MSG_P2P") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	MESSAGE_BROADCAST("MSG_BRO") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	MESSAGE_MULTICAST("MSG_MUL") {
//		@Override
//		public void execute() {
//
//		}
//	},
//	UNSUPPORTED("UNSUPPORTED"){
//		@Override
//		public void execute() {
//			System.out.println("Command is not supported. Run HELP for list of supported commands.");
//		}
//	};

	@Autowired private KeywordApiService keywordApiService;
	@Autowired private MessageApiService messageApiService;
	@Autowired private RegisterApiService registerApiService;

	private List<String> params;

	public Command(List<String> params) {
		this.params = params;
	}

	public Command() {
	}

	public abstract void execute();

	public List<String> getParams() {
		return params;
	}
}
