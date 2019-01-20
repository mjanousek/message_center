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

	public Keyword add(Keyword keyword) {
		if (repository.existsById(keyword.getWord())) {
			throw new IllegalArgumentException("Keyword '" + keyword.getWord() + "' is already in the system");
		}
		return repository.save(keyword);
	}

	public List<Keyword> findAll() {
		return repository.findAll();
	}

	public List<Keyword> findByWordIn(List<String> keywordsFound) {
		return repository.findByWordIn(keywordsFound);
	}
}
