package com.tshcmiller.simsassistant.sims;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.XMLReader;

public final class Aspirations {
		
	private static ArrayList<String> aspirations;
	private static XMLReader xmlAspirations;
	
	/**<p>This class shouldn't be instantiated.</p>*/
	private Aspirations() {}
	
	/**
	 * <p>Gets a random adult aspiration from the list.</p>
	 * @return the randomly generated aspiration
	 */
	public static String getRandomAdultAspiration(SimsAssistant assistant) {
		Legacy legacy = assistant.getLegacy();
		ArrayList<String> aspirations = Aspirations.aspirations;
		
		if (Legacy.preventTraitSharing) {
			System.out.println(legacy.getAspirations());
			aspirations.removeAll(legacy.getAspirations());
		}
		
		if (aspirations.isEmpty()) {
			assistant.getConsole().writeNotification("WARNING: Due to current restrictions, there are no more unique aspirations available. A random one will be chosen.");
			aspirations = Aspirations.aspirations;
		}
		
		int size = aspirations.size() - 1;
		int max = (size - 4);
		
		return aspirations.get(4 + Tools.generateRandomInteger(0, max));
		
	}
	
	/**
	 * <p>Gets a random childhood aspiration from the list.</p>
	 * @return the randomly generated aspiration
	 */
	public static String getRandomChildAspiration() {
		return aspirations.get(Tools.generateRandomInteger(0, 3));
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
		
		console.writeNotification("Loaded %d aspirations from file.", aspirations.size());
	}
	
}
