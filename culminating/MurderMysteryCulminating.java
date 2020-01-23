package culminating;

/*
 * Title: Mutual Killing: Who's the Killer?
 * Project Description: This culminating assignment is a murder mystery 
 * text-game, combining storytelling elements with an investigation 
 * player-interactive phase. Once the player discovers all the clues, 
 * they transition into a trial-like event where the player debates against 
 * characters to determine the identity of the killer. In the trial, 
 * there are minigames such as debates where the player counters other 
 * characters' arguments, a hangman game where the player finds
 * the appropriate word for the topic being discussed. It also 
 * contains multiple choice aspects, a question is asked and the 
 * player has 4 answers to choose from. Like the multiple choice 
 * questions, there are certain parts where the player chooses a piece 
 * of evidence, the choice being between the player's entire inventory of
 * evidence. This game is heavily based on the commercialized videogame by Spike
 * Chunsoft, Danganronpa 2: Goodbye Despair. Many elements from that game
 * are recreated in this program, I claim no ownership in any of the ideas
 * within this file other than the program's code.
 * Name: Karen Akune
 * Date: June 15th 2018
 */

// Beginning of program code

// Import Scanner class to monitor keyboard input.
import java.util.Scanner;

public class MurderMysteryCulminating {
	// Declare and/or initialiaze global variables and arrays
	// used in multiple methods.
	// byte error counter variables to keep track of user's tries:
	// Counter array for each debate minigame and difficulty.
	// Gentle difficulty
	static byte debateG[] = {10, 10, 10, 10, 10, 10};
	
	// Counter array for each multiple choice and difficulty.
	// Gentle difficulty
	static byte mChoiceG[] = {3, 3, 3, 3, 3};
	
	// Counter array for each evidence selection and difficulty
	// Gentle difficulty
	static byte eSelectG[] = {10, 10, 10, 10};
	
	// String array inventory; will hold pieces of evidence player finds during investigation.
	// Size declarator of 15; 15 pieces of evidence will be found in the game.
	// static in order to make static references to inventory in other methods.
	static String inventory[] = new String[15];
			
	// Beginning of main method
	public static void main(String[] args) {
		// Scanner variable to monitor keyboard input
		Scanner keyboard = new Scanner(System.in);
		
		// boolean variable guideState; if the player chooses to turn the guide on,
		// gameState remains true. If not, gameState is set to false. Will control whether
		// game guide instructions are displayed during the game.
		// Default state is on (true).
		boolean guideState = true;
		
		// String variable restart; will store user input when prompted to play again.
		String restart;
		
		// do while loop to encase entire game code.
		// Once player reaches end of game, they will be prompted if they want to
		// play again; if yes, they will be taken back to the game guide on/off
		// selection, thus restarting the game. If not, a thank you message will 
		// be displayed, along with credits.
		do {			
			// Call gameGuide method to introduce the game to the player. 
			// gameGuide will return a boolean that will be assigned to guideState
			guideState = gameGuide();
			
			// Wish player good luck before entering them into game.
			System.out.println(); // Skip a line
			System.out.println("You are now ready to begin your investigation. Good luck unravelling this mysterious murder.");
			// Call enter method for player to press enter in order to continue.
			enter();
			
			// Call story method to introduce player to game's plot.
			story();
			
			// Call investigation method to lead player to investigation phase of the game.
			// Accepts boolean guideState to determine whether to show instructions for investigation. 
			investigation(guideState);
			
			// Call trial method after player finishes investigation phase to move on to the main event of the game: the trial.
			trial(guideState);
			
			// Prompt user if they would like to restart the game.
			System.out.println("\nWould you like to participate in the mutual killing again?");
			System.out.print("(If you answer \"yes\" the game will restart back to the game guide screen.) ");
			
			// Store user's choice in restart.
			restart = keyboard.nextLine();
			
			// Input validation while loop that will only accept ignoring case "no" or "yes" as answers.
			while (!(restart.equalsIgnoreCase("no")) && !restart.equalsIgnoreCase("yes")) {
				System.out.println("\nThat is not a valid answer. Please answer \"yes\" or \"no\".");
				System.out.println("Would you like to participate in the mutual killing again?");
				System.out.print("(If you answer \"yes\" the game will restart back to the game guide screen.) ");
				// Store user's answer in answer.
				restart = keyboard.nextLine();
			}
			
		} 
		// Answering "yes" will restart the game back to game guide state selection.
		while (restart.equalsIgnoreCase("yes"));
			
		// Display credits and thank you message.
		System.out.println("\nThank you for playing \"Mutual Killing: Who's the Killer?\".");
		System.out.println();
		System.out.println("Coded by Karen Akune");
		System.out.println("Story, Game Concept Design by Spike Chunsoft");
		System.out.println();
		System.out.println("\"Mutual Killing: Who's the Killer?\" is entirely based on Spike Chunsoft's commmercialized game");
		System.out.println("\"Danganronpa 2: Goodbye Despair\". Karen Akune simply reused Spike Chunsoft's ideas in order to recreate");
		System.out.println("the company's game in a text-based, non-commercialized, condensed version of the original.");
	}

	// Beginning of gameGuide method
	// This method will introduce the game to the user,
	// and ask them if they would like to turn the guide on.
	public static boolean gameGuide() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
		
		// String variable that will hold user's answer.
		String answer;
		
		// boolean variable to hold flag for gameState.
		boolean state = true;

		// Introduce game to user and explain the game guide feature.
		System.out.println("\"Mutual Killing: Who's the Killer?\" is a text-based murder mystery game.");
		System.out.println("There are a few unique minigames throughout the playthrough that require a certain knowledge.");
		System.out.println("(This is the game guide feature; it is on by default. The game guide will instruct you during the game's minigames.)");
		System.out.print("\nDo you possess that knowledge? (If you answer \"Yes\", you will turn off the game guide.) ");
		
		// Store user's answer in answer.
		answer = keyboard.nextLine();
		
		// while loop for input validation, won't accept answers other than ignoring case "no" or "yes".
		while(!(answer.equalsIgnoreCase("no")) && !answer.equalsIgnoreCase("yes")) {
			System.out.print("\nThat is not a valid answer. Please answer \"yes\" or \"no\". ");

			// Store user's answer in answer.
			answer = keyboard.nextLine();
		}
		
		// User chooses to turn game guide off.
		if  (answer.equalsIgnoreCase("yes")) {
			// Apply false to state.
			state = false;
		}
		// Otherwise state will remain true (default game guide on).
		
		// Return state to guideState boolean in main method.
		return state;
	}
	
	// Beginning of enter method
	// In this game, the player will be able to control the speed at which the story dialogue and narration are displayed.
	// After three lines, the game will wait for the player to press enter to continue. This method will be called each time enter is needed.
	public static void enter() {
		// dummyEnter String variable to read enter key input.
		String dummyEnter;
		
		// Scanner variable to store keyboard input.
		Scanner keyboard = new Scanner(System.in);
		
		// Display a "press enter" message to alert player.
		System.out.println("(Press enter to continue)");
		
		// Make computer wait for enter to be pressed before continuing.
		dummyEnter = keyboard.nextLine();
	}
	
	// Beginning of story method
	// When this method is called, it will introduce the player to the setting and story of the game.
	// The story method contains the narrative before the investigation beings.
	public static void story() {
		System.out.println("You were invited to a small party in a hotel by Magnus Nova, the heir of Nova Corporation, the most well-known company "
				+ "\nin the tech industry.");
		enter();
		System.out.println("The list of guests to this party were important people with close connections to the heir.");
		enter();
		System.out.println("You were simply one of Magnus' closest friends, while the other guests were celebrities or "
				+ "\ncelebrated leaders in their respective occupations.");
		// Call enter method to wait for user to press enter before continuing.
		enter();
		
		System.out.println("While you and the other guests held strong relations to the heir, to each other, you were strangers.");
		System.out.println("This fact is all the more chilling when a murder took place.");
		
		enter();
		
		System.out.println("The party began in the hotel's dining hall.");
		System.out.println("Most of the people present in the building were enjoying the festivities there.");
		
		enter();
		
		System.out.println("Suddenly, after you hear a short \"beep\", the dining hall is encased in darkness.");
		System.out.println("A commotion ensues, with many guests speaking at once.");
		System.out.println("After the lights turn back on, there is an empty silence filled with tension.");
		
		enter();
		
		System.out.println("Then, someone screamed. It came from the photographer, Elina Orion. \"Are you okay?\"");
		System.out.println("Who could she be talking about? When the guests look the same direction as Elina, they see someone is on the ground.");
		System.out.println("Face down, unmoving. The nurse, Mariane Telles.");
		
		enter();

		System.out.println("\"W-wha?\" The nurse suddenly raises her head. \"Ohh... How embarrassing! I just fainted because of the blackout...\"");
		System.out.println("Everyone heaved a sigh of relief. It wasn't anything serious.");
		
		enter();
			
		System.out.println("But then, the guests look at each other... And notice someone missing.");
		System.out.println("Where is Magnus, the host of the party?");
		System.out.println("You all split up to look for him.");
		System.out.println("However, someone smells blood in the dining hall.");
				
		enter();
		
		System.out.println("You go towards where they claimed the smell came from, in the back of the room under a table.");	
		System.out.println("You have a sudden feeling of trepidation. When you lift up the table cloth, no one was prepared for the horrible sight.");	
		System.out.println();
		System.out.println("Magnus Nova, face down, laying in a pool of blood, under a table.");

		enter();
				
		System.out.println("\nScreams are let loose.");
				
		System.out.println("In disarray, some guests run away from the dining hall, out of the hotel and towards the closed gates.");
		System.out.println("You follow them and stop at the hotel entrance, where you are able to watch from a distance.");
		System.out.println("You think the guests would then exit through the gates, but to your surprise, they stop in front of them.");
				
		enter();
		System.out.println("A muscular man, Neil Nidas, manages to break the lock, but the gates won't open.");
		System.out.println("The guests were trying to force them open, when Princess Sumia Noah steps forward and takes a taped note from the gates.");
		System.out.println("You jog towards the group of guests, and as you do, the princess begins reading the note out loud.");
				
		enter();
				
		System.out.println("\n\"If you want to leave, you must solve the mystery of Magnus Nova's murder.");
		System.out.println("The killer... Is one of you all who were present.");
				
		enter();
				
		System.out.println("Until you solve the murder, you will all be trapped in the hotel premises.");
		System.out.println("I have prepared a special place for you all to discuss and debate this mystery in the basement!");
		System.out.println("Don't worry, it's just vaguely structured like court trial stands in a circle.");
		System.out.println("You know, for easier debating for you guys!\"");
				
		enter();
				
		System.out.println("The woman's voice begins to tremble in the next few lines:");
		System.out.println("\"Also, if you fail to pinpoint the correct culprit, you will all be executed!");
		System.out.println("But don't fret, if you do find the culprit, only the killer themselves will be executed.");
				
		enter();
				
		System.out.println("Until then, these gates will remain closed, even if you break the lock!");
		System.out.println("Let's have some fun investigating this mutual killing!\"");
			
		enter();
		
		System.out.println("\nThe guests only panic even more.");
		System.out.println("You manage to coax them into reentering the hotel, reasoning that it's best to follow the note for now.");
		System.out.println("This note isn't a prank... You saw someone break the lock, but the gates really won't open.");
		
		enter();
		
		System.out.println("\nIf you want to leave, you will have to investigate this mutual killing.\n");
	}
	
	// Beginning of Evidence Inventory evidence added message method
	// The message will be repeated many times throughout the code.
	public static String invMessage() {
		String message = " has been added to Evidence Inventory.";
		return message;
	}

	// Beginning of investigation method
	// When this method is called, the player will enter the investigation phase of the game,
	// where they will be able to interact with the environment to a certain extent.
	// This is when the player will begin filling their evidence inventory and getting to know the murder case better.
	// Accepts boolean variable to check if game guide is on.
	public static void investigation(boolean guide) {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
		
		// String variable answer to hold user's input
		String answer;
		
		// Skip a line.
		System.out.println();
		
		// If game guide is on, introduce player to investigation phase.
		if(guide) {
			System.out.println("You will now transition into the investigation phase of Mutual Killing.");
			System.out.println("The objective in this phase is to investigate all the rooms in the hotel");
			System.out.println("and obtain evidence for the trial that will take place later on in the game.");
			enter();
		}
		
		// Transition back to the narrative.
		System.out.println(); // Skip a line
		System.out.println("'I must investigate Magnus' death... If what that note says is true,");
		System.out.println("then we will all die unless we find out who the murderer is.'");
		System.out.println("'Is the killer... Really one of us?'");
		
		enter();

		System.out.println("You shake you head. 'No, I can't afford to lose time here.'");
		System.out.println("'I need to find out the truth about this murder!'");
		System.out.println("You enter the hotel and walk back to the scene of the murder.");
		
		enter();

		System.out.println("'I should begin where everything happened.'");
		System.out.println("You see another note taped to the door to the dining hall.");
		System.out.println("It reads:");
		
		enter();

		System.out.println("\"The victim's body was discovered at the dining hall of Hotel Future."
				+ "\nThe time of death was around 11:30PM.");
		enter();
		System.out.println("The cause of death was stabbing with a sharp object.\n"
				+ "The victim was stabbed multiple times in the region between the abdomen and the throat.");
		enter();
		System.out.println("There are no other wounds on the body, nor any traces of poison or other drugs."); 
		
		enter();

		System.out.println("I just thought you'd appreciate some of the basic info about this murder!");
		System.out.println("But that's all you'll get from me! It's your job to find out everything else!\"");
		
		enter();

		System.out.println("That's all it says.");
		System.out.println("'Well, at least it looks like this will be useful.'");
		
		enter();
		
		// Add the clue to the evidence inventory.
		inventory[0] = "Dining Hall Note";
		System.out.println("\"" + inventory[0] + "\"" + invMessage());
		// Describe the evidence
		System.out.println("The victim's body was discovered at the dining hall of Hotel Future." + 
				"\nThe time of death was around 11:30PM.\n\n" +  
				"The cause of death was stabbing with a sharp object.\n" +
				"The victim was stabbed multiple times in the region between the abdomen and the throat.\n\n" + 
				"There are no other wounds on the body, nor any traces of poison or other drugs.\n");
		
		enter();
		
		System.out.println("You enter the dining hall.");
		
		enter();

		System.out.println("You see three of the other guests in the room.");
		System.out.println("One is the photographer, Elina, another an animal breeder, and the other is the nurse Mariane.");
		
		enter();

		System.out.println("'I should ask them if they found out anything.");
		System.out.println("It's probably a good idea to investigate the rest of this room as well.'");
		
		enter();
		
		// while loop that continues until inventory indexes 4, 2, 3, 1, 5, 6, 12, and 13 are filled
		while (inventory[1] == null || inventory[2] == null || inventory[3] == null 
				|| inventory[4] == null || inventory[5] == null || inventory[6] == null 
				|| inventory[12] == null || inventory[13] == null) {
			System.out.println("What would you like to investigate?");
			System.out.print("(Body/Mariane/Elina/Breeder/Air Conditioner/Duralumin Case) ");
			answer = keyboard.nextLine();
		
			// Input validation while loop.
			// Won't accept answers other than Body, Mariane, Elina, Breeder, Air Conditioner, or Duralumin Case.
			while (!(answer.equalsIgnoreCase("Body")) && !(answer.equalsIgnoreCase("Mariane")) && 
					!(answer.equalsIgnoreCase("Elina")) &&!(answer.equalsIgnoreCase("Breeder")) && 
					!(answer.equalsIgnoreCase("Air Conditioner")) && !(answer.equalsIgnoreCase("Duralumin Case"))) {
				System.out.println("\nThat is not a valid answer. Please choose between the choices below:");
				System.out.print ("(Body/Mariane/Elina/Breeder/Air Conditioner/Duralumin Case) ");
				answer = keyboard.nextLine();
			}
			
			// if statement for investigation around body.
			if (answer.equalsIgnoreCase("Body")) {
				// Call body method.
				body();
			}
			// else if statement for questioning nurse Mariane.
			else if (answer.equalsIgnoreCase("Mariane")) {
				// Call nurse method to display questioning of the nurse.
				nurse();
			}
			// else if statement for questioning the photographer Elina.
			else if (answer.equalsIgnoreCase("Elina")) {
				// Call photographer method to display questioning of the photographer.
				photographer();
			}
			// else if statement for questioning the breeder Georgio.
			else if(answer.equalsIgnoreCase("Breeder")) {
				// Call breeder method to display questioning of the breeder.
				breeder();
			}
			// else if statement for investigating the air conditioner.
			else if(answer.equalsIgnoreCase("Air Conditioner")) {
				// Call AC method to investigate.
				AC();
			}
			// else statement for investigating the duralumin case
			else {
				// Call duraluminCase method
				duraluminCase();
			}
		}
		// Finish investigation in the dining hall.
		System.out.println("'I think I've investigated everything I can in here.");
		System.out.println("I should take a look at the rest of the building.'");
		enter();
		System.out.println("'It'll be a good idea to consider our crime scene to be the whole building, "
				+ "\nand not just the main hall...");
		System.out.println("When the lights went out, it affected the entire hotel.'");
		enter();
		// Enter the hallway.
		System.out.println("You entered the hallway. You saw the musician, Ilya Miles, then a few steps away");
		System.out.println("the princess from an overseas country, Sumia Noah. There was also the entrance to the kitchen,");
		System.out.println("a back storage room and an office. 'I should probably question the two in the hallway before moving on.'");
		enter();
		// Encase investigation of hallway in a while loop;
		// Checks if inventory indexes 7 and 11 aren't filled, repeats if true.
		while (inventory[7] == null || inventory[11] == null) {
			System.out.print("What should you investigate here? (Ilya/Sumia) ");
			// Store user's answer in answer variable.
			answer = keyboard.nextLine();
		
			// Input validation while loop that won't accept any answers other than Ilya and Sumia.
			while(!(answer.equalsIgnoreCase("Ilya")) && !(answer.equalsIgnoreCase("Sumia"))) {
				System.out.println("That is not a valid answer. Please choose between the choices below:");
				System.out.print("(Ilya/Sumia) ");
				// Store in answer.
				answer = keyboard.nextLine();
			}
			// if and else statements for questioning the musician and the princess.
			if (answer.equalsIgnoreCase("Ilya")) {
				// Call musician method to question her.
				musician();
			}
			// else statement for questioning the princess.
			else {
				// Call princess method to question her.
				princess();
			}
		}
		// Continue investigation in the rest of the rooms.
		System.out.println("'I don't think there's anything left in the corridor.'");
		// while loop that continues until inventory indexes 8, 9, 10 and 14 are filled, as well as boolean updated is assigned true.
		boolean updated = false;
		while (inventory[8] == null || inventory[9] == null || inventory[10] == null || inventory[14] == null || !updated) {
			System.out.print("Where should you go next? (Kitchen/Storage/Office) ");
			// Store input in answer.
			answer = keyboard.nextLine();
			// Input validation while loop; won't accept answers other than the 3 choices above.
			while (!(answer.equalsIgnoreCase("Kitchen")) && !(answer.equalsIgnoreCase("Storage")) && 
					!(answer.equalsIgnoreCase("Office"))) {
				System.out.println("Invalid answer. Please chooce between the choices below:");
				System.out.print("(Kitchen/Storage/Office) ");
				// Store in answer variable.
				answer = keyboard.nextLine();
			}
			// if statement for investigating the kitchen.
			if (answer.equalsIgnoreCase("Kitchen")) {
				// Call kitchen method to run investigation of kitchen.
				kitchen();
			}
			// else if statement for investigating the storage room.
			else if (answer.equalsIgnoreCase("Storage")) {
				// Call storage method to run investigation of the storage room.
				storage();
			}
			// else statement for investigating the office.
			else {
				// Call office method to run investigation of the office.
				office();
				// Assign true to updated, as air conditioner evidence description was updated.
				updated = true;
			}
		}
		// Finish investigation.
		System.out.println("\n'I've finished checking all the rooms here.'");
		System.out.println("'This \"trial\" should be starting soon...'");
		enter();
	}
	
	// Beginning of body investigation method
	// Method to encase the investigation of the body.
	public static void body() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
				
		// String variable answer to hold user's input
		String answer;
		
		// Describe state of body.
		System.out.println("\nUnder the table... Magnus' body was lying face down.");
		System.out.println("He looks as if he was just in the middle of doing something, but...");
		System.out.println("According to the note, he was stabbed repeatedly between his stomach and his throat.");
		enter();
			
		// Update evidence by calling note method (contains complete description of the piece of evidence).
		System.out.println("\n\"" + inventory[0] + "\" Evidence Updated");
		note();
					
		enter();
		
		// while loop that continues until inventory indexes 1 and 4 are filled
		while (inventory[1] == null || inventory[4] == null) {
			System.out.println("There are more things to investigate around the body...");
			System.out.println("What would you like to take a look at?");
			System.out.print("(Goggles/Blood) ");
			answer = keyboard.nextLine();
					
			// Input validation while loop.
			// Won't accept answers other than Goggles, Blood.
			while (!(answer.equalsIgnoreCase("Goggles")) && !(answer.equalsIgnoreCase("Blood"))) {
				System.out.println("\nThat is not a valid answer. Please choose between the choices below:");
				System.out.print("(Goggles/Blood) ");
				answer = keyboard.nextLine();
			}
			// Nested if/else statements for each of the evidences
			// if statement for investigating the goggles.
			if (answer.equalsIgnoreCase("Goggles")) {
				System.out.println("\n'Goggles? ...They look like night-vision goggles.");
				System.out.println("What are they doing here?'");
				enter();
				System.out.println("\"Um, if I may.\" Said Elina the photographer from across the room.");
				System.out.println("\"These goggles are night-vision goggles, but they also read heat signatures.\"");
				System.out.println("\"I tried them on... I was wondering what they were for.\"");
				enter();
				System.out.println("'Then... Did the culprit use these night-heat-vision goggles to stab Magnus during the blackout?'");
				enter();
				// Add Goggles to evidence inventory.
				inventory[1] = "Night-Heat-Vision Goggles";
				System.out.println("\"" + inventory[1] + "\"" + invMessage());
				// Call goggles method to display description of the evidence.
				goggles();
				enter();
			}
			// For Blood answer.
			else {
				System.out.println("\nThe blood flowing out of Magnus' body is making a large pool under the table.");
				System.out.println("That's a lot of blood... There are stains all over the tablecloth across from me...");
				System.out.println("There must have been quite a lot of blood splattered all around from the force of the stabs.");
				enter();
				System.out.println("But... there aren't any traces of a trail of blood anywhere.");
				enter();
				// Add Bloodstain Under Table to Evidence Inventory
				inventory[4] = "Bloodstain Under Table";
				System.out.println("\"" + inventory[4] + "\"" + invMessage());
				// Display description
				blood();
				enter();
			}
		}
		// Once inventory index 1 and 4 are filled, complete investigation around body.
		System.out.println("I think I've investigated all I can under the table.");
		enter();
	}
	
	// Beginning of nurse method
	// Method to encase questioning of the nurse Mariane Telles.
	public static void nurse() {
		System.out.println();
		System.out.println("You go up to the nurse. \"Mariane? Have you found anything?\"");
		System.out.println("\"Um... I was a bit lightheaded, but I was able to examine Mr. Nova's body.\"");
		System.out.println("'Right, Mariane fainted during the blackout. Face down...'");
		enter();
		System.out.println("\"I don't know what happened. Next thing I know, I'm kissing the ground...");
		System.out.println("That was so embarrassing, in front of everybody...");
		System.out.println("But.. Mr. Nova was killed... I can't afford to worry about myself.\" Mariane teared up.");
		enter();
		// Add Faint Face Down to evidence inventory.
		inventory[5] = "Faint Face Down";
		System.out.println("\"" + inventory[5] + "\"" + invMessage());
		// Call faint method for Faint Face Down evidence description.
		faint();
		enter();
		System.out.println("\"U-um... If you would like, I could share my autopsy results with you.");
		System.out.println("I found many stab wounds on Mr. Nova's chest and abdomen...");
		System.out.println("The weapon... Also pierced the lungs and the internal organs...");
		enter();
		System.out.println("It was probably a thin, sharp implement, about 5 millimeters in diameter... and...");
		System.out.println("He was probably stabbed... Again and again.\"");
		System.out.println("Mariane looks very distraught with the information she shared.");
		enter();
		System.out.println("'5 millimeters in diameter? That's awfully thin... Like an awl or an ice pick or something.'");
		enter();
		// Add Mariane's Autopsy Results to Evidence Inventory.
		inventory[13] = "Mariane's Autopsy Results";
		System.out.println("\"" + inventory[13] +"\"" + invMessage());
		// Call autopsy method to display evidence's description.
		autopsy();
		enter();
		System.out.println("\"O-oh! Also, I found something in Mr. Nova's pocket while I was examining him...\"");
		System.out.println("Mariane gave you a piece of paper. It read:");
		System.out.println();
		System.out.println("\"Be careful. A murder will happen tonight. Someone will definitely kill someone.\"");
		enter();
		System.out.println("No sender. 'A murder threat?'");
		System.out.println("\"This is a bit... Unnerving.\"");
		System.out.println("\"Y-yes...\" Mariane agreed. \"But it would explain Mr. Nova's actions during the party.\"");
		enter();
		System.out.println("If you remembered correctly... \"He was being extremely cautious about anything potentially dangerous.\"");
		enter();
		// Add Murder Threat to Evidence Inventory.
		inventory[12] = "Murder Threat";
		System.out.println("\"" + inventory[12] + "\"" + invMessage());
		// Call threat method for Murder Threat evidence description.
		threat();
		enter();
		System.out.println("'Before I leave, I should confirm something with Mariane.'");
		System.out.println("\"Hey, Mariane. Magnus was here with us at the main hall until the lights went out, right?\"");
		System.out.println("The nurse wiped some tears from her eyes. \"Y-yes... I'm sure of it.\"");
		enter();
		System.out.println("\"Then, since we found his body after the lights came back on...");
		System.out.println("We should probably assume that he died during the blackout.\"");
		System.out.println("\"I suppose you are right...\" Replied Mariane.");
		enter();
		System.out.println("\"Why did we find Magnus under that table? The culprit picked a pretty bad hiding place.\" You said.");
		System.out.println("\"Figuring out what happened during the blackout will be critical to figuring out this case.\"");
		System.out.println("Mariane seemed apprehensive. \"Y-yes, but... No one could see anything!\"");
		enter();
		System.out.println("Maybe no one could SEE anything... 'But maybe someone heard?'");
		System.out.println("There was a musician at the party. Don't musicians have good ears?");
		System.out.println("'Maybe I should speak with her.'");
		enter();
	}
	
	// Beginning of photographer method; encases questioning of the photographer
	public static void photographer() {
		System.out.println("\nYou approach Elina. \"Hey, Elina... Right? How are you feeling?\"");
		System.out.println("The photographer sighs deeply. \"Awful.\"");
		System.out.println("\"But we need to do all we can to solve this--\" She pauses. \"murder.\" She finished softly.");
		enter();
		System.out.println("You stare at her hopefully. \"Do you have something that might be helpful?\"");
		System.out.println("Her eyes shined with determination. \"Photos.\"");
		System.out.println("\"I was taking pictures of everybody before the blackout.\"");
		enter();
		System.out.println("\"I mapped out everybody's position in the room before the lights went out.\"");
		System.out.println("Elina shows her sketch. \"But Magnus' location is very peculiar...\"");
		System.out.println("When you look at her map, you see that Magnus' position was on the opposite corner of the room");
		System.out.println("in relation to where you found his body.");
		enter();
		System.out.println("\"That's really weird... How did he end up there from across the room?\"");
		System.out.println("Elina huffed. \"That's what I'd like to know.\"");
		System.out.println("What exactly happened during the blackout?");
		enter();
	}
	
	// Beginning of breeder method to display questioning of the breeder
	public static void breeder() {
		System.out.println("\n\"Where is it?!\" You heard the breeder, Georgio Tess, mumble in distress as you approached.");
		System.out.println("\"What are you looking for?\" You inquired.");
		System.out.println("\"My earring... My precious earring gifted by my late grandmother! Where is it?!\"");
		enter();
		System.out.println("Being under his scrutiny made you uncomfortable. You looked down. \"Uh... Maybe it fell under the floor?");
		System.out.println("The carpet doesn't extend to the corners of the hall, so it could've fallen through the gaps in the floor.\"");
		System.out.println("\"What a fine observation...\" Georgio muttered, looking down at the hardwood floor.");
		enter();
		System.out.println("He crouched down and neared his face toward a gap in the floor.");
		System.out.println("\"I see it!\" He gasped. \"But how could I get it out?\"");
		System.out.println("The gaps in the floor were in no way big enough for a hand, and didn't seem like a tool could reach far enough.");
		enter();
		System.out.println("\"Maybe you should give up then.\"");
		System.out.println("\"There is no way! That earring is worth more than your life!\" Georgio shouted.");
		System.out.println("He turned around. \"If you won't help me, then I will find a way by myself.\"");
		enter();
		System.out.println("Georgio left. 'He's really determined to get it back huh?'");
		System.out.println("'It'd be nice if he could spare some of that determination for the investigation...'");
		enter();
		// Add Gaps in Floorboard to Evidence Inventory.
		inventory[2] = "Gaps in Floorboard";
		System.out.println("\"" + inventory[2] + "\"" + invMessage());
		// Display Gaps in Floor evidence description.
		gaps();
		enter();
	}
	
	// Beginning of AC method; includes investigation of the air conditioner
	public static void AC() {
		System.out.println("\n'An air conditioner and its remote control...'");
		System.out.println("'There was a \"beep\" before the blackout.'");
		System.out.println("The air conditioner is the only machine in the dining hall.");
		enter();
		System.out.println("'Huh? The air conditioner's timer...'");
		System.out.println("\"It's set to 11:30.\"");
		System.out.println("That's about the same time Magnus was killed.");
		enter();
		System.out.println("'That \"beep\" must have been the sound of the air conditioner turning on!'");
		enter();
		//Add Air Conditioner Timer to Evidence Inventory.
		inventory[3] = "A/C Timer";
		System.out.println("\"" + inventory[3] + "\"" + invMessage());
		// Air Conditioner Timer evidence description.
		ac();
		enter();
	}
	
	// Beginning of duraluminCase method; invludes investigation of the duralumin case
	public static void duraluminCase() {
		System.out.println("\nYou approach the open duralumin case on the ground.)"); 
		System.out.println("'The case Magnus was constantly hovering around during the party.'");
		System.out.println("Before entering the party, Magnus patted down all the guests to check for any dangerous items.");
		enter();
		System.out.println("He also confiscated any dangerous kitchen appliances and tools around, and put them all in the duralumin case.");
		System.out.println("'Could someone have taken something from this case to kill Magnus?'");
		System.out.println("'But the key was with Magnus the entire time...'");
		enter();
		System.out.println("There's a nightstick inside... and a can of tear gas... and some other unsettling items.");
		System.out.println("'A hard plastic case?'");
		System.out.println("'It's relatively small... Almost shaped like a case for larger glasses.'");
		enter();
		System.out.println("'So... Magnus knew something was going to happen. There's no other way to explain his paranoia.'");
		enter();
		// Add Duralumin Case to Evidence Inventory.
		inventory[6] = "Duralumin Case";
		System.out.println("\"" + inventory[6] + "\"" + invMessage());
		// Duralumin case evidence description.
		duralumin();
		enter();
	}
	
	// Beginning of musician method for questioning of musician
	public static void musician() {
		System.out.println("\nYou approached the musician, Ilya Miles.");
		System.out.println("You heard Ilya humming a tune under her breath.");
		System.out.println("\"Hey, Ilya Miles?\" You catch her attention.");
		enter();
		System.out.println("'If musicians really have good ears, Ilya will have an important clue.'");
		System.out.println("\"Yup?\" She responded amicably.");
		System.out.println("\"Musicians are known to have good ears, right?");
		enter();
		System.out.println("\"Yep, usually they do! I'm glad to say that I'm one of them.\"");
		System.out.println("\"Then you must have heard what happened during the blackout.\" You said.");
		System.out.println("\"Hm...\" She appeared to be thinking. \"Yeah, I think I remember how everything went down.\"");
		enter();
		System.out.println("\"It started out with Elina:\"");
		System.out.println();
		System.out.println("'Ah! The power went out!'");
		enter();
		System.out.println("\"Then the mechanic, Kade Sona:\"");
		System.out.println();
		System.out.println("'What's going on?! I can't see anything!'");
		enter();
		System.out.println("\"The rest played out like so:\"");
		System.out.println();
		System.out.println("'Magnus: Hey, what are you doing there?!'");
		enter();
		System.out.println("'Terrence, the cook: Hey, everyone! Where are you?! The outage isn't just in the kitchen?'");
		enter();
		System.out.println("'Sumia, the princess: Could someone perhaps have flipped the circuit breakers?'");
		enter();
		System.out.println("'Kade: W... Wait here! I'll use the walls as a guide and go see if I can't fix this...!'");
		enter();
		System.out.println("\"And that's how it all played out!\" Ilya exclaimed.");
		System.out.println("Something seems peculiar about that dialogue...");
		System.out.println("'Why did Magnus say that? \"What are you doing there\"?'");
		enter();
		// Add Ilya's Testimony to Evidence Inventory
		inventory[7] = "Ilya's Testimony";
		System.out.println("\"" + inventory[7] + "\"" + invMessage());
		// Ilya's Testimony evidence description
		testimony();
		enter();
		System.out.println("You thank Ilya for her time and move on.");
		enter();
	}
	
	public static void princess() {
		System.out.println("\nYou approached Sumia Noah, the princess from a faraway overseas country.");
		System.out.println("As you neared, Sumia asked. \"This part of the wall is made of different material "
				+ "\nand is colored differently from the rest. What does it mean?\"");
		enter();
		System.out.println("You looked to where she was gesturing. She was pointing at the fire door,");
		System.out.println("coloured a solid yellow against the hotel hallway's patterned wallpaper.");
		System.out.println("\"It's a fire door.\" You said.");
		enter();
		System.out.println("Sumia looked confused.");
		System.out.println("You clarified. \"In case there's a fire, this door can be sealed to stop it "
				+ "\nfrom spreading. That gives everyone a chance to escape.\"");
		System.out.println("\"I see! It seals the area! ...This is most definitely culture shock!\" Sumia exclaimed.");
		enter();
		// Add Fire Door to Evidence Inventory
		inventory[11] = "Fire Door";
		System.out.println("\"" + inventory[11] + "\"" + invMessage());
		// Fire Door evidence description
		fire();
		enter();
	}
	// Beginning of kitchen method
	// Runs the investigation of the kitchen.
	public static void kitchen() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
				
		// String variable answer to hold user's input.
		String answer;
		
		System.out.println("\n'I should take a look at the kitchen. The cook, Terrence Hale, must be there.'");
		System.out.println("You enter the kitchen.");
		System.out.println("Just as you expected, Terrence was cleaning things up in the kitchen.");
		enter();
		// Create a while loop to investigate the kitchen; while the inventory indexes 10 and 14 aren't filled, or player
		// doesn't check the dishes, the loop continues.
		// boolean to check if dishes were investigated.
		boolean checked = false;
		while (inventory[10] == null || inventory[14] == null || !checked) {
			System.out.print("What will you investigate? (Terrence/Clipboard/Dishes) ");
			// Store in answer.
			answer = keyboard.nextLine();
			// Input validation while loop; will only accept the above choices.
			while(!(answer.equalsIgnoreCase("Terrence")) && !(answer.equalsIgnoreCase("Clipboard")) && !(answer.equalsIgnoreCase("Dishes"))) {
				System.out.println("That is not a valid answer. Please pick between the choices below:");
				System.out.print("(Terrence/Clipboard/Dishes) ");
				// Store in answer.
				answer = keyboard.nextLine();
			}
			// if statement for investigating party dishes.
			if (answer.equalsIgnoreCase("Dishes")) {
				System.out.println("\nThe kitchen is packed with party dishes.");
				System.out.println("The people at the party weren't able to enjoy the cuisine due to... What happened not long ago.");
				System.out.println("One dish that catches your attention is a HUMONGOUS meat on a bone.");
				System.out.println("The handles on the meat glinted. 'Are those... Iron skewers?'");
				enter();
				// Assign true to checked to signal dishes were investigated.
				checked = true;
			}
			// else if statement for questioning Terrence.
			else if (answer.equalsIgnoreCase("Terrence")) {
				System.out.println("\nYou called out to him. \"Hey, Terrence. Were you in the kitchen when the lights went out?\"");
				System.out.println("He nodded. \"At first, I thought the power outage was limited to the kitchen, "
						+ "\nbut when I somehow managed to find my way out...\"");
				enter();
				System.out.println("\"The corridor was completely dark as well!");
				System.out.println("I could hear everyone's voices, so I tried finding my way by clinging to the wall...");
				System.out.println("And when I did, I saw it was dark in there too... There was absolutely no light anywhere.\"");
				enter();
				System.out.println("You questioned Terrence. \"Couldn't you have used the kitchen range to get some light going?\"");
				System.out.println("The cook replied. \"No, that's impossible. It's a gas range, "
						+ "\nbut it's controlled electronically so it was also affected by the outage.\"");
				enter();
				System.out.println("\"I see... so there really was nothing you could do.\"");
				System.out.println("'I thought that since you use fire in the kitchen, maybe there was"
						+ "\na source of light here, but I guess there wasn't.'");
				enter();
				// Add Terrence's Account to Evidence Inventory.
				inventory[10] = "Terrence's Account";
				System.out.println("\"" + inventory[10] + "\"" + invMessage());
				// Call account method to display Terrence's Account evidence description.
				account();
				enter();
			}
			// else statement for investigating the clipboard.
			else {
				System.out.println("\nThere was a clipboard hanging from the wall.");
				System.out.println("When you took a closer look at it, it was the kitchen's equipment list.");
				System.out.println("'All the dangerous items in the list were confiscated by Magnus... and left in the duralumin case.'");
				enter();
				System.out.println("Nevertheless... 'This is a surprisingly complete list.");
				System.out.println("There's even a barbecue griddle and a portable hot-pot stove in this kitchen.'");
				enter();
				// Add Kitchen Equipment List to Evidence Inventory.
				inventory[14] = "Kitchen Equipment List";
				System.out.println("\"" + inventory[14] + "\"" + invMessage());
				// Call list method to display Kitchen Equipment List description
				list();
				enter();
			}
		}
		// Finish kitchen investigation, go back to hallway.
		System.out.println("'I've investigated everything of importance in the kitchen.");
		enter();
	}
	
	// Beginning of storage method
	// Runs the investigation of the storage room.
	public static void storage() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
						
		// String variable answer to hold user's input.
		String answer;
		
		System.out.println("\nMoving farther in to the hotel, you enter the the storage room.");
		System.out.println("It's really dusty... You think you see cobwebs in each corner of the room.");
		System.out.println("It's full of junk. There are boxes filled with a multiple variety of items.");
		enter();
		// While loop that continues until inventory indexes 8 and 9 are filled
		while(inventory[8] == null || inventory[9] == null) {
			System.out.print("What would you like to investigate? (Laundry/Clothing Irons) ");
			// Store input in answer
			answer = keyboard.nextLine();
			// Input validation while loop that continues until user inputs laundry or clothing irons.
			while(!(answer.equalsIgnoreCase("Laundry")) && !(answer.equalsIgnoreCase("Clothing Irons"))) {
				
					System.out.println("That is not a valid answer. Please choose between the choices below:");
					System.out.print("(Laundry/Clothing Irons) ");
					// Store input in answer
					answer = keyboard.nextLine();
			}
			// if statement for Laundry choice.
			if (answer.equalsIgnoreCase("Laundry")) {
					System.out.println("\n'There seems to be a large cloth someone just shoved into this laundry box...");
					System.out.println("I think... that's one of the tablecloths.'");
					System.out.println("You take a closer look at it.");
					enter();
					System.out.println("'It's... stained with blood!'");
					System.out.println("Then, this tablecloth must be connected to Magnus' murder somehow.");
					enter();
					// Add Tablecloth in Storage Room to Evidence Inventory
					inventory[9] = "Tablecloth in Storage Room";
					System.out.println("\"" + inventory[9] + "\"" + invMessage());
					// Call tablecloth method to display description of Tablecloth in Storage Room evidence
					tablecloth();
					enter();
			}
			// else statement for investigation of clothing irons
			else {
					System.out.println("\n'Hm? Are those irons? ...They're all plugged in and powered on.");
					System.out.println("Have they been on since the lights came back on?");
					System.out.println("Did someone... do this deliberately?'");
					enter();
					// Add Irons in Storage Room to Evidence Inventory
					inventory[8] = "Irons in Storage Room";
					System.out.println("\"" + inventory[8] + "\"" + invMessage());
					// Call iron method to display description of the evidence
					iron();
					enter();
			}
		}	
		// Return to hallway
		System.out.println("'There's nothing more of importance here.'");
	}
	
	// Beginning of office method
	// This method runs the investigation of the office.
	public static void office() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's input.
		String answer;
		
		// boolean variable to check if breakers and air conditioner were checked
		boolean checked1 = false, checked2 = false;
		
		System.out.println("\nAs you entered the office, nothing in particular stood out to you.");
		System.out.println("'The circuit breakers for the hotel should be in this office.'");
		System.out.println("They probably have something to do with the outage.");
		enter();
		// while loop to continue until breakers and air conditioner are checked.
		while (!checked1 || !checked2) {
			System.out.print("What would you like to investigate here? (Breakers/Air Conditioner) ");
			// Store input in answer.
			answer = keyboard.nextLine();
			// input validation while loop; won't accept any answers other than Breakers/Air Conditioner.
			while (!(answer.equalsIgnoreCase("Breakers")) && !(answer.equalsIgnoreCase("Air Conditioner"))) {
				System.out.print("Invalid answer. Choose between these choices: (Breakers/Air Conditioner) ");
				// Store in answer.
				answer = keyboard.nextLine();
			}
			// if statement for investigating breakers.
			if (answer.equalsIgnoreCase("Breakers")) {
				System.out.println("\n'The power outage probably happened because the breakers were tripped somehow...'");
				System.out.println("'I can't see any signs they were tampered with. There's nothing suspicious about them, really...");
				System.out.println("But aren't they a little too high? I don't think any of us could reach them without standing up on something...'");
				enter();
				System.out.println("How did the culprit trip the breakers in the first place?");
				enter();
				// Assign true to checked1.
				checked1 = true;
			}
			// else statement for investigating air conditioner.
			else {
				System.out.println("'\nYou see a remote hanging on the wall.");
				System.out.println("It's connected to the office's air conditioner.");
				System.out.println("'Huh? This one is also set to 11:30. It's just the same as the one back in the main hall.'");
				enter();
				System.out.println("This... can't be a coincidence.");
				enter();
				// Update Air Conditioner Timer evidence.
				System.out.println("\"" + inventory[3] + "\"" + " has been updated.");
				// Call updated air conditioner description method.
				ac2();
				enter();
				// Assign true to checked2.
				checked2 = true;
			}
		}
		// Finish investigation of office and return to hallway.
		System.out.println("There's nothing more of importance here. I should go back to the hallway.");
	}
	
	// Beginning of trial method; contains all the methods pertaining to the trial, in the appropriate order.
	// Accepts booleans to check for guide and difficulty.
	// Contains core gameplay of Mutual Killing.
	public static void trial(boolean guideState) {
		// Call trialBegin method to introduce the trial in the narrative.
		trialBegin();
		// Call all minigame methods in appropriate order. debate1, debate2, and debate3 accept boolean variable to check if guide is on/off.
		debate1(guideState);
		evSelect1();
		debate2(guideState);
		evSelect2();
		debate3(guideState);
		mChoice1();
		mChoice2();
		evSelect3();
		mChoice3();
		mChoice4();
		debate4();
		debate5();
		mChoice5();
		debate6();
		evSelect4();
		// End trial and game with trialEnd method.
		trialEnd();
	}
	
	// Beginning of trialBegin method; will encase the dialogue and narrative before the core gameplay begins.
	public static void trialBegin() {
		System.out.println("After finding the last clue, a note fell from the ceiling.");
		System.out.println("'Huh?' You caught the note.");
		System.out.println("It read:");
		enter();
		System.out.println("\"Make your way to the basement! Make sure everyone is present,");
		System.out.println("Otherwise we can't start the trial!\"");
		enter();
		System.out.println("You sighed, but decided to follow the instructions anyway.");
		System.out.println("It's the only thing you can do if you want a chance to leave this place.");
		enter();
		System.out.println("Everyone gathered at the entrance to the basement.");
		System.out.println("Correction: mostly everyone.");
		System.out.println("'Georgio is missing.'");
		enter();
		System.out.println("Not long after, the missing guest showed up.");
		System.out.println("There was an extravagant earring hanging from his left ear.");
		System.out.println("'Huh? He managed to get back his earring somehow.'");
		enter();
		System.out.println("\"Everyone is present, yes?\" Said Sumia.");
		System.out.println("A chorus of \"yes\"es followed.");
		System.out.println("The group descended down the stairs.");
		enter();
		System.out.println("You lead the group, so you are the first to see the trial stands the first note mentioned.");
		System.out.println("Everyone spoke to themselves about who could have set this up");
		System.out.println("but before long, another note fell out from above.");
		enter();
		System.out.println("\"Hurry up. Get on the stands and start your debate!");
		System.out.println("I'm bored! Just start the trial already.\"");
		enter();
		System.out.println("'We're not a reality show for you to watch!'");
		System.out.println("Either way, everyone gets on a stand.");
		System.out.println("A last note fell out.");
		enter();
		System.out.println("\"Great! Now you can start debating.\" It ended with a smiley face...");
		enter();
	}
	
	// Beginning of trialGuide method
	// This method holds the instructions for the debates during the trial, explaining the minigame to the player.
	public static void debateGuide() {
		System.out.println("\nAs the trial progresses, you will frequently find yourself engaged in Nonstop Debates.\n" + 
				"During Nonstop Debate, your fellow guests will testify continuously, one after another.\n" + 
				"Your task is to discover the lies and inconsistencies hidden in these testimonies...");
		enter();
		System.out.println("...and to use the pieces of evidence you have gathered to shoot those weak points (shown in All Caps) down.\n" + 
				"A few evidences relevant to the topic at hand will be picked for you to choose.\n" + 
				"Pay careful attention to the character's testimonies, then choose the correct evidence and character.");
		enter();
		System.out.println("Incidentally, you get a set amount of chances depending on the difficulty chosen.\n" +
				"If the number of chances is dwindled to 0, the game will end in failure, so please be careful.\n" + 
				"A game over will result in the trial restarting, so do your best to keep on advancing.\n");
		System.out.println("The rest is up to you... We wish you luck.");
		enter();
	}
	
	public static void debateGuide2() {
		System.out.println("\nIn the previous discussion, there was only a single Weak Point, but...\n" + 
				"...from now on, there will be several Weak Points standing in your way.\n" + 
				"Nevertheless, the weak points may be many, but only one indicates an inconsistency.");
		enter();
		System.out.println("In other words, the rest are False Weak Points..\n" + 
				"Furthermore, doing so will deplete your debate counter.\n" + 
				"Should the counter reach zero, the game will end in failure, so be careful.");
		enter();
		System.out.println("Please reason out which of the Weak Points is the true inconsistency.\n" +
				"The rest is up to you We wish you luck.\n");
		enter();
	}
	
	public static void debateGuide3() {
		System.out.println("\nIn this next discussion, we introduce Solid Points (marked by 's [apostrophes]).\n" + 
				"Up until now, the weak points that appeared before you indicated where you could prove a claim wrong...\n" + 
				"These Solid Points, on the other hand, indicate where you can prove it right.\n");
		enter();
		System.out.println("Hitting these Agreement Points with an Evidence may require that you reverse the way you thought so far.\n" + 
				"You are not trying to destroy an opponent's lie or inconsistency...\n" + 
				"..But rather to prove, with an Evidence, that what they are saying is correct.\n");
		enter();
		System.out.println("Whether a given discussion calls for refutation or corroboration is up to you to reason out.\n" + 
				"The rest is up to you... We wish you luck.\n");
		enter();
	}
	
	// Beginning of debate1 method; runs the first debate in the trial.
	public static void debate1(boolean guide) {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer1 = "0";
		String answer2 = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;
		
		System.out.println("You didn't want to lose any time. \"How about we discuss the most interesting fact first?\"");
		System.out.println("\"Huh? What do you mean, most interesting fact...?\" Said Terrence, a bit unnerved.");
		System.out.println("\"The location of the body... It's strange that we found it under that table.\" You clarified.");
		enter();
		System.out.println("'The reason Magnus' body was under the table in the big hall...\n" + 
				"We'll start there... but it must lead us to discover who killed Magnus!\n");
		System.out.println("If we don't... No. I must focus on clearing up this mystery!'");
		enter();
		
		// Call debate guide if guide is on.
		if (guide) {
			debateGuide();
		}
		// Beginning of debate
		// do while loop, until the counter reaches zero, and the player chooses the right character and evidence,
		// the debate will continue. An if statement checking variable first will prevent the counter from depleting 
		// on the first iteration. Also prevents player from answering on the first iteration.
		do {
			// Display title of minigame.
			title((byte)1); // Have to cast as a byte since Java reads it as an int.
			System.out.println("Evidences: " + inventory[2] + " / " + inventory[1] + " / " + inventory[4]);
			enter();
			System.out.println("Elina: How did Magnus' body... End up under that table?");
			enter();
			System.out.println("Georgio: It was the table set deepest inside the hall... and he was under it.");
			enter();
			System.out.println("Kade: Maybe after the culprit killed Magnus... They MOVED THE BODY over there?");
			enter();
			System.out.println("Elina: But why would they do that?");
			enter();
			System.out.println("Ilya: They wanted to hide the body, of course!");
			enter();
			System.out.println("'...Huh? Something was strange about one of the testimonies. It doesn't quite fit with what I know.'");
			enter();
			tries(debateG[0]);
			enter();
			// if statement to prevent counter from depleting on first iteration; checks if it's not the first iteration before
			// beginning to deplete counter.
			if (!first) {
				// Prompt user for first answer: who is wrong?
				System.out.print("Who said something contradicting? ");
				answer1 = keyboard.nextLine();
				// while loop won't accept any other answers other than the names of characters with weak points in statements.
				while (!(answer1.equalsIgnoreCase("Kade"))) {
					validation();
					answer1 = keyboard.nextLine();
				}
				// nested if statement to check if the first answer is correct before moving onto the next question.
				if (answer1.equalsIgnoreCase("Kade")) {
					// Prompt user for piece of evidence.
					System.out.print("What evidence counters the statement? ");
					answer2 = keyboard.nextLine();
					// while loop that won't accept any other answers other than the selected pieces of evidence.
					// Choices for Gentle difficulty:
					while (!(answer2.equalsIgnoreCase(inventory[2])) && !(answer2.equalsIgnoreCase(inventory[1])) &&
							!(answer2.equalsIgnoreCase(inventory[4]))) {
						validation();
						answer2 = keyboard.nextLine();
					}
					// if statement that checks if player chose the wrong piece of evidence
					if (!(answer2.equalsIgnoreCase(inventory[4]))) {
						// Display wrong choice message.
						System.out.println("\n'No... That doesn't seem right. There's a more appropriate piece of evidence.'");
						enter();
						// Deplete from counter
						debateG[0]--;
					}
				}
				// else statement that checks if player doesn't choose the correct character.
				else {
					// Display wrong character message.
					System.out.println("\n'No... There's someone else whose statements contradict the evidence.'");
					enter();
					debateG[0]--;
				}
			}
			// Assign false to first at end of loop to signal first iteration passing.
			first = false;
		} while (debateG[0] != 0 && !(answer1.equalsIgnoreCase("Kade")) || !(answer2.equalsIgnoreCase(inventory[4])));
		// Finish the first debate
		System.out.println("\n\"Don't you think it's odd to suggest that the body was moved?\" You questioned.");
		System.out.println("Kade seemed confused. \"...Huh? Why?\"");
		System.out.println("You clarified. \"Remember the state of things under the table when we found the body.");
		enter();
		System.out.println("\"There was a large amount of blood there, but no blood trails that would suggest the body had been moved.\" You finished.");
		System.out.println("Elina commented. \"So, it's improbable that the culprit moved the body... I see.\"");
		System.out.println("Kade was disappointed. \"And I was so sure I was onto something!\"");
		enter();
	}
	// Beginning of evSelect1 method; runs the first evidence selection minigame in the trial.
	public static void evSelect1() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;
		
		// Continue story from first debate.
		System.out.println("Ilya had a question. \"But, if the culprit didn't carry it there, why was the body under the table?\"");
		System.out.println("\"That was probably... the place he was killed.\" You explained.");
		System.out.println("\"Are you saying the murder took place under a table?!\" Neil Nidas, the man who broke the lock on the gates, spoke up.");
		enter();
		System.out.println("\"Magnus crawled under the table for some reason, and that's where the culprit got to him...\n" + 
				"...And that's the reason his body was left there. It makes sense, doesn't it?\" You confirmed.");
		System.out.println("\"That does make sense, but... what was Magnus doing hiding under a table in the first place?\" Neil asked.");
		enter();
		System.out.println("You said hesitantly. \"That's... still undetermined at this point.\"");
		System.out.println("A tense silence followed your statement.");
		System.out.println("\"Well... Why not focus on another topic for the moment?\" Princess Sumia attempted to change the subject.");
		enter();
		System.out.println("She continued. \"In fact, I have a question. HOW did Mr. Magnus end up under the table? During the blackout no less?\"");
		System.out.println("Elina added. \"He shouldn't have been able to see anything there!\"");
		System.out.println("'How did Magnus manage to get under the table... I'm sure there's an evidence to answer that.'");
		enter();
		
		// for loop to print out evidence indexes 0 to 7
		for (int i = 0; i <= 7; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// for loop to print out evidence indexes 8 to 14
		for (int i = 8; i < inventory.length; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// do while loop that continues until player selects Night-Heat-Vision Goggles or evSelect count reaches 0.
		do {
			// Display title of minigame.
			title((byte)2);
			// Prompt player for an evidence.
			System.out.print("What evidence explains this? ");
			answer = keyboard.nextLine();
			// boolean variable to determine if player selected a valid piece of evidence.
			boolean found = false;
			// input validation while loop that continues until player selects an evidence from inventory.
			while(!found) {
				// for loop that runs through all inventory indexes.
				for (int i = 0; i < inventory.length; i++) {
					// if the user's answer matches an inventory index, break out of loop.
					if (answer.equalsIgnoreCase(inventory[i])) {
						found = true;
						break;
					}
				}
				// out of the for loop, if a match wasn't found, repeat input validation while loop.
				if (!found) {
					// Display invalid answer message
					validation();
					answer = keyboard.nextLine();
				}
			}
			// if statement that checks if wrong evidence was selected.
			if (!(answer.equalsIgnoreCase(inventory[1]))) {
				// Wrong answer selected message
				System.out.println("\n'Wait... This can't be the right evidence.'");
			}
			// if statement that checks if past first iteration.
			if (!first) {
				eSelectG[0]--;
			}
			enter();
			// Display counter.
			tries(eSelectG[0]);
			enter();
		} while(!(answer.equalsIgnoreCase(inventory[1])) && eSelectG[0] != 0);
		// Finish evidence selection.
		System.out.println("\n\"The blackout would make it very difficult to get under the table... But...\" You started.");
		System.out.println("\"There was a set of night-vision goggles under the table. If Magnus used them, he would be able to see during the blackout.\"");
		enter();
	}
	
	// Beginning of debate2 method
	// Continues trial from first evidence selection; copies debate1 method's format.
	public static void debate2(boolean guide) {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer1 = "0";
		String answer2 = "0";
		
		// Continue dialogue.
		System.out.println("\"Wait a second!\" Said Elina, seeming indignant.");
		System.out.println("\"That's clearly wrong, isn't it?! It's obviously just the opposite!\" She finished.");
		System.out.println("\"The opposite...?\" You questioned.");
		enter();
		System.out.println("\"I mean, it's obvious it was the culprit, not Magnus, who used those goggles!\"");
		System.out.println("\"It does seem quite farfetched...\" Agreed Georgio.");
		enter();
		// Display second debate's guide.
		if(guide) {
			debateGuide2();
		}
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;

		// Beginning of debate
		// do while loop, until the counter reaches zero, and the player chooses the right character and evidence,
		// the debate will continue. An if statement checking variable first will prevent the counter from depleting 
		// on the first iteration. Also prevents player from answering on the first iteration.
		do {
			title((byte)1);
			System.out.println("Evidences: " + inventory[12] + " / " + inventory[6] + " / " + inventory[0]);
			enter();
			System.out.println("Elina: If we think about this rationally... It must've been the culprit who used the night-vision goggles!");
			enter();
			System.out.println("Georgio: The goggles left behind in the crime scene... ARE VERY SUSPICIOUS.");
			enter();
			System.out.println("Sumia: It does seem improbable...");
			enter();
			System.out.println("Ilya: Hm... Maybe you're right!");
			enter();
			System.out.println("Elina: It was the culprit who BROUGHT THOSE GOGGLES TO THE CRIME SCENE!");
			enter();
			System.out.println("'There's a hole in one of those counterarguments!'");
			enter();
			tries(debateG[1]);
			enter();
			// if statement to prevent counter from depleting on first iteration; checks if it's not the first iteration before
			// beginning to deplete counter.
			if (!first) {
				// Prompt user for first answer: who is wrong?
				System.out.print("Who said something contradicting? ");
				answer1 = keyboard.nextLine();
				// while loop won't accept any other answers other than the names of characters with weak points in statements.
				while (!(answer1.equalsIgnoreCase("Elina")) && !(answer1.equalsIgnoreCase("Georgio"))) {
					validation();
					answer1 = keyboard.nextLine();
				}
				// nested if statement to check if the first answer is correct before moving onto the next question.
				if (answer1.equalsIgnoreCase("Elina")) {
					// Prompt user for piece of evidence.
					System.out.print("What evidence counters the statement? ");
					answer2 = keyboard.nextLine();
					// while loop that won't accept any other answers other than the selected pieces of evidence.
					// Choices for Gentle difficulty:
					while (!(answer2.equalsIgnoreCase(inventory[12])) && !(answer2.equalsIgnoreCase(inventory[6])) &&
							!(answer2.equalsIgnoreCase(inventory[0]))) {
						validation();
						answer2 = keyboard.nextLine();
					}
					// if statement that checks if player chose the wrong piece of evidence
					if (!(answer2.equalsIgnoreCase(inventory[6]))) {
							// Display wrong choice message.
							System.out.println("\n'No... That doesn't seem right. There's a more appropriate piece of evidence.'");
							enter();
							// Deplete from counter
							debateG[1]--;
						}
				}
				// else statement that checks if player doesn't choose the correct character.
				else {
					// Display wrong character message.
					System.out.println("\n'No... There's someone else whose statements contradict the evidence.'");
					enter();
					debateG[1]--;
				}
			}
			// Assign false to first at end of loop to signal first iteration passing.
			first = false;
		} while (debateG[1] != 0 && !(answer1.equalsIgnoreCase("Elina")) || !(answer2.equalsIgnoreCase(inventory[6])));
		
		// Finish the second debate
		System.out.println("\n\"No, the person who brought those goggles to the hotel was, undoubtedly, the victim - Magnus.\" You persevered.");
		System.out.println("Elina was surprised. \"Undoubtedly...? Why?\"");
		System.out.println("You asked. \"Do you remember that duralumin case Magnus was carrying with him during the party?\"");
		enter();
		System.out.println("\"There was a storage case for a set of night-vision goggles inside.\" You finished.");
		System.out.println("Sumia added. \"In other words, we can infer the goggles were inside the duralumin case when they were brought into the lodge...\"");
		System.out.println("You continued. \"That means that someone removed those goggles from inside the case during the power outage.\"");
		enter();
		System.out.println("\"And the only person who could have done that is Magnus, who never left its side.\"");
		System.out.println("\"I see... If you put it this way, I guess you're right...\" Agreed Elina, seeming embarrassed.");
		enter();
	}
	
	// Beginning of second evSelect method
	// Follows the same format as the first evidence selection.
	public static void evSelect2() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;
		
		// Continue story from second debate.
		System.out.println("Mariane observed. \"B-but, doesn't it seem weird? Magnus was so vigilant tonight...\"");
		System.out.println("Sumia agreed. \"If I may change the subject for a little while, why WAS Magnus on such high alert tonight?");
		System.out.println("\"He brought in not only defense goods, but even those night-vision goggles in that duralumin case of his...\" Neil questioned.");
		enter();
		System.out.println("\"That is definitely excessive for a 'just in case' scenario...!\" Exclaimed Kade.\n");
		System.out.println("\"Now that you mention it... so was his confiscation of dangerous items.\" Elina observed.");
		enter();
		System.out.println("She continued. \"Subjecting us to such a thorough body check... that's way too much if he was simply being cautious!\"");
		System.out.println("'He must have known. He knew someone was going to try and murder tonight...'");
		enter();
		
		// for loop to print out evidence indexes 0 to 7
		for (int i = 0; i <= 7; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// for loop to print out evidence indexes 8 to 14
		for (int i = 8; i < inventory.length; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// do while loop that continues until player selects Murder Threat or evSelect count reaches 0.
		do {
			title((byte)2);
			// Prompt player for an evidence.
			System.out.print("What evidence explains this? ");
			answer = keyboard.nextLine();
			// boolean variable to determine if player selected a valid piece of evidence.
			boolean found = false;
			// input validation while loop that continues until player selects an evidence from inventory.
			while(!found) {
				// for loop that runs through all inventory indexes.
				for (int i = 0; i < inventory.length; i++) {
					// if the user's answer matches an inventory index, break out of loop.
					if (answer.equalsIgnoreCase(inventory[i])) {
						found = true;
						break;
					}
				}
				// out of the for loop, if a match wasn't found, repeat input validation while loop.
				if (!found) {
					// Display invalid answer message
					validation();
					answer = keyboard.nextLine();
				}
			}
			// if statement that checks if wrong evidence was selected.
			if (!(answer.equalsIgnoreCase(inventory[12]))) {
				// Wrong answer selected message
				System.out.println("\n'Wait... This can't be the right evidence.'");
			}
			// if statement that checks if past first iteration.
			if (!first) {
				eSelectG[1]--;
			}
			enter();
			// Display counter.
			tries(eSelectG[1]);
			enter();
		} while(!(answer.equalsIgnoreCase(inventory[12])) && eSelectG[1] != 0);
		// Finish evidence selection.
		System.out.println("\n\"Everyone... Take a look at this.\" You said as you took out the piece of paper.");
		System.out.println("\"Be careful. A murder will happen tonight. Someone will definitely kill someone.\" Read out Terrence.");
		System.out.println("\"In all likelihood... this is a threat someone sent to Togami.\" You explained.");
		enter();
		System.out.println("One question popped up in everyone's minds... Who is \"someone\"?");
		System.out.println("\"Regardless of who wrote it... is this threat the reason Magnus was so wary?\" Asked Elina.");
		System.out.println("Mariane stuttered out. \"T-there's no other explanation...\"");
		enter();
	}
	
	// Beginning of third debate method
	// Accepts a boolean variable to display guide if on. 
	public static void debate3(boolean guide) {
		// Continue dialogue.
		System.out.println("Sumia began. \"If Mr. Magnus was the one wearing the goggles... How was the culprit able to kill him?\"");
		System.out.println("\"Did the culprit know the power was going to go out?\" Ilya inquired.");
		System.out.println("'If the murder took place during the blackout, the mystery of that blackout must be important...'");
		enter();
		System.out.println("'In that case, we must make it clear why that power outage happened...!'");
		
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer1 = "0";
		String answer2 = "0";
		
		// Display third debate guide.
		if(guide) {
			debateGuide3();
		}
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;

		// Beginning of debate
		// do while loop, until the counter reaches zero, and the player chooses the right character and evidence,
		// the debate will continue. An if statement checking variable first will prevent the counter from depleting 
		// on the first iteration. Also prevents player from answering on the first iteration.
		do {
			title((byte)1);
			System.out.println("Evidences: " + inventory[8] + " / " + inventory[9] + " / " + inventory[10]);
			enter();
			System.out.println("Georgio: Who flipped the breakers and how did they do it?");
			enter();
			System.out.println("Ilya: Perhaps they 'threw a stone' at the fusebox?");
			enter();
			System.out.println("Kade: They must've 'used a remote control', right?");
			enter();
			System.out.println("Mariane: The fusebox must've 'been tampered' with somehow.");
			enter();
			System.out.println("Sumia: It is possible the 'power lines or the power transmission' have been tampered with!");
			enter();
			System.out.println("Elina: Maybe we were just 'using too much power'?");
			enter();
			System.out.println("'I think I have some clues regarding that question already...'");
			enter();
			tries(debateG[2]);
			enter();
			// if statement to prevent counter from depleting on first iteration; checks if it's not the first iteration before
			// beginning to deplete counter.
			if (!first) {
				// Prompt user for first answer: who is right?
				System.out.print("Who said something evidenced? ");
				answer1 = keyboard.nextLine();
				// while loop won't accept any other answers other than the names of characters with solid points in statements.
				while (!(answer1.equalsIgnoreCase("Elina")) && !(answer1.equalsIgnoreCase("Kade")) && !(answer1.equalsIgnoreCase("Mariane"))
						&& !(answer1.equalsIgnoreCase("Sumia")) && !(answer1.equalsIgnoreCase("Ilya"))) {
					validation();
					answer1 = keyboard.nextLine();
				}
				// nested if statement to check if the first answer is correct before moving onto the next question.
				if (answer1.equalsIgnoreCase("Elina")) {
					// Prompt user for piece of evidence.
					System.out.print("What evidence proves the statement? ");
					answer2 = keyboard.nextLine();
					// while loop that won't accept any other answers other than the selected pieces of evidence.
					// Choices for Gentle difficulty:
					while (!(answer2.equalsIgnoreCase(inventory[8])) && !(answer2.equalsIgnoreCase(inventory[9])) &&
							!(answer2.equalsIgnoreCase(inventory[10]))) {
						validation();
						answer2 = keyboard.nextLine();
					}
					// if statement that checks if player chose the wrong piece of evidence
					if (!(answer2.equalsIgnoreCase(inventory[8]))) {
							// Display wrong choice message.
							System.out.println("\n'No... That doesn't seem right. There's a more appropriate piece of evidence.'");
							enter();
							// Deplete from counter
							debateG[2]--;
						}
				}
				// else statement that checks if player doesn't choose the correct character.
				else {
					// Display wrong character message.
					System.out.println("\n'No... There's someone else whose statements prove the evidence.'");
					enter();
					debateG[2]--;
				}
			}
			// Assign false to first at end of loop to signal first iteration passing.
			first = false;
		} while (debateG[2] != 0 && !(answer1.equalsIgnoreCase("Elina")) || !(answer2.equalsIgnoreCase(inventory[8])));
		
		// Finish the third debate
		System.out.println("\n\"It's just like Elina said... The power outage happened because too much power was being used.\" You said.");
		System.out.println("\"It wasn't a coincidence, though. Someone... triggered it on purpose.");
		System.out.println("They used three irons in the storage room for that.\" You clarified.");
		enter();
		System.out.println("\"When I found them after the power outage, they were all plugged in and turned on.\"");
		System.out.println("\"So, someone set up those irons purposely to draw enough power to trip the breakers?\" Elina asked.");
		System.out.println("\"It seems that way.\" You answered.");
		enter();
	}
	
	// Beginning of first mChoice method
	// First multiple choice segment of the trial.
	public static void mChoice1() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// Continue dialogue.
		System.out.println("\"Wait! In order for the culprit to fire up those irons...\" Started Neil.");
		System.out.println("\"They would have to go to the storage room, wouldn't they?\"");
		enter();
		System.out.println("\"In that case, the people who were in the big hall when the power outage occurred should be removed from the suspect list, right?!\" He finished");
		System.out.println("\"No, the people who were in the big hall are not yet removed from suspicion!\" You argued.");
		System.out.println("\"The irons weren't the only cause for the blackout... They were only part of the problem.\" You continued.");
		enter();
		// while loop to run through the multiple choice.
		while(!(answer.equalsIgnoreCase(inventory[3]))) {
			title((byte)3);
			System.out.println("What triggeed the blackout?");
			// Display evidences.
			System.out.println(inventory[8] + "   " + inventory[9] + "   " + inventory[3] + "   " + inventory[1] + "   ");
			// Store user input in answer.
			answer = keyboard.nextLine();
			// input validation while loop that won't accept any answer other than the options displayed.
			while (!(answer.equalsIgnoreCase(inventory[8])) && !(answer.equalsIgnoreCase(inventory[9])) && !(answer.equalsIgnoreCase(inventory[3]))
					&& !(answer.equalsIgnoreCase(inventory[1]))) {
				validation();
				answer = keyboard.nextLine();
			}
			// if wrong answer is picked, deplete from counter.
			if (!(answer.equalsIgnoreCase(inventory[3]))) {
				mChoiceG[0]--;
			}
		}
		// Finish first multiple choice segment.
		System.out.println("\n\"The irons did cause the power outage, but they weren't the direct trigger for it...\" You explained.");
		System.out.println("You continued. \"The direct trigger was the air conditioners in the big hall and the office turning on.\"");
		System.out.println("\"A...Air conditioners?! Neil exclaimed.\"");
		enter();
		System.out.println("\"Both air conditioners were set to turn on at 11:30...\" You shared.");
		System.out.println("Sumia declared. \"11:30... That's about the same time that Mr. Magnus died!\"");
		System.out.println("\"So... the moment those air conditioners turned on, the breakers flipped, causing the blackout...\" Neil observed.");
		System.out.println("\"You got me! I am fully convinced!\" He finished loudly.");
		enter();
		System.out.println("\"The culprit must have investigated the power limit at the hotel beforehand, and set up enough irons to just barely reach it.\" Said Georgio.");
		System.out.println("\"Then, all they had to do was set up the air conditioner's timers and wait for them to turn on...\" He deducted.");
		enter();
		System.out.println("\"Hm... But who reset the breakers?\" Terrence asked.");
		System.out.println("\"The fuse box was way too high for anyone to reach it.\" He added, furrowin his brow.");
		System.out.println("Cue a note falling from the ceiling. \"That was me!\" It said.");
		enter();
		System.out.println("An awkward silence followed. 'That's one mystery solved.'");
		System.out.println("Mariane piped up. \"U-um... We've debated so much already, but... Do we have a clue to lead us to the culprit?\"");
		System.out.println("Everyone stayed silent.");
		enter();
		System.out.println("\"I-I'm sorry! I didn't mean to bring everyone down...\" She said, panicked and regretful.");
		System.out.println("Elina seemed deflated. \"That IS true though... We didn't even find out the murder weapon!\"");
		enter();
	}
	
	// Beginning of the second mChoice method.
	// Follows same format of the first mChoice.
	public static void mChoice2() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// Continue dialogue.
		System.out.println("\"Wait, on that note, isn't the murder weapon obvious?\" Kade asked.");
		System.out.println("\"Obvious...? What do you mean?\" You asked for clarification.");
		enter();
		System.out.println("\"It was probably one of the kitchen's knives, wasn't it?\" The mechanic answered.");
		System.out.println("\"But... Magnus confiscated them all.\" Terrence reminded.");
		System.out.println("\"Then the culprit brought the knife with them!\" Kade stood by his point.");
		enter();
		System.out.println("Mariane protested softly. \"B-but... Mr. Orion performed a thorough body check on everybody...\"");
		System.out.println("\"Magnus wasn't a professional, he could've missed something!\" Kade argued.");
		System.out.println("\"E-even if he did...\" Mariane continued. \"A knife as a murder weapon... That can't be it.\" She finished meekly.");
		enter();
		System.out.println("'Mariane is right. There's something not quite right with the knife being the murder weapon.'");
		enter();
		// while loop to run through the multiple choice.
		while(!(answer.equals(inventory[13])) ) {
			title((byte)3);
			System.out.println("What disproves the knife as the murder weapon?");
			// Display answers.
			System.out.println(inventory[13] + "   " + inventory[10] + "   " + inventory[7] + "   " + inventory[0] + "   ");
			// Store user input in answer.
			answer = keyboard.nextLine();
			// input validation while loop that won't accept any answer other than the options displayed.
			while (!(answer.equalsIgnoreCase(inventory[13])) && !(answer.equalsIgnoreCase(inventory[10])) && !(answer.equalsIgnoreCase(inventory[7]))
					&& !(answer.equalsIgnoreCase(inventory[0]))) {
				validation();
				answer = keyboard.nextLine();
			}
			// if wrong answer is picked, deplete from counter.
			if (!(answer.equalsIgnoreCase(inventory[13]))) {
				mChoiceG[1]--;
			}
		}
		// Finish second multiple choice segment.
		System.out.println("\n\"A knife can't be the murder weapon, right Mariane?\" You asked.");
		System.out.println("\"What are you talking about? A knife has to be the murder weapon.\" Protested Kade.");
		System.out.println("Mariane explained. \"But... judging from the wounds on Mr. Orion's body, the murder weapon should be about 5 millimeters in diameter...\"");
		enter();
		System.out.println("\"5 millimeters in diameter? That's much thinner than a knife!\" Agreed Ilya.");
		System.out.println("\"B-but then, we really have no murder weapon!\" Kade panicked.");
		System.out.println("\"I-I'm sorry!\" Apologized Mariane, feeling guilty.");
		enter();
	}
	
	// Beginning of third evSelect method.
	// Follows other evSelect formats.
	public static void evSelect3() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;
		
		// Continue story from second debate.
		System.out.println("\"If we could even deduce something from what happened in the blackout...\" Tried Sumia.");
		System.out.println("Georgio observed somberly. \"No one could see anything during the blackout... Try as you might, the truth shall remain hidden in darkness.\"");
		System.out.println("'The truth shall remain hidden in darkness'...? But it won't.' You thought with conviction.");
		enter();
		System.out.println("\"No... We still have something that can illuminate the situation... so to speak.\" You said assertively.");
		enter();
		
		// for loop to print out evidence indexes 0 to 7
		for (int i = 0; i <= 7; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// for loop to print out evidence indexes 8 to 14
		for (int i = 8; i < inventory.length; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// do while loop that continues until player selects Murder Threat or evSelect count reaches 0.
		do {
			title((byte)2);
			// Prompt player for an evidence.
			System.out.print("What evidence explains this? ");
			answer = keyboard.nextLine();
			// boolean variable to determine if player selected a valid piece of evidence.
			boolean found = false;
			// input validation while loop that continues until player selects an evidence from inventory.
			while(!found) {
				// for loop that runs through all inventory indexes.
				for (int i = 0; i < inventory.length; i++) {
					// if the user's answer matches an inventory index, break out of loop.
					if (answer.equalsIgnoreCase(inventory[i])) {
						found = true;
						break;
					}
				}
				// out of the for loop, if a match wasn't found, repeat input validation while loop.
				if (!found) {
					// Display invalid answer message
					validation();
					answer = keyboard.nextLine();
				}
			}
			// if statement that checks if wrong evidence was selected.
			if (!(answer.equalsIgnoreCase(inventory[7]))) {
				// Wrong answer selected message
				System.out.println("'Wait... This can't be the right evidence.'");
			}
			// if statement that checks if past first iteration.
			if (!first) {
				eSelectG[2]--;
			}
			enter();
			// Display counter.
			tries(eSelectG[2]);
			enter();
		} while(!(answer.equalsIgnoreCase(inventory[7])) && eSelectG[2] != 0);
		// Finish evidence selection.
		System.out.println("\n\"It's true that no one could SEE anything during the blackout. But that doesn't mean no one could HEAR anything.\"");
		System.out.println("\"Is this my cue?!\" Ilya exclaimed excited.");
		System.out.println("\"S-sure...\" You said, not too confidently.");
		enter();
		System.out.println("Ilya almost jumped on the spot. \"Yes! So it went like this:\"");
		enter();
		System.out.println("Elina: Ah! The power went out!");
		enter();
		System.out.println("Kade: What's going on?! I can't see anything!");
		enter();
		System.out.println("Magnus: Hey, what are you doing there?!");
		enter();
		System.out.println("Terrence: Hey, everyone! Where are you?! The outage isn't just in the kitchen?");
		enter();
		System.out.println("Sumia: Could someone perhaps have flipped the circuit breakers?");
		enter();
		System.out.println("Kade: W... Wait here! I'll use the walls as a guide and go see if I can't fix this...!");
		enter();
	}
	
	// Beginning of mChoice; third segment in the trial.
	public static void mChoice3() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		System.out.println("\"Listening to Magnus' words during the blackout...\" Started Sumia.");
		System.out.println("\"'What are you doing there'? Just what did he see during the blackout?\" Asked Kade.");
		System.out.println("\"He was killed under the table... What reason did he have to go to the table?\" Elina added.");
		enter();
		System.out.println("\"Georgio exclaimed. \"Perhaps... the culprit didn't stab Magnus under the table.\"");
		System.out.println("\"B-but... With the blood splattered all over the tablecloth... It's clear that that's where Mr. Orion was killed...\" Mariane said.");
		System.out.println("\"Magnus was stabbed under the table. But that doesn't mean that that's where he was stabbed from.\" Said Georgio cryptically.");
		enter();
		System.out.println("'The place where Magnus was stabbed and the place the culprit stabbed him from... are different?");
		System.out.println("So.. It's a place that both IS under the table and ISN'T under the table?'");
		enter();
		// while loop to run through the multiple choice.
		while(!(answer.equalsIgnoreCase("Crawlspace Under Floor"))) {
			System.out.println("Where could the culprit have stabbed Magnus from?");
			// Display answers.
			System.out.println("Crawlspace Under Floor   Under The Table   From The Kitchen   From Across Room");
			// Store user input in answer.
			answer = keyboard.nextLine();
			// input validation while loop that won't accept any answer other than the options displayed.
			while (!(answer.equalsIgnoreCase("Crawlspace Under Floor")) && !(answer.equalsIgnoreCase("Under The Table")) && !(answer.equalsIgnoreCase("The Kitchen"))
					&& !(answer.equalsIgnoreCase("Across The Room"))) {
				validation();
				answer = keyboard.nextLine();
			}
			// if wrong answer is picked, deplete from counter.
			if (!(answer.equalsIgnoreCase("Crawlspace Under Floor"))) {
				mChoiceG[2]--;
			}
		}
		// Finish third multiple choice segment.
		System.out.println("\nYou had an epiphany. \"The culprit stabbed Magnus from the crawlspace under the floor, didn't he?\"");
		System.out.println("\"H...He was stabbed through the floor?!\" Elina was astounded.");
		System.out.println("\"The hotel's floor is full of gaps, and the carpet didn't reach all the way to that table.\" You clarified.");
		enter();
	}
	
	// Beginning of fourth multiple choice segment
	// Follows other multiple choice method formats.
	public static void mChoice4() {
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// Continue dialogue.
		System.out.println("\"If someone pushed a weapon through one of those gaps... they could probably stab and kill Magnus.\"");
		System.out.println("Sumia was curious. \"In that case, the culprit would have to get into that crawlspace. How did they do that? Where is the entrance?\"");
		System.out.println("\"I don't know... but I think I have an idea who might.\" You answered her inquiry.");
		enter();
		
		// while loop to run through the multiple choice.
				while(!(answer.equalsIgnoreCase("Georgio"))) {
					title((byte)3);
					System.out.println("Who knows what you're talking about?");
					// Display answers.
					System.out.println("Ilya   Terrence   Georgio   Neil");
					// Store user input in answer.
					answer = keyboard.nextLine();
					// input validation while loop that won't accept any answer other than the options displayed.
					while (!(answer.equalsIgnoreCase("Ilya")) && !(answer.equalsIgnoreCase("Terrence")) && !(answer.equalsIgnoreCase("Georgio"))
							&& !(answer.equalsIgnoreCase("Neil"))) {
						validation();
						answer = keyboard.nextLine();
					}
					// if wrong answer is picked, deplete from counter.
					if (!(answer.equalsIgnoreCase("Georgio"))) {
						mChoiceG[3]--;
					}
				}
				// Finish third multiple choice segment.
				System.out.println("\n\"Hey, Georgio... you dropped your earring back at the big hall, didn't you?\" You asked.");
				System.out.println("\"Indeed.\" He nodded.");
				System.out.println("\"And you're wearing this earring on your left ear now, right?\" You questioned for clarification.");
				enter();
				System.out.println("\"Huh? So you--\" Muttered Terrence.");
				System.out.println("You continued. \"Georgio didn't have it on during the investigation... But just before the trial he had it back on.");
				System.out.println("That means that during the investigation, he managed to retrieve his earring from under the floor.\"");
				enter();
				System.out.println("Elina asked. \"And so, it means he must know a way to get under the floor?\"");
				System.out.println("\"Yes... Nothing can stand in I, Georgio Tess', way!\"");
				System.out.println("\"Just explain it loudly and clearly, like a man!\" Shouted Neil, pasionately.");
				enter();
				System.out.println("\"So be it, then! I shall tell you! The answer to this mystery lies in the storage room!\" Exclaimed Georgio.");
				System.out.println("He continued. \"I discovered the entrance concealed among the piles of boxes...");
				System.out.println("I then acquired a flashlight from a hotel room, and stepped into the space, unafraid to face the danger ahead.\"");
				enter();
				System.out.println("\"And there I claimed the spoils of my solitary battle... My earring, mine again at last!\" Georgio finished dramatically.");
				System.out.println("\"...Anyway, it would seem he entered the crawlspace from the storage room.\" Deducted Sumia.");
				System.out.println("She continued. \"It didn't look as though the walls extend down there, so it wouldn't be very far from the storage room to the hall.\"");
				enter();
	}
	
	// Beginning of fourth debate method
	// Follows format of other debate methods.
	public static void debate4() {
		System.out.println("\"So, someone sneaked into the crawlspace from the storage room during the party, then?\" Asked Elina for certainty.");
		System.out.println("\"It probably wasn't just during the party... but during the power outage...\" Added Mariane.");
		System.out.println("Ilya commented. \"If someone disappeared during the blackout, it's not so strange that we didn't notice!\"");
		enter();
		System.out.println("Terrence exclaimed. \"But, walking from the hall to the storage room in the dark is like trying to make Eggs Benedict without eggs!\"");
		System.out.println("\"He's right... I couldn't even find the office, which is right next to the dining hall... \" Kade said apprehensively.");
		System.out.println("\"In that darkness? It was definitely impossible to move to the storage room!\" Exclaimed Neil.");
		enter();
		System.out.println("You thought deeply. 'But... something is definitely not right here. I feel like there's something we're missing...");
		System.out.println("Somehow... it seems we're reaching a conclusion. This will lead us to the culprit, for sure.'");
		enter();
		
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer1 = "0";
		String answer2 = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;

		// Beginning of debate
		// do while loop, until the counter reaches zero, and the player chooses the right character and evidence,
		// the debate will continue. An if statement checking variable first will prevent the counter from depleting 
		// on the first iteration. Also prevents player from answering on the first iteration.
		do {
			title((byte)1);
			System.out.println("Evidences: " + inventory[14] + " / " + inventory[12] + " / " + inventory[11]);
			enter();
			System.out.println("Terrence: Moving all the way to the storage room the dark... That's just can't be possible!");
			enter();
			System.out.println("Ilya: Maybe they had 'their own night-vision goggles'?");
			enter();
			System.out.println("Neil: Maybe they used 'a cord' leading to the storage room!");
			enter();
			System.out.println("Kade: They used 'light'. That would do it, wouldn't it?");
			enter();
			System.out.println("Mariane: It must've been impossible, after all...");
			enter();
			System.out.println("'I think I have some clues regarding that question already...'");
			enter();
			tries(debateG[3]);
			enter();
			// if statement to prevent counter from depleting on first iteration; checks if it's not the first iteration before
			// beginning to deplete counter.
			if (!first) {
				// Prompt user for first answer: who is right?
				System.out.print("Who said something evidenced? ");
				answer1 = keyboard.nextLine();
				// while loop won't accept any other answers other than the names of characters with solid points in statements.
				while (!(answer1.equalsIgnoreCase("Ilya")) && !(answer1.equalsIgnoreCase("Neil")) && !(answer1.equalsIgnoreCase("Kade"))) {
					validation();
					answer1 = keyboard.nextLine();
				}
				// nested if statement to check if the first answer is correct before moving onto the next question.
				if (answer1.equalsIgnoreCase("Kade")) {
					// Prompt user for piece of evidence.
					System.out.print("What evidence proves the statement? ");
					answer2 = keyboard.nextLine();
					// while loop that won't accept any other answers other than the selected pieces of evidence.
					// Choices for Gentle difficulty:
					while (!(answer2.equalsIgnoreCase(inventory[14])) && !(answer2.equalsIgnoreCase(inventory[12])) &&
							!(answer2.equalsIgnoreCase(inventory[11]))) {
						validation();
						answer2 = keyboard.nextLine();
					}
					// if statement that checks if player chose the wrong piece of evidence
					if (!(answer2.equalsIgnoreCase(inventory[14]))) {
							// Display wrong choice message.
							System.out.println("\n'No... That doesn't seem right. There's a more appropriate piece of evidence.'");
							enter();
							// Deplete from counter
							debateG[3]--;
						}
				}
				// else statement that checks if player doesn't choose the correct character.
				else {
					// Display wrong character message.
					System.out.println("\n'No... There's someone else whose statements prove the evidence.'");
					enter();
					debateG[3]--;
				}
			}
			// Assign false to first at end of loop to signal first iteration passing.
			first = false;
		} while (debateG[3] != 0 && !(answer1.equalsIgnoreCase("Kade")) || !(answer2.equalsIgnoreCase(inventory[14])));
		// Finish the third debate
		System.out.println("\n\"That's it! The culprit used light!\" You exclaimed.");
		System.out.println("Kade was delighted. \"Huh? I was right?!\"");
		System.out.println("\"But, where did they get light from?\" Asked Ilya.");
		enter();
		System.out.println("\"They had a light source. Here, it's written right here in the equipment list for the kitchen.\" You clarified.");
		System.out.println("You read it out loud. \"'Fork x 20. Knife x 20. Spoon x 20. Skewer x 5. Frying pan x 3. Wine glass x 20.");
		System.out.println("Barbecue griddle x 1 and portable stove x 1.'\"");
		enter();
		System.out.println("\"The light source... It's the portable stove.\" You asserted.");
		System.out.println("Elina exclaimed. \"P... Portable stove?\"");
	}
	
	// Beginning of 5th debate method
	// Follows same format as other debate methods.
	public static void debate5() {
		// Continue dialogue.
		System.out.println("\"You can turn on a portable stove even when there's no electricity, and you can even carry it around.\" You started.");
		System.out.println("Then finished. \"The culprit used that portable stove, and moved through the corridor all the way to the storage room!\"");
		enter();
		System.out.println("\"I see. That portable stove is a plausible explanation, but your reasoning still has a hole in it...\" Declared Georgio.");
		System.out.println("\"Please tell us more about that.\" Requested Terrence.");
		System.out.println("\"For that... We shall discuss!\" Georgio said with dramatic effect.");
		enter();
		
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer1 = "0";
		String answer2 = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;

		// Beginning of debate
		// do while loop, until the counter reaches zero, and the player chooses the right character and evidence,
		// the debate will continue. An if statement checking variable first will prevent the counter from depleting 
		// on the first iteration. Also prevents player from answering on the first iteration.
		do {
			title((byte)1);
			System.out.println("Evidences: " + inventory[1] + " / " + inventory[11] + " / " + inventory[14]);
			enter();
			System.out.println("Georgio: Have you forgotten the mechanic's testimony from before?");
			enter();
			System.out.println("Georgio: He said he COULND'T REACH THE OFFICE.");
			enter();
			System.out.println("Sumia: Oh! So the light from the portable stove claim...");
			enter();
			System.out.println("Elina: It would CONTRADICT THAT TESTIMONY.");
			enter();
			System.out.println("Neil: If Kade failed to reach the office, that means the corridors were COMPLETELY DARK...");
			enter();
			System.out.println("Georgio: If someone used light in the corridor, they would be SEEN BY THE MECHANIC!\"");
			enter();
			System.out.println("'In that case... the culprit somehow managed to use light without catching Kade's attention...'");
			enter();
			tries(debateG[4]);
			enter();
			// if statement to prevent counter from depleting on first iteration; checks if it's not the first iteration before
			// beginning to deplete counter.
			if (!first) {
				// Prompt user for first answer: who is wrong?
				System.out.print("Who said something contradicting? ");
				answer1 = keyboard.nextLine();
				// while loop won't accept any other answers other than the names of characters with weak points in statements.
				while (!(answer1.equalsIgnoreCase("Neil")) && !(answer1.equalsIgnoreCase("Georgio")) && !(answer1.equalsIgnoreCase("Elina"))) {
					validation();
					answer1 = keyboard.nextLine();
				}
				// nested if statement to check if the first answer is correct before moving onto the next question.
				if (answer1.equalsIgnoreCase("Georgio")) {
					// Prompt user for piece of evidence.
					System.out.print("What evidence counters the statement? ");
					answer2 = keyboard.nextLine();
					// while loop that won't accept any other answers other than the selected pieces of evidence.
					// Choices for Gentle difficulty:
					while (!(answer2.equalsIgnoreCase(inventory[1])) && !(answer2.equalsIgnoreCase(inventory[11])) &&
							!(answer2.equalsIgnoreCase(inventory[14]))) {
						validation();
						answer2 = keyboard.nextLine();
					}
					// if statement that checks if player chose the wrong piece of evidence
					if (!(answer2.equalsIgnoreCase(inventory[11]))) {
							// Display wrong choice message.
							System.out.println("\n'No... That doesn't seem right. There's a more appropriate piece of evidence.'");
							enter();
							// Deplete from counter
							debateG[4]--;
						}
				}
				// else statement that checks if player doesn't choose the correct character.
				else {
					// Display wrong character message.
					System.out.println("\n'No... There's someone else whose statements contradict the evidence.'");
					enter();
					debateG[4]--;
				}
			}
			// Assign false to first at end of loop to signal first iteration passing.
			first = false;
		} while (debateG[4] != 0 && !(answer1.equalsIgnoreCase("Georgio")) || !(answer2.equalsIgnoreCase(inventory[11])));
		
		// Finish the fifth debate.
		System.out.println("\n\"There was a 'wall' that can block light in that corridor.\" You explained.");
		System.out.println("You clarified for the guests. \"...The fire doors.\"");
		System.out.println("\"If someone closed them, they could make a perfect wall blocking off the corridor.\" You provided further.");
		enter();
	}
	
	// Beginning of 5th mChoice method
	// Follows same format as other multiple choice methods.
	public static void mChoice5() {
		// Continue dialogue.
		System.out.println("\"That's not all... just past the fire doors the corridor quickly makes a sharp turn,");
		System.out.println("\"so even if you only have a short opening... as soon as you round that corner you're out of sight.\" You finished.");
		System.out.println("\"I see...\" Understood Georgio. \"Then... It's time for the culprit to make their appearance!\"");
		enter();
		System.out.println("Ilya was perplexed. \"Is it?!\"");
		System.out.println("'\"The real culprit\"... I don't want to accuse anybody, but...");
		System.out.println("The culprit... Can only be...!'");
		enter();
		
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "0";
		
		// while loop to run through the multiple choice.
		while(!(answer.equalsIgnoreCase("Terrence"))) {
			title((byte)3);
			System.out.println("Who is the only one who can be the culprit?");
			// Display answers.
			System.out.println("Georgio   Terrence   Elina   Mariane");
			// Store user input in answer.
			answer = keyboard.nextLine();
			// input validation while loop that won't accept any answer other than the options displayed.
			while (!(answer.equalsIgnoreCase("Elina")) && !(answer.equalsIgnoreCase("Terrence")) && !(answer.equalsIgnoreCase("Georgio"))
					&& !(answer.equalsIgnoreCase("Mariane"))) {
				validation();
				answer = keyboard.nextLine();
			}
			// if wrong answer is picked, deplete from counter.
			if (!(answer.equalsIgnoreCase("Terrence"))) {
				mChoiceG[4]--;
			}
		}
		// Finish fifth multiple choice segment.
		System.out.println("\"Terrence...\" You started. \"It was you, wasn't it?\"");
		System.out.println("The cook was quiet for a moment, fidgeting. Then... \"H-huh? W-what're you talking about?!\"");
		System.out.println("Elina covered her mouth, gasping. \"What?! Terrence is the culprit? Really?!\"");
		enter();
		System.out.println("Terrence started sweating. \"W-W-What're you saying?! There's no way it's me!\"");
		System.out.println("You replied. \"This is just my theory right now, of course... so, if you have any counterarguments, by all means, let's hear them.\"");
		System.out.println("\"C-C-Counterarguments...? W...Why're you even treating me like a culprit...?\"");
		enter();
		System.out.println("\"Because... the portable stove that was used as a light was in the kitchen.\" You explained.");
		System.out.println("\"W-well! Even if I did stab Magnus, how could I have done it?! It would be dark in the crawlspace!\" He countered.");
		System.out.println("'Ugh... I didn't investigate the crawlspace, so I can't counter that. But...'");
		enter();
		System.out.println("\"Let's ask Georgio, then!\" Supplied Sumia.");
		System.out.println("\"...Regrettably, nothing remained of the murderer's paraphernalia in the dark of the crawlspace.\" Explained Georgio.");
		System.out.println("\"However, a small phosphorescence around the blood pooled on the floor...\" He said further.");
		enter();
		System.out.println("Gergio finished. \"It appeared to be... Glow-in-the-dark paint.\"");
		System.out.println("\"Oh! Then he could've used that as a landmark to get there in the dark!\" Ilya exclaimed.");
		System.out.println("\"Fine! Then how do you explain my voice in the hall, huh?!\" Terrence growled angrily.");
		enter();
	}
	
	// Beginning of sixth debate method
	// Follows format of other debate methods.
	public static void debate6() {
		// Continue dialogue.
		System.out.println("'Kade: What's going on?! I can't see anything!");
		System.out.println("Magnus: Hey, what are you doing there?!");
		System.out.println("Terrence: Hey, everyone! Where are you?! The outage isn't just in the kitchen?'");
		enter();
		System.out.println("\"Ilya heard Terrence's voice in the hall...\" Said Mariane softly.");
		System.out.println("\"D-does that prove Terrence's innocence?\" Kade questioned, nervous.");
		System.out.println("\"I definitely heard Terrence's voice in the hall! A live voice!\" Ilya said, confidently.");
		enter();
		System.out.println("\"That's right, I wasn't in the storage room. I was in the hall. I can't have been the culprit!\" Terrence declared.");
		System.out.println("You had something else to say about that. \"No, that's not what it means.\"");
		System.out.println("\"H-huh...?\" Terrence uttered. \"Why?!\"");
		enter();
		System.out.println("'This... Will prove it!'");
		enter();
		
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer1 = "0";
		String answer2 = "0";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;
		
		// Beginning of debate
		// do while loop, until the counter reaches zero, and the player chooses the right character and evidence,
		// the debate will continue. An if statement checking variable first will prevent the counter from depleting 
		// on the first iteration. Also prevents player from answering on the first iteration.
		do {
			title((byte)1);
			System.out.println("Evidences: " + inventory[1] + " / " + inventory[4] + " / " + inventory[2]);
			enter();
			System.out.println("Georgio: Was the cook IN THE STORAGE ROOM during the blackout?");
			enter();
			System.out.println("Terrence: I was in a different place at that time!");
			enter();
			System.out.println("Ilya: I HEARD Terrence's voice!");
			enter();
			System.out.println("Terrence: As long as that testimony exists... You cannot deny I WAS AT THE HALL!");
			enter();
			System.out.println("''...I have to find it!");
			enter();
			tries(debateG[5]);
			enter();
			// if statement to prevent counter from depleting on first iteration; checks if it's not the first iteration before
			// beginning to deplete counter.
			if (!first) {
				// Prompt user for first answer: who is wrong?
				System.out.print("Who said something contradicting? ");
				answer1 = keyboard.nextLine();
				// while loop won't accept any other answers other than the names of characters with weak points in statements.
				while (!(answer1.equalsIgnoreCase("Ilya")) && !(answer1.equalsIgnoreCase("Georgio")) && !(answer1.equalsIgnoreCase("Terrence"))) {
					validation();
					answer1 = keyboard.nextLine();
				}
				// nested if statement to check if the first answer is correct before moving onto the next question.
				if (answer1.equalsIgnoreCase("Georgio")) {
					// Prompt user for piece of evidence.
					System.out.print("What evidence counters the statement? ");
					answer2 = keyboard.nextLine();
					// while loop that won't accept any other answers other than the selected pieces of evidence.
					// Choices for Gentle difficulty:
					while (!(answer2.equalsIgnoreCase(inventory[1])) && !(answer2.equalsIgnoreCase(inventory[4])) &&
							!(answer2.equalsIgnoreCase(inventory[2]))) {
						validation();
						answer2 = keyboard.nextLine();
					}
					// if statement that checks if player chose the wrong piece of evidence
					if (!(answer2.equalsIgnoreCase(inventory[2]))) {
							// Display wrong choice message.
							System.out.println("\n'No... That doesn't seem right. There's a more appropriate piece of evidence.'");
							enter();
							// Deplete from counter
							debateG[5]--;
						}
				}
				// else statement that checks if player doesn't choose the correct character.
				else {
					// Display wrong character message.
					System.out.println("\n'No... There's someone else whose statements contradict the evidence.'");
					enter();
					debateG[5]--;
				}
			}
			// Assign false to first at end of loop to signal first iteration passing.
			first = false;
		} while (debateG[5] != 0 && !(answer1.equalsIgnoreCase("Terrence")) || !(answer2.equalsIgnoreCase(inventory[2])));
		
		// Finish the sixth debate.
		System.out.println("\"The fact we heard Terrence's voice in the hall doesn't prove that he was actually in the hall.\" You asserted.");
		System.out.println("\"Huh? W...Why...?\" He said tremulously.");
		System.out.println("\"Think about the floor in the hall. There are massive gaps all over it, aren't there...?\" You asked the guests.");
		enter();
		System.out.println("You finished. \"Which means, even if you spoke from under the floor, it would still sound like you were in the room with us.\"");
		System.out.println("\"Gaaah!\" He started gripping his hair.");
		System.out.println("\"I see... by speaking from under the floor, he tried to leave the impression he was there with us.\" Georgio deduced.");
		enter();
		System.out.println("\"...Is that right, Terrence?\" Elina seemed furious.");
		System.out.println("\"W-W-W-Wait just a minute!c\" Terrence tried.");
		System.out.println("\"W-What's the meaning of this, Mr. Hale?!\" Mariane was on the verge of tears.");
		enter();
		System.out.println("\"I TOLD YA T' WAIT A MINUTE!\" Terrence yelled in fury.");
		System.out.println("\"E-eek!\" Squeaked Mariane, flinching.");
		System.out.println("Terrence started babbling unitelligibly with a surprisingly thick country accent.");
		enter();
	}
	
	// Beginning of last minigame and fourth evidence selection.
	public static void evSelect4() {
		// Continue dialogue.
		System.out.println("Sumia continued. \"If Mr. Terrence was in the hall during the blackout, he should still have been there when the lights came back on, right?\"");
		System.out.println("\"Yep, that sounds right. He can't have been walking back and forth in the dark!\" Ilya answered.");
		System.out.println("Elina questioned. \"Huh? Was... Terrence there at that time?\"");
		enter();
		System.out.println("\"We can't leave this to faint memories and gut feelings. A man's life is on the line.\" Georgio said seriously.");
		System.out.println("\"But... I am not at all confident about this. I have a feeling he was there... but I feel like he also wasn't...\" Sumia said, uncertain.");
		System.out.println("\"...Maybe we should test his memory.\" You said.");
		enter();
		System.out.println("\"Y-you again?!\" Terrence stopped his babble to exclaim.");
		System.out.println("'In order to prove whether Terrence was in the hall after the blackout...\n" + 
				"We should ask him about... this!'");
		enter();
		
		// Scanner variable to monitor keyboard input.
		Scanner keyboard = new Scanner(System.in);
								
		// String variable answer to hold user's inputs. Initialize with a String; otherwise a problem that variables
		// have not been initialized occurs on the do while loop's first iteration for the debate.
		String answer = "";
		
		// boolean variable first; will prevent counter from being depleted on the first iteration.
		boolean first = true;
		
		// for loop to print out evidence indexes 0 to 7
		for (int i = 0; i <= 7; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// for loop to print out evidence indexes 8 to 14
		for (int i = 8; i < inventory.length; i++) {
			System.out.print(inventory[i] + "   ");
		}
		System.out.println(); // Skip a line
		// do while loop that continues until player selects Murder Threat or evSelect count reaches 0.
		do {
			title((byte)2);
			// Prompt player for an evidence.
			System.out.print("What evidence explains this? ");
			answer = keyboard.nextLine();
			// boolean variable to determine if player selected a valid piece of evidence.
			boolean found = false;
			// input validation while loop that continues until player selects an evidence from inventory.
			while(!found) {
				// for loop that runs through all inventory indexes.
				for (int i = 0; i < inventory.length; i++) {
					// if the user's answer matches an inventory index, break out of loop.
					if (answer.equalsIgnoreCase(inventory[i])) {
						found = true;
						break;
					}
				}
				// out of the for loop, if a match wasn't found, repeat input validation while loop.
				if (!found) {
					// Display invalid answer message
					validation();
					answer = keyboard.nextLine();
				}
			}
			// if statement that checks if wrong evidence was selected.
			if (!(answer.equalsIgnoreCase(inventory[5]))) {
				// Wrong answer selected message
				System.out.println("'Wait... This can't be the right evidence.'");
			}
			// if statement that checks if past first iteration.
			if (!first) {
				eSelectG[3]--;
			}
			enter();
			// Display counter.
			tries(eSelectG[3]);
			enter();
		} while(!(answer.equalsIgnoreCase(inventory[5])) && eSelectG[3] != 0);
		// Finish evidence selection.
		System.out.println("\"If you really were in the hall when the power came back on...\" You began.");
		System.out.println("\"You should know all about what happened to Mariane, shouldn't you?!\" You ended.");
		System.out.println("\"P-Please! Don't remind me... It was embarrassing...\" Mariane close her eyes tightly and ducked her head.");
		enter();
		System.out.println("\"Sorry. This is important.\" You apologized.");
		System.out.println("\"Um... Uh... That's peculiar. I seem to have forgotten...\" Terrence said while sweating profusely.");
		System.out.println("\"...That's it. It's over.\" You said with finality, conviction, and a sliver of sadness.");
		enter();
		System.out.println("\"N-No! Wait just a second!\" Terrence shouted in desperation.");
		System.out.println("\"You finally show your true colours, you vile cook?!\" Georgio tensed.");
		System.out.println("\"Y...You're wrong... I'm not the culprit... I'm not someone... who would ever kill anyone...\"");
		enter();
		System.out.println("Sumia frowned, disappointed... and sad. \"Mr Terrence... Why would you ever do something so horrible?\"");
		System.out.println("\"I...I wouldn't...I'd never...\" Tears started slipping from Terrence's eyes, in desperation.");
		enter();
		// Skip a line
		System.out.println();
		System.out.println("TRIAL: END"); // end trial message
		enter();
	}
	
	// Beginning of trialEnd method
	public static void trialEnd() {
		// Ending game narrative.
		System.out.println("A note fell from the ceiling. You caught it, and read it out loud:");
		System.out.println("\"Good job! You figured out the true murderer!\"\n"
				+ "\"As promised... I shall execute the killer!\" At the end, you gulped.\n");
		enter();
		System.out.println("\"N-no...Wait! NOOOOO!\" Screamed Terrence in desperation... in fear.");
		System.out.println("The guests all looked down, unable to witness a man's despair before his imminent death.");
		System.out.println("When suddenly... Terrence's screams simply cut out.");
		enter();
		System.out.println("\"What?\" You were perplexed, looking up.");
		System.out.println("Terrence was gone... Disappeared into thin air. As if he never existed, as if you didn't endure an entire trial to uncover his murder.");
		System.out.println("\"W-what?!\" Kade asked, his voice trembling with fear.");
		enter();
		System.out.println("\"Is this what they meant by...an execution?\" Georgio muttered, eyes wide.");
		System.out.println("'This was all just a horrible dream...Right?' You tried to reason in your mind, but knew it couldn't be true.");
		System.out.println("You just pinched your arm, after all.\n\nIt hurt.");
		enter();
		System.out.println("Mutual Killing: Who's the Killer?: END");
		enter();
	}
	
	// Beginning of tries method; displays a message with the number of tries left for the player during the trial's minigames.
	// Accepts a byte variable; one of the minigame counters.
	public static void tries(byte counter) {
		System.out.println("You have " + counter + " tries left.");
	}
	
	// Beginning of validation method, used during the debates and trials to display an "input invalid" message.
	public static void validation() {
		System.out.print("Invalid answer. Please choose an appropriate answer: ");
	}
	
	// Evidence Inventory
	// Methods for each of the evidences in the inventory,
	// will hold description of the evidences found.
	// Player may access the evidence inventory mostly any time in the game,
	// so each evidence will be encased in its own method.
	// Beginning of dining hall note evidence method
	public static void note() {
		System.out.println("The victim's body was discovered at the dining hall of Hotel Future." + 
				"\nThe time of death was around 11:30PM.\n\n" +  
				"The cause of death was stabbing with a sharp object.\n" +
				"The victim was stabbed multiple times in the region between the abdomen and the throat.\n\n" + 
				"There are no other wounds on the body, nor any traces of poison or other drugs.\n" +
				"Magnus' body is lying face down, his hand reaching forward.\n");
	}
	
	public static void goggles() {
		System.out.println("Found under the table where the body was discovered. Reads heat-signatures.");
	}
	
	// Beginning of blood method
	public static void blood() {
		System.out.println("A pool of blood formed around the body underneath the table. "
				+ "\nA lot of blood was splattered against the inside of the tablecloth."
				+ "\nAlso, there was no sign that the body was dragged through the bloodstain.");
	}
	
	// Beginning of autopsy method
	public static void autopsy() {
		System.out.println("According to Mariane's autopsy, Magnus sustained several puncture wounds to his chest and abdomen. "
				+ "\nThe wounds were inflicted by a thin, sharp weapon roughly 5 mm in diameter.");
	}
	
	// Beginning of threat method
	public static void threat() {
		System.out.println("A threatening letter that was found inside Magnus' pocket. "
				+ "\n\"Be careful. A murder will happen tonight. Someone will definitely kill someone.\" "
				+ "\nThe author of this letter is unknown.");
	}
	
	// Beginning of faint method
	public static void faint() {
		System.out.println("During the blackout, Mariane fainted in the dining hall. By the time the "
				+ "\nlights came back on, she ended up face down on the ground.");
	}
	
	// Beginning of gaps method
	public static void gaps() {
		System.out.println("The main hall's floor is full of gaps. Georgio dropped his earring "
				+ "\nthrough one of them.");
	}
	
	// Beginning of ac method
	public static void ac() {
		System.out.println("The air conditioner in the main hall was set to turn on automatically at 11:30.");
	}
	
	// Beginning of duralumin method
	public static void duralumin() {
		System.out.println("The case Magnus had with him. There's a lot of security equipment inside and a "
				+ "\nhard plastic case.");
	}
	
	// Beginning of testimony method
	public static void testimony() {
		System.out.println("Ilya described the voices she heard during the power outage:");
		System.out.println("Elina: Ah! The power went out!");
		System.out.println("Kade: What's going on?! I can't see anything!");
		System.out.println("Magnus: Hey, what are you doing there?!");
		System.out.println("Terrence: Hey, everyone! Where are you?! The outage isn't just in the kitchen?");
		System.out.println("Sumia: Could someone perhaps have flipped the circuit breakers?");
		System.out.println("Kade: W... Wait here! I'll use the walls as a guide and go see if I can't fix this...!");
	}
	
	// Beginning of fire method
	public static void fire() {
		System.out.println("The hotel's corridor is equipped with doors that can be closed to "
				+ "\nprevent the spread of fire.");
	}
	
	// Beginning of account method
	public static void account() {
		System.out.println("The kitchen range is controlled electronically, and so couldn't be used during the power outage.");
	}
			
	// Beginning of list method
	public static void list() {
		System.out.println("Magnus confiscated every item on the list that could be used as a weapon. "
			+ "\nAccording to the list, there is also a barbecue griddle and a portable hot pot stove in the kitchen.");
	}
			
	// Beginning of tablecloth method
	public static void tablecloth() {
		System.out.println("A bloodstained tablecloth was found randomly stuffed inside the laundry hamper.");
	}
			
	// Beginning of iron method
	public static void iron() {
		System.out.println("There were three irons placed inside the storage room. They were all turned on when you found them.");
	}
			
	// Beginning of ac2 method
	public static void ac2() {
		System.out.println("The air conditioner in the main hall was set to turn on automatically at 11:30." + 
			"\nThe air conditioner in the office was also set to 11:30.");
	}
	
	// Beginning of title
	// Holds println that displays the title of the minigame; accepts a byte value to check which minigame the
	// player is about to play and displays the title accordingly.
	public static void title(byte game) {
		// if the minigame is a debate (value 1) display title of the minigame.
		if (game == 1) {
			System.out.println("\nNONSTOP DEBATE");
		}
		// else if minigame is a multiple choice (3), display title of minigame.
		else if (game == 3) {
			System.out.println("\nMULTIPLE CHOICE");
		}
		// else (minigame is evidence selection (2), display title of minigame.
		else {
			System.out.println("\nEVIDENCE SELECTION");
		}
	}
}
