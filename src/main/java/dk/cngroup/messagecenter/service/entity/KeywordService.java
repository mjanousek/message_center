package dk.cngroup.messagecenter.service.entity;

import dk.cngroup.messagecenter.data.KeywordRepository;
import dk.cngroup.messagecenter.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService {

	KeywordRepository repository;

	@Autowired
	public KeywordService(KeywordRepository repository) {
		this.repository = repository;
	}

	public Keyword add(Keyword device) {
		return repository.save(device);
	}

	public List<Keyword> findAll() {
		return repository.findAll();
	}

	public List<Keyword> findByWordIn(List<String> keywordsFound) {
		return repository.findByWordIn(keywordsFound);
	}
}
