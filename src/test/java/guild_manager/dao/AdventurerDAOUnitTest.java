package guild_manager.dao;

import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guild_manager.models.Adventurer;

import org.junit.jupiter.api.DisplayName;

public class AdventurerDAOUnitTest {
  
  private EntityManager entityManager;
  private AdventurerDAO adventurerDAO;

  @BeforeEach
  public void setup() {
    entityManager = mock(EntityManager.class);
    adventurerDAO = new AdventurerDAO(entityManager);
  }

  @Test
  @DisplayName("Testa chamado de persist")
  public void testCallPersist() {
    Adventurer adv = new Adventurer("Dante", 9, "Trickster");

    adventurerDAO.save(adv);
    
    verify(entityManager, times(1)).persist(adv);
  }

  @Test
  @DisplayName("Testa chamado de merge")
  public void testCallMerge() {
    Adventurer adv = new Adventurer("Vergil", 10, "Samurai");

    adventurerDAO.update(adv);

    verify(entityManager, times(1)).merge(adv);
  }

  @Test
  @DisplayName("Testa chamado de find")
  public void testCallFind() {
    int id = 1;
    adventurerDAO.getById(id);
    verify(entityManager).find(Adventurer.class, id);
  }

  @Test
  @DisplayName("Testa chamado de createQuery")
  public void testCallCreateQuery() {
    @SuppressWarnings("unchecked")
		TypedQuery<Adventurer> mockQuery = mock(TypedQuery.class);

    when(entityManager.createQuery("FROM Adventurer", Adventurer.class)).thenReturn(mockQuery);
    adventurerDAO.getAll();

    verify(entityManager).createQuery("FROM Adventurer", Adventurer.class);
  }

  @Test
  @DisplayName("Testa deletar objeto quando já está sendo gerenciado")
  public void testDeleteWhenManaged() {
    Adventurer adv = new Adventurer("Nero", 7, "Swordsman");
    
    when(entityManager.contains(adv)).thenReturn(true);
    adventurerDAO.delete(adv);

    verify(entityManager, times(1)).remove(adv);
  }

  @Test
  @DisplayName("Testa deletar objeto quando ainda não está sendo gerenciado")
  public void testDeleteWhenNotManaged() {
    Adventurer adv = new Adventurer("Nero", 7, "Swordsman");
    Adventurer mergedAdv = new Adventurer("Nero", 7, "Swordsman");

    when(entityManager.contains(adv)).thenReturn(false);
    when(entityManager.merge(adv)).thenReturn(mergedAdv);
    adventurerDAO.delete(adv);

    verify(entityManager).merge(adv);
    verify(entityManager).remove(mergedAdv);
  }

}
