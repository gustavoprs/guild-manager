package guild_manager.utils;

import java.util.Scanner;

public class InputHandler {
	
	public static int readInt(Scanner scanner, String prompt){
		while(true){
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			try {
				return Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("\nInvalid input. Please enter a valid integer.\n");
			} 
		}
	}

	public static String readLine(Scanner scanner, String prompt){
		while(true){
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if(input.length() != 0) {
				return input;
			}
			
			System.out.println("\nInvalid input. Please enter a valid integer.\n");
		}
	}

}
