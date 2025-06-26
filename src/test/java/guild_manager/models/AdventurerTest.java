package guild_manager.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

public class AdventurerTest {

	@Test
	void testLevelUp(){
		Adventurer adventurer = new Adventurer("Lana", 10, "Mage");
		adventurer.levelUp();

		assertEquals(11, adventurer.getLevel());
	}

	@Test
	void testAddItem(){
		Adventurer adventurer = new Adventurer("John", 10, "Tank");
		Item axe = new Item("Battle axe", "Weapon");

		adventurer.addItem(axe);

		assertEquals(1, adventurer.getItems().size());
		assertTrue(adventurer.getItems().contains(axe));
		assertEquals(adventurer, axe.getOwner());
	}

}
