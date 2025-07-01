package guild_manager;

import java.util.List;
import java.util.Scanner;

import guild_manager.dao.AdventurerDAO;
import guild_manager.dao.ItemDAO;
import guild_manager.dao.MissionDAO;
import guild_manager.models.Adventurer;
import guild_manager.models.Item;
import guild_manager.models.Mission;
import guild_manager.utils.InputHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder.In;

public class Main {

	private static EntityManager entityManager = Persistence.createEntityManagerFactory("guild-manager").createEntityManager();
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		try{
			mainMenu();
		} finally{
			scanner.close();
			entityManager.close();
		}
	}

	public static void mainMenu(){
		int option;

		do {
			System.out.println("\n--- MAIN MENU ---");
			System.out.println("[1] Adventurers Menu");
			System.out.println("[2] Itens Menu");
			System.out.println("[3] Missions Menu");
			System.out.println("[0] Exit");
			option = InputHandler.readInt(scanner, "Option: ");

			switch (option) {
				case 1:
					adventurersMenu();
					break;
				case 2:
					itemsMenu();
					break;
				case 3:
					missionsMenu();
					break;
				case 0:
					break;
				default:
					System.out.println("\nInvalid option, please chose again.");
					break;
			}

		} while(option != 0);
	}

	public static void adventurersMenu(){
		AdventurerDAO adventurerDAO = new AdventurerDAO(entityManager);
		int option;
		Adventurer adventurer;

		do{
			System.out.println("\n--- ADVENTURERS MENU ---");
			System.out.println("[1] List Adventurers");
			System.out.println("[2] Register Adventurer");
			System.out.println("[3] Unregister Adventurer");
			System.out.println("[0] Back");
			option = InputHandler.readInt(scanner, "Option: ");

			switch (option) {
				case 1:
					List<Adventurer> adventurers = adventurerDAO.getAll();
					if(adventurers.size() == 0){
						System.out.println("\nNo adventurers found.");
						break;
					}

					System.out.println("\nAdventurers ("+adventurers.size()+"):\n");
					for (Adventurer a : adventurers) {
						System.out.println(a.toString());
					}
					break;
				case 2:
					System.out.println();
					String name = InputHandler.readLine(scanner, "Name: ");
					int level;
					do {
						level = InputHandler.readInt(scanner, "Level: ");
						if(level <= 0){
							System.out.println("\nInvalid level, type again.");
						}
					} while (level <= 0);
					String role = InputHandler.readLine(scanner, "Role: ");
					
					adventurer = new Adventurer(name, level, role);

					entityManager.getTransaction().begin();
					adventurerDAO.save(adventurer);
					entityManager.getTransaction().commit();

					System.out.println("\nAdventurer registered!");
					break;
				case 3:
					int id;
				
					do {
						id = InputHandler.readInt(scanner, "\nAdventurer ID (0 to Cancel): "); 
						if(id < 0){
							System.out.println("\nInvalid ID, type again.");
						}
					} while (id < 0);

					if(id == 0){
						System.out.println("\nCanceling...");
						break;
					}

					adventurer = adventurerDAO.getById(id);
					if(adventurer == null){
						System.out.println("Adventurer not found.");
						break;
					}

					System.out.println("\nDeleting adventurer '"+ adventurer.getName() +"'...");

					entityManager.getTransaction().begin();
					adventurerDAO.delete(adventurer);
					entityManager.getTransaction().commit();

					System.out.println("Adventurer deleted!");

					break;
				case 0:
					break;
				default:
					System.out.println("Invalid option, chose again.");
					break;
			}
		} while(option != 0);
	}

	public static void itemsMenu(){
		int option;
		ItemDAO itemDAO = new ItemDAO(entityManager);
		Item item;

		do{
			System.out.println("\n--- ITEMS MENU ---");
			System.out.println("[1] List Items");
			System.out.println("[2] Add Items");
			System.out.println("[3] Remove Items");
			System.out.println("[0] Back");
			option = InputHandler.readInt(scanner, "Option: ");

			switch (option) {
				case 1:
					List<Item> items = itemDAO.getAll();
					if(items.size() == 0){
						System.out.println("\nNo items found.");
						break;
					}

					System.out.println("\nItems ("+items.size()+"):\n");
					for (Item i : items) {
						System.out.println(i.toString());
					}
					break;
				case 2:
					System.out.println();
					String name = InputHandler.readLine(scanner, "Name: ");
					String type = InputHandler.readLine(scanner, "Type: ");
					
					item = new Item(name, type);

					entityManager.getTransaction().begin();
					itemDAO.save(item);
					entityManager.getTransaction().commit();

					System.out.println("\nItem registered!");
					break;
				case 3:
					int id;
				
					do {
						id = InputHandler.readInt(scanner, "\nItem ID (0 to Cancel): "); 
						if(id < 0){
							System.out.println("\nInvalid ID, type again.");
						}

					} while (id < 0);

					if(id == 0){
						System.out.println("\nCanceling...");
						break;
					}

					item = itemDAO.getById(id);
					if(item == null){
						System.out.println("Item not found.");
						break;
					}

					System.out.println("\nDeleting item '"+ item.getName() +"'...");

					entityManager.getTransaction().begin();
					itemDAO.delete(item);
					entityManager.getTransaction().commit();

					System.out.println("Item deleted!");

					break;
				case 0:
					break;
				default:
					System.out.println("Invalid option, chose again.");
					break;
			}
		} while(option != 0);
	}

	public static void missionsMenu(){
		int option;
		MissionDAO missionDAO = new MissionDAO(entityManager);
		Mission mission;

		do{
			System.out.println("\n--- MISSIONS MENU ---");
			System.out.println("[1] List Missions");
			System.out.println("[2] Add Missions");
			System.out.println("[3] Remove Missions");
			System.out.println("[0] Back");
			option = InputHandler.readInt(scanner, "Option: ");

			switch (option) {
				case 1:
					List<Mission> missions = missionDAO.getAll();
					if(missions.size() == 0){
						System.out.println("\nNo missions found.");
						break;
					}

					System.out.println("\nMissions ("+missions.size()+"):\n");
					for (Mission m : missions) {
						System.out.println(m.toString());
					}
					break;
				case 2:
					System.out.println();
					String title = InputHandler.readLine(scanner, "Title: ");
					String description = InputHandler.readLine(scanner, "Description: ");
					
					mission = new Mission(title, description, "Available");

					entityManager.getTransaction().begin();
					missionDAO.save(mission);
					entityManager.getTransaction().commit();

					System.out.println("\nMission registered!");
					break;
				case 3:
					int id;
				
					do {
						id = InputHandler.readInt(scanner, "\nMission ID (0 to Cancel): "); 
						if(id < 0){
							System.out.println("\nInvalid ID, type again.");
						}

					} while (id < 0);

					if(id == 0){
						System.out.println("\nCanceling...");
						break;
					}

					mission = missionDAO.getById(id);
					if(mission == null){
						System.out.println("Item not found.");
						break;
					}

					System.out.println("\nDeleting mission '"+ mission.getTitle() +"'...");

					entityManager.getTransaction().begin();
					missionDAO.delete(mission);
					entityManager.getTransaction().commit();

					System.out.println("Mission deleted!");

					break;
				case 0:
					break;
				default:
					System.out.println("Invalid option, chose again.");
					break;
			}
		} while(option != 0);
	}

}