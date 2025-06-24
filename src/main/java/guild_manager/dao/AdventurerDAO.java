package guild_manager.dao;

import java.util.List;

import guild_manager.models.Adventurer;
import jakarta.persistence.EntityManager;

public class AdventurerDAO {
	
	private EntityManager entityManager;

	public AdventurerDAO(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Adventurer getById(int id){
		return entityManager.find(Adventurer.class, id);
	}

	public List<Adventurer> getAll(){
		return entityManager.createQuery("FROM Adventurer", Adventurer.class).getResultList();
	}

	public void save(Adventurer adventurer){
		entityManager.persist(adventurer);
	}

	public Adventurer update(Adventurer adventurer){
		return entityManager.merge(adventurer);
	}

	public void delete(Adventurer adventurer){
		if(!entityManager.contains(adventurer)){
			adventurer = entityManager.merge(adventurer);
		}

		entityManager.remove(adventurer);
	}

}
