package guild_manager.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guild_manager.models.Mission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MissionDAOIntegrationTest {

	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private MissionDAO missionDAO;

	@BeforeAll
	static void setupClass(){
		entityManagerFactory = Persistence.createEntityManagerFactory("test");
	}

	@AfterAll
	static void tearDownClass(){
		entityManagerFactory.close();
	}

	@BeforeEach
	void setUp(){
		entityManager = entityManagerFactory.createEntityManager();
		missionDAO = new MissionDAO(entityManager);
	}

	@AfterEach
	void tearDown(){
		if(entityManager.isOpen()) {
				entityManager.close();
		}
	}

	@Test
	void testSaveAndGetById(){
		Mission mission = new Mission("Kill 10 goblins", "Eliminate 10 goblins threatening the village", "Pending");
		
		entityManager.getTransaction().begin();
		missionDAO.save(mission);
		entityManager.getTransaction().commit();

		Mission missionFound = missionDAO.getById(mission.getId());
		assertNotNull(missionFound);
		assertEquals("Kill 10 goblins", missionFound.getTitle());
	}

	@Test
	void testUpdate(){
		Mission mission = new Mission("Kill 10 goblins", "Eliminate 10 goblins threatening the villag", "Pending");

		entityManager.getTransaction().begin();
		missionDAO.save(mission);
		entityManager.getTransaction().commit();
		
		mission.setStatus("Finished");
		
		entityManager.getTransaction().begin();
		missionDAO.update(mission);
		entityManager.getTransaction().commit();

		Mission updatedMission = missionDAO.getById(mission.getId());
		assertEquals("Finished", updatedMission.getStatus());
	}

	@Test
	void testDelete(){
		Mission mission = new Mission("Kill 10 goblins", "Eliminate 10 goblins threatening the village", "Pending");

		entityManager.getTransaction().begin();
		missionDAO.save(mission);
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		missionDAO.delete(mission);
		entityManager.getTransaction().commit();

		Mission deletedMission = missionDAO.getById(mission.getId());
		assertNull(deletedMission);
	}

}
