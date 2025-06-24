package guild_manager.dao;

import java.util.List;

import guild_manager.models.Mission;
import jakarta.persistence.EntityManager;

public class MissionDAO {
	
	private EntityManager entityManager;

	public MissionDAO(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Mission getById(int id){
		return entityManager.find(Mission.class, id);
	}

	public List<Mission> getAll(){
		return entityManager.createQuery("FROM Mission", Mission.class).getResultList();
	}

	public void save(Mission mission){
		entityManager.persist(mission);
	}

	public Mission update(Mission mission){
		return entityManager.merge(mission);
	}

	public void delete(Mission mission){
		if(!entityManager.contains(mission)){
			mission = entityManager.merge(mission);
		}

		entityManager.remove(mission);
	}

}
