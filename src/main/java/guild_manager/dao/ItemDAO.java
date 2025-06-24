package guild_manager.dao;

import java.util.List;

import guild_manager.models.Item;
import jakarta.persistence.EntityManager;

public class ItemDAO {
	
	private EntityManager entityManager;

	public ItemDAO(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Item getById(int id){
		return entityManager.find(Item.class, id);
	}

	public List<Item> getAll(){
		return entityManager.createQuery("FROM Item", Item.class).getResultList();
	}

	public void save(Item item){
		entityManager.persist(item);
	}

	public Item update(Item item){
		return entityManager.merge(item);
	}

	public void delete(Item item){
		if(!entityManager.contains(item)){
			item = entityManager.merge(item);
		}

		entityManager.remove(item);
	}

}
