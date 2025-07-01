package guild_manager.dao;

import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guild_manager.models.Item;
import guild_manager.models.Adventurer;

import org.junit.jupiter.api.DisplayName;

public class ItemDAOUnitTest {
  
  private EntityManager entityManager;
  private Adventurer advMock;
  private ItemDAO itemDAO;

  @BeforeEach
  public void setup() {
    entityManager = mock(EntityManager.class);
    advMock = mock(Adventurer.class);    
    itemDAO = new ItemDAO(entityManager);
  }

  @Test
  @DisplayName("Testa chamado de persist")
  public void testCallPersist() {
    Item item = new Item("Ebony&Ivory", "GUNS", advMock);

    itemDAO.save(item);
    
    verify(entityManager, times(1)).persist(item);
  }

  @Test
  @DisplayName("Testa chamado de merge")
  public void testCallMerge() {
    Item item = new Item("Yamato", "KATANA", advMock);

    itemDAO.update(item);

    verify(entityManager, times(1)).merge(item);
  }

  @Test
  @DisplayName("Testa chamado de find")
  public void testCallFind() {
    int id = 1;
    itemDAO.getById(id);
    verify(entityManager).find(Item.class, id);
  }

  @Test
  @DisplayName("Testa chamado de createQuery")
  public void testCallCreateQuery() {
    @SuppressWarnings("unchecked")
		TypedQuery<Item> mockQuery = mock(TypedQuery.class);

    when(entityManager.createQuery("FROM Item", Item.class)).thenReturn(mockQuery);
    itemDAO.getAll();

    verify(entityManager).createQuery("FROM Item", Item.class);
  }

  @Test
  @DisplayName("Testa deletar objeto quando já está sendo gerenciado")
  public void testDeleteWhenManaged() {
    Item item = new Item("Red Queen", "Longsword", advMock);
    
    when(entityManager.contains(item)).thenReturn(true);
    itemDAO.delete(item);

    verify(entityManager, times(1)).remove(item);
  }

  @Test
  @DisplayName("Testa deletar objeto quando ainda não está sendo gerenciado")
  public void testDeleteWhenNotManaged() {
    Item item = new Item("Red Queen", "Longsword", advMock);
    Item mergedAdv = new Item("Red Queen", "Longsword", advMock);

    when(entityManager.contains(item)).thenReturn(false);
    when(entityManager.merge(item)).thenReturn(mergedAdv);
    itemDAO.delete(item);

    verify(entityManager).merge(item);
    verify(entityManager).remove(mergedAdv);
  }

}
