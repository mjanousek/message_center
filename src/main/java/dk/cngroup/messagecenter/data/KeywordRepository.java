package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {
	List<Keyword> findByWordIn(List<String> keywordsFound);
}
