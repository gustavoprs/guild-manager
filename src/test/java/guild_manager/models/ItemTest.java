package guild_manager.models;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class ItemTest {

  private Adventurer adventurer1;
  private Adventurer adventurer2;

  @BeforeEach
  public void setUp() {
    adventurer1 = mock(Adventurer.class);
    adventurer2 = mock(Adventurer.class);

    when(adventurer1.getName()).thenReturn("Nero");
    when(adventurer2.getName()).thenReturn("Vergil");
  }

  @Test
  @DisplayName("Testa descrição de item")
  public void itemDescriptionTest() {
    Item item = new Item("Blue Rose", "Gun", adventurer1);
    Assertions.assertEquals("The item Blue Rose of type Gun belongs to the adventurer " + adventurer1.getName(), item.describe());
  }

  @Test
  @DisplayName("Testa troca de dono")
  public void itemOwnerSwitch() {
    Item item = new Item("Yamato", "Katana", adventurer1);

    item.setOwner(adventurer2);

    Assertions.assertEquals(adventurer2, item.getOwner());
  }

}
