package guild_manager.dao;

import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guild_manager.models.Mission;

import org.junit.jupiter.api.DisplayName;

public class MissionDAOUnitTest {
  
  private EntityManager entityManager;
  private MissionDAO missionDAO;

  @BeforeEach
  public void setup() {
    entityManager = mock(EntityManager.class);
    missionDAO = new MissionDAO(entityManager);
  }

  @Test
  @DisplayName("Testa chamado de persist")
  public void testCallPersist() {
    Mission mission = new Mission("Bloody Palace", "Elimine hordas de demônios", "Ativa");

    missionDAO.save(mission);
    
    verify(entityManager, times(1)).persist(mission);
  }

  @Test
  @DisplayName("Testa chamado de merge")
  public void testCallMerge() {
    Mission mission = new Mission("Yamato's Seal", "Selar o poder da Yamato", "Concluída");

    missionDAO.update(mission);

    verify(entityManager, times(1)).merge(mission);
  }

  @Test
  @DisplayName("Testa chamado de find")
  public void testCallFind() {
    int id = 1;
    missionDAO.getById(id);
    verify(entityManager).find(Mission.class, id);
  }

  @Test
  @DisplayName("Testa chamado de createQuery")
  public void testCallCreateQuery() {
    TypedQuery<Mission> mockQuery = mock(TypedQuery.class);

    when(entityManager.createQuery("FROM Mission", Mission.class)).thenReturn(mockQuery);
    missionDAO.getAll();

    verify(entityManager).createQuery("FROM Mission", Mission.class);
  }

  @Test
  @DisplayName("Testa deletar objeto quando já está sendo gerenciado")
  public void testDeleteWhenManaged() {
    Mission mission = new Mission("Demonic Gate", "Feche o portal infernal", "Falhou");
    
    when(entityManager.contains(mission)).thenReturn(true);
    missionDAO.delete(mission);

    verify(entityManager, times(1)).remove(mission);
  }

  @Test
  @DisplayName("Testa deletar objeto quando ainda não está sendo gerenciado")
  public void testDeleteWhenNotManaged() {
    Mission mission = new Mission("Demonic Gate", "Feche o portal infernal", "Falhou");
    Mission mergedAdv = new Mission("Demonic Gate", "Feche o portal infernal", "Falhou");

    when(entityManager.contains(mission)).thenReturn(false);
    when(entityManager.merge(mission)).thenReturn(mergedAdv);
    missionDAO.delete(mission);

    verify(entityManager).merge(mission);
    verify(entityManager).remove(mergedAdv);
  }

}
