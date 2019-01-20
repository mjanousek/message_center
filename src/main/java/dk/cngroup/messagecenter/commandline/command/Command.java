package dk.cngroup.messagecenter.commandline.command;

import dk.cngroup.messagecenter.service.api.KeywordApiService;
import dk.cngroup.messagecenter.service.api.MessageApiService;
import dk.cngroup.messagecenter.service.api.RegisterApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class Command {

	protected KeywordApiService keywordApiService;
	protected MessageApiService messageApiService;
	protected RegisterApiService registerApiService;

	protected List<String> params;

	public Command(List<String> params) {
		this.params = params;
	}

	public Command() {
	}

	public abstract void execute();

	public List<String> getParams() {
		return params;
	}

	public void setDependencies(
			KeywordApiService keywordApiService,
			MessageApiService messageApiService,
			RegisterApiService registerApiService
	) {
		this.keywordApiService = keywordApiService;
		this.messageApiService = messageApiService;
		this.registerApiService = registerApiService;
	}
}
