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
import guild_manager.models.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ItemDAOIntegrationTest {

	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private ItemDAO itemDAO;

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
		itemDAO = new ItemDAO(entityManager);
	}

	@AfterEach
	void tearDown(){
		if(entityManager.isOpen()) {
				entityManager.close();
		}
	}

	@Test
	void testSaveAndGetById(){
		Item item = new Item("Sword", "Weapon", null);
		
		entityManager.getTransaction().begin();
		itemDAO.save(item);
		entityManager.getTransaction().commit();

		Item itemFound = itemDAO.getById(item.getId());
		assertNotNull(itemFound);
		assertEquals("Sword", itemFound.getName());
	}

	@Test
	void testUpdate(){
		Adventurer adventurer = new Adventurer("John", 5, "Rogue");
		Adventurer adventurer2 = new Adventurer("Lana", 8, "Rogue");

    entityManager.getTransaction().begin();
    entityManager.persist(adventurer); 
    entityManager.persist(adventurer2);
    entityManager.getTransaction().commit();

		Item item = new Item("Sword", "Weapon", adventurer);

		entityManager.getTransaction().begin();
		itemDAO.save(item);
		entityManager.getTransaction().commit();
		
		item.setOwner(adventurer2);
		
		entityManager.getTransaction().begin();
		itemDAO.update(item);
		entityManager.getTransaction().commit();

		Item updatedItem = itemDAO.getById(item.getId());
		assertEquals("Lana", updatedItem.getOwner().getName());
	}

	@Test
	void testDelete(){
		Item item = new Item("Sword", "Weapon", null);

		entityManager.getTransaction().begin();
		itemDAO.save(item);
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		itemDAO.delete(item);
		entityManager.getTransaction().commit();

		Item deletedItem = itemDAO.getById(item.getId());
		assertNull(deletedItem);
	}

}
