package dk.cngroup.messagecenter.service.entity;

import dk.cngroup.messagecenter.data.LogRepository;
import dk.cngroup.messagecenter.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

	LogRepository repository;

	@Autowired
	public LogService(LogRepository repository) {
		this.repository = repository;
	}

	public void log(String message) {
		Log log = new Log(message);
		repository.save(log);
	}
}
