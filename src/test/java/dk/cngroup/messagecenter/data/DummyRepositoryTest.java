package dk.cngroup.messagecenter.data;

import dk.cngroup.messagecenter.MessageCenterApplication;
import dk.cngroup.messagecenter.config.DataConfig;
import dk.cngroup.messagecenter.model.Dummy;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessageCenterApplication.class, DataConfig.class})
public class DummyRepositoryTest {

	@Autowired
	DummyRepository dummyRepository;

	@Test
	public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
		Dummy genericEntity = dummyRepository
				.save(new Dummy("test"));
		Dummy foundEntity = dummyRepository
				.getOne(genericEntity.getId());

		assertNotNull(foundEntity);
		assertEquals(genericEntity.getMsg(), foundEntity.getMsg());
	}

	@Test
	@Transactional
	public void countEmptyDatabaseTest() {
		assertEquals(0, dummyRepository.count());
	}

	@Test
	public void oneSavedBookTest() {
		Dummy toSave = new Dummy("Prvni");
		dummyRepository.save(toSave);
		assertEquals(1, dummyRepository.count());
		assertNotNull(toSave.getId());

		Optional<Dummy> returnedFromDb = dummyRepository.findById(toSave.getId());
		assertEquals(toSave, returnedFromDb.get());

	}

	@After
	public void cleanDb() {
		dummyRepository.deleteAll();
	}
}