package guild_manager.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guild_manager.models.Adventurer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AdventurerDAOIntegrationTest {

	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private AdventurerDAO adventurerDAO;

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
		adventurerDAO = new AdventurerDAO(entityManager);
	}

	@AfterEach
	void tearDown(){
		entityManager = entityManagerFactory.createEntityManager();
		adventurerDAO = new AdventurerDAO(entityManager);
	}

	@Test
	void testSaveAndGetById(){
		Adventurer adventurer = new Adventurer("Lana", 10, "Mage");
		
		entityManager.getTransaction().begin();
		adventurerDAO.save(adventurer);
		entityManager.getTransaction().commit();

		Adventurer adventurerFound = adventurerDAO.getById(adventurer.getId());
		assertNotNull(adventurerFound);
		assertEquals("Lana", adventurerFound.getName());
	}

	@Test
	void testUpdate(){
		Adventurer adventurer = new Adventurer("Lana", 10, "Mage");

		entityManager.getTransaction().begin();
		adventurerDAO.save(adventurer);
		entityManager.getTransaction().commit();
		
		adventurer.setRole("Archer");
		
		entityManager.getTransaction().begin();
		adventurerDAO.update(adventurer);
		entityManager.getTransaction().commit();

		Adventurer updatedAdventurer = adventurerDAO.getById(adventurer.getId());
		assertEquals("Archer", updatedAdventurer.getRole());
	}

	@Test
	void testDelete(){
		Adventurer adventurer = new Adventurer("Lana", 10, "Mage");

		entityManager.getTransaction().begin();
		adventurerDAO.save(adventurer);
		entityManager.getTransaction().commit();


		entityManager.getTransaction().begin();
		adventurerDAO.delete(adventurer);
		entityManager.getTransaction().commit();

		Adventurer deletedAdventurer = adventurerDAO.getById(adventurer.getId());
		assertNull(deletedAdventurer);
	}

}
