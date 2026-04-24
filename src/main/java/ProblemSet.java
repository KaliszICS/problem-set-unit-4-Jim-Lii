/*
File Name: Problem Set Unit 4 (100%)
Author: Jim Li
Date Created: Apr. 20, 2026
Date Last Modified: Apr. 24, 2026
*/

import java.util.Scanner;
import java.util.Random;
public class ProblemSet {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Random random = new Random();

		//welcome message
		System.out.println("Welcome to the High Low Guessing Game.\n");
		System.out.print("Input a number of rounds to play: ");
		int rounds = 0;

		//get rounds
		while (rounds < 1) { //continues until rounds > 0
			while (!input.hasNextInt()) { //is integer?
				input.nextLine();
				System.out.print("\nInvalid Input!\nInput a number of rounds to play: ");
			}
			rounds = input.nextInt();
			if (rounds < 1) {
				System.out.print("\nInvalid Input!\nInput a number of rounds to play: ");
			}
			input.nextLine();
		}

		//get range
		String range = "", rangeLeft = "", rangeRight = "";
		int splitIndex = -1, bottomRange = 0, topRange = 0;
		System.out.println("\nWhat Range would you like to play between (#-#)?");
		while (topRange - bottomRange < 2) { //while there are less than 3 possible numbers
			while (!isNumber(rangeLeft) || !isNumber(rangeRight)) { //while each side of the range is not a number
				while (splitIndex < 0) { //while there is not a point to split
					range = input.nextLine();
					splitIndex = splitIndex(range);
					if (splitIndex < 0) {
						System.out.println("\nInvalid Input!\nWhat Range would you like to play between (#-#)?");
					}
				}
				rangeLeft = range.substring(0, splitIndex); //split the range
				rangeRight = range.substring(splitIndex + 1);
				if (!isNumber(rangeLeft) || !isNumber(rangeRight)) {
					System.out.println("\nInvalid Input!\nWhat Range would you like to play between (#-#)?");
					splitIndex = -1;
				}
			}
			bottomRange = Math.min(Integer.parseInt(rangeLeft), Integer.parseInt(rangeRight));
			topRange = Math.max(Integer.parseInt(rangeLeft), Integer.parseInt(rangeRight));
			if (topRange - bottomRange < 2) {
			    rangeLeft = "";
			    rangeRight = "";
			    splitIndex = -1;
				System.out.println("\nInvalid Input!\nWhat Range would you like to play between (#-#)?");
			}
		}

		// get ranges for high, low, and even
		double evenPoint = (bottomRange + topRange) / 2.0;
		int bottomEvenPoint = (int) Math.floor(evenPoint), topEvenPoint = (int) Math.ceil(evenPoint);
		int selectedOption = 0, score = 0;
		boolean correct;

		//game start
		for (int i = 1; i <= rounds; i++) {
			int ranNum = random.nextInt(topRange - bottomRange + 1) + bottomRange; //get random number
			System.out.println("\nRound " + i + ":");
			String prompt = prompt(bottomRange, bottomEvenPoint, topEvenPoint, topRange);
			System.out.println(prompt);
			while (selectedOption > 3 || selectedOption < 1) { // until integer is 1, 2, or 3
				while (!input.hasNextInt()) { // until input is integer
					input.nextLine();
					System.out.println("\nInvalid Input!\n" + prompt);
				}
				selectedOption = input.nextInt();
				if (selectedOption > 3 || selectedOption < 1) {
					System.out.println("\nInvalid Input!\n" + prompt);
				}
			}
			input.nextLine(); //reset stuff for next round
			prompt = "";
			correct = check(selectedOption, ranNum, bottomRange, topRange, bottomEvenPoint, topEvenPoint);

			System.out.print("\nThe number was " + ranNum + ", You were ");
			if (correct) {
				System.out.println("correct.");
				score++;
			} else {
				System.out.println("incorrect.");
			}
			System.out.println("Current score: " + score);
			selectedOption = 0;
		}

		//final results
		System.out.println("\nTotal Score: " + score);
		if (rounds/2.0 > score) {
			System.out.print("You got " + score + " out of " + rounds + " correct. Better luck next time.");
		} else {
			System.out.print("Congratulations you got " + score + " out of " + rounds + " rounds right!");
		}
	}

	//method checks if range is splittable, then returns the index of the split point
	public static int splitIndex(String range) {
		if (!range.contains("-")) {
			return -1; //if no dash
		}
		if (range.startsWith("-")) {
			return range.indexOf("-", 1); //if starts with dash
		}
		return range.indexOf("-"); //if does not start with dash
	}

	//method checks if something is a number
	public static boolean isNumber(String num) {
		// check if the string is a number
		if (num.startsWith("-")) { //remove dash
			num = num.substring(1);
		}
		if (num.length() == 0) { //no 0 length string or "-"
			return false;
		}
		for (int i = 0; i < num.length(); i++) { //check characters if they are numbers
			if (num.charAt(i) < '0' || num.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}
	
	//method makes prompt
	public static String prompt(int bottomRange, int bottomEvenPoint, int topEvenPoint, int topRange){
	    String prompt = "Please select High, Low, or Even:\n1. High (" + (topEvenPoint + 1);
			if (topEvenPoint + 1 != topRange){
			    prompt += " to " + topRange;
			}
			prompt += ")\n2. Low (" + bottomRange;
			if (bottomRange != bottomEvenPoint - 1){
			    prompt += " to " + (bottomEvenPoint - 1);
			}
			prompt += ")\n3. Even (" + bottomEvenPoint;
			if (bottomEvenPoint != topEvenPoint){
			    prompt += " to " + topEvenPoint;
			}
			prompt += ")";
			return prompt;
	}

	//method checks selected option. Correct = true, incorrect = false
	public static boolean check(int selectedOption, int ranNum, int bottomRange, int topRange, int bottomEvenPoint, int topEvenPoint) {
		if (selectedOption == 1) { //high
			if (ranNum >= topEvenPoint && ranNum <= topRange) {
				return true;
			}
			return false;
		}
		if (selectedOption == 2) { //low
			if (ranNum >= bottomRange && ranNum <= bottomEvenPoint) {
				return true;
			}
			return false;
		}
		else { //even
			if (ranNum == bottomEvenPoint || ranNum == topEvenPoint) {
				return true;
			}
			return false;
		}
	}
}