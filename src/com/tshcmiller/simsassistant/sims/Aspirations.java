package com.tshcmiller.simsassistant.sims;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.XMLReader;

public final class Aspirations {
		
	private static ArrayList<String> aspirations;
	private static XMLReader xmlAspirations;
	
	/**<p>This class shouldn't be instantiated.</p>*/
	private Aspirations() {}
	
	/**
	 * <p>Gets a random aspiration from the list.</p>
	 * @return the randomly generated aspiration
	 */
	public static String getRandomAspiration() {
		int size = aspirations.size();
		
		return aspirations.get(Tools.generateRandomInteger(0, size - 1));
	}
	
	/**
	 * <p>Loads and creates the aspiration list from a file.</p>
	 * @param console the current instance of the console
	 */
	public static void loadAspriations(Console console) {		
		console.writeDebugText("Loading aspirations from aspirations.xml");
		aspirations = new ArrayList<String>();
		xmlAspirations = new XMLReader("/xml/aspirations.xml");
		
		String[] tags = {"child", "adult"};
		
		for (String tag : tags) {
			for (String node : xmlAspirations.getNodeList(tag)) {
				aspirations.add(node);
			}
		}
		
		console.writeDebugText("%d aspirations loaded from file.", aspirations.size());
	}
	
}
