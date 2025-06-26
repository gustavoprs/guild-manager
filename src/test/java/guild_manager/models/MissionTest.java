package guild_manager.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

public class MissionTest {

	@Test
	void testAddParticipant(){
		Mission mission = new Mission("Kill 10 goblins", "Eliminate 10 goblins threatening the village", "Active");
		Adventurer adventurer = new Adventurer("Lana", 10, "Mage");

		mission.addParticipant(adventurer);

		assertTrue(mission.getParticipants().contains(adventurer));
	}

	@Test
	void testIsAdvIn(){
		Mission mission = new Mission("Kill 10 goblins", "Eliminate 10 goblins threatening the village", "Active");
		Adventurer adventurer = new Adventurer("Lana", 10, "Mage");

		mission.addParticipant(adventurer);
		
		assertTrue(mission.isAdvIn(adventurer));
	}

}
