package dk.cngroup.messagecenter.service.api;

import dk.cngroup.messagecenter.model.Keyword;
import dk.cngroup.messagecenter.service.entity.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeywordApiService {

	@Autowired
	KeywordService service;

	public Keyword add(String keyword) {
		return service.add(new Keyword(keyword));
	}
}
