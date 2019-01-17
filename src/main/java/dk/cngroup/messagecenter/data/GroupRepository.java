package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Group findByName(String name);
}
