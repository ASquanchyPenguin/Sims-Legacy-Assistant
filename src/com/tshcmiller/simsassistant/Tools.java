package com.tshcmiller.simsassistant;

import java.util.Random;

public final class Tools {

	/**
	 * <p>This class shouldn't be instantiated.</p>
	 */
	private Tools() {}
	
	/**
	 * <p>Generates a random integer in the range [min, max].</p>
	 * @param min the minimum bound (inclusive)
	 * @param max the maximum bound (inclusive)
	 * @return a random integer between these bounds.
	 */
	public static int generateRandomInteger(int min, int max) {
		Random random = new Random();
		
		return min + random.nextInt(++max);
	}
	
	/**
	 * <p>Pauses the program for a specified amount of milliseconds</p>
	 * @param millis the number of milliseconds to pause the program
	 */
	public static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.err.printf("%s%n", "Failed to pause the program!");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * <p>Validates if the integer <em>k</em> is the specified range.</p>
	 * @param k the integer to validate
	 * @param min the lower bound (inclusive)
	 * @param max the upper bound (inclusive)
	 * @return if k is between (or equal to) these bounds
	 */
	public static boolean validateInteger(int k, int min, int max) {
		return (k >= min) && (k <= max);
	}
	
	/**
	 * <p>Converts a string input to an integer.</p>
	 * @param input the number to convert
	 * @return the number (if successful) or -1 otherwise
	 */
	public static int convertInteger(String input) {
		int k = -1;
		
		try {
			k = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			new Console().writeNotification("%s is not a valid integer.", input);
		}
		
		return k;
	}
	
}
