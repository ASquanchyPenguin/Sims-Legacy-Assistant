package com.tshcmiller.simsassistant;

import java.util.Scanner;

public class Console {

	private Scanner scanner;
	
	private boolean showDebugText;
	
	public Console() {
		this.scanner = new Scanner(System.in);
		this.showDebugText = false;
	}
	
	/**
	 * <p>Breaks the output to the next line.</p>
	 */
	public void breakLine() {
		System.out.println();
	}
	
	/**
	 * <p>Clears the console to create some space for the line of output.</p>
	 */
	public void clear() {
		int size = (int) (Settings.getValue("clrsize"));
		
		for (int i = 0; i < size; i++) {
			breakLine();
		}
	}
	
	/**
	 * <p>Closes the scanner associated with this Console.</p>
	 */
	public void close() {
		scanner.close();
	}
	
	/**
	 * <p>Asks the user a yes/no question.</p>
	 * @param prompt the question they will answer
	 * @return if the user entered yes
	 */
	public boolean confirmActionAsString(String prompt) {
		String input = "";
		
		while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
			printf("%s%nEnter \"yes\" or \"no\": ", prompt);
			input = readLine();
			breakLine();
		}
		
		return input.equalsIgnoreCase("yes");
	}
	
	/**
	 * <p>Asks the user a yes/no question.</p>
	 * @param prompt the question they will answer
	 * @return if the user entered 1
	 */
	public boolean confirmAction(String prompt) {
		int k = -1;
		
		breakLine();
		while (k == -1) {
			printList(new String[] {prompt, "Yes", "No"});
			k = readInteger(1, 2);
			breakLine();
		}		
		
		return (k == 1);
	}
	
	/**
	 * <p>Finds the largest cell within the table.</p>
	 * @param list the table to consider
	 * @return the largest cell length
	 */
	private int getMaxCellLength(String[][] list) {
		int length = 0;
		int max = 0;
		
		//Determine the largest cell in the table
		for (int i = 0; i < list.length; i++) {
			length = getMaxStringLength(list[i]);
			
			if (max < length) {
				max = length;
			}
		}
		
		return max;
	}
	
	/**
	 * <p>Finds the largest length of all of the strings in the array.</p>
	 * @param list the list of arrays to check
	 * @return the largest length found in the list
	 */
	private int getMaxStringLength(String[] list) {
		int length = list.length;
		int max = 0;
		
		if (length != 0) {
			max = list[0].length();
			
			for (int i = 0; i < length; i++) {
				if (list[i].length() > max) {
					max = list[i].length();
				}
			}
		}
		
		return max;
	}
	
	/**
	 * <p>Creates a line to divide the output into a new section.</p>
	 * @param length the number of segments to write
	 */
	public void partitionLine(int length) {
		for (int i = 0; i < 25 * length; i++) {
			System.out.print("=");
		}
		
		breakLine();
	}
	
	/**
	 * <p>Prints out a formatted String. </p>
	 * @param format the desired format of the string
	 * @param objects the objects to format into the string
	 */
	public void printf(String format, Object... objects) {
		System.out.printf(format, objects);
	}
	
	/**
	 * <p>Prints out a formatted String and adds a new line after it.</p>
	 * @param format the desired format of the string
	 * @param objects the objects to format into the string
	 */
	public void printfln(String format, Object... objects) {
		printf(format + "%n", objects);
	}
	
	/**
	 * <p>Prints out a numbered list in descending order. The 0th element is considered to be the Title of the list.</p>
	 * @param list the list of strings to print out
	 */
	public void printList(String... list) {		
		for (int i = 0; i < list.length; i++) {
			printfln("%d. %s", i, list[i]);
		}
		
	}
	
	/**
	 * <p>Prints out a list of Strings in a formatted table. The table is read by row.</p>
	 * <p>NOTE: Each row should be equal in length. This is not the case for the bottom row.</p>
	 * @param title the title of the table
	 * @param list the list of Strings to format
	 */
	public void printTable(String title, String[][] list) {
		int max = getMaxCellLength(list);
		int length = list.length;
		int cell = 0;
		
		String format = "[%d] %-" + max + "s ";
		
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < list[i].length; j++) {
				cell = (i + 1) + j * length;
				
				printf(format, cell, list[i][j]);
			}
			
			breakLine();
		}	
	}
	
	/**
	 * <p>Reads and validates the integer entered into the Console.</p>
	 * @param min The lower bound for this integer (inclusive) 
	 * @param max The upper bound for this integer (inclusive)
	 * @return the integer entered into console or -1 if unsuccessful
	 */
	public int readInteger(int min, int max) {
		write("Your selection: ");
		
		String line = scanner.nextLine();
		int k = -1;

		try {
			k = Integer.parseInt(line);
			
			if (!Tools.validateInteger(k, min, max)) {
				writeWarning("%d is not in the range [%d, %d]", k, min, max);
				return -1;
			}
			
		} catch (NumberFormatException e) {
			writeWarning("%s is not an integer!", line);
			return -1;
		}
		
		return k;
	}
	
	/**
	 * <p>Reads the next line of input to the Console.</p>
	 * @return the line entered into the Console
	 */
	public String readLine() {
		return scanner.nextLine();
	}
	
	/**
	 * <p>Reads the next line of input to the Console.</p>
	 * @return an array of each word in the line
	 */
	public String[] readLineArray() {		
		return readLine().split("\\s+");
	}
	
	/**
	 * <p>Sets the value to show the debug text in the Console.</p>
	 * @param showDebugText the desired value for the feature
	 */
	public void setShowDebugText(boolean showDebugText) {
		writeNotification("Debug mode is set to %b.", showDebugText);
		this.showDebugText = showDebugText;
	}
	
	/**
	 * <p>Waits for the user to press a key.</p>
	 */
	public void waitForKeyPress() {
		write("Press enter to contine...");
		readLine();
	}
	
	/**
	 * <p>Prints out the String <em>s</em>.</p>
	 * @param s the String to output
	 */
	public void write(String s) {
		System.out.print(s);
	}
	
	/**
	 * <p>Prints out the String <em>s</em> and creates a new line.</p>
	 * @param s the String to output
	 */
	public void writeln(String s) {
		System.out.println(s);
	}
	
	/**
	 * <p>Prints out the String if debug mode is active. Adds a new line at the end.</p>
	 * @param format the desired format of the output
	 * @param objects the necessary objects for that format
	 */
	public void writeDebugText(String format, Object... objects) {
		if (showDebugText) {
			format = "[debug]: " + format;
			printfln(format, objects);
		}
	}
	
	/**
	 * <p>Prints out a notification to the console.</p>
	 * @param s the text to output
	 */
	public void writeNotification(String format, Object... objects) {
		format = "[info]: " + format;
		printfln(format, objects);
	}
	
	/**
	 * <p>Prints out a warning to the console.</p>
	 * @param s the text to output
	 */
	public void writeWarning(String format, Object... objects) {
		format = "WARNING: " + format;
		breakLine();
		partitionLine(3);
		printfln(format, objects);
		partitionLine(3);
	}
}
