package dk.cngroup.messagecenter.commandline.command;

import java.util.List;

public class KeywordCommand extends Command {

	public KeywordCommand(List<String> params) {
		super(params);
	}

	@Override
	public void execute() {
		keywordApiService.add(params.get(0));
	}
}
