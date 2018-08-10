package com.tshcmiller.simsassistant.stipulations;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.XMLReader;

public class RandomLifeEvents {

	private static ArrayList<String> randomLifeEvents;
	private static XMLReader xmlFile;
	
	/** <p>This class shouldn't be instantiated.</p> */
	private RandomLifeEvents() {}
	
	/**
	 * <p>Gets a random life event from the list.</p>
	 * @return the event
	 */
	public static String getRandomLifeEvent() {
		return randomLifeEvents.get(Tools.generateRandomInteger(0, randomLifeEvents.size() - 1));
	}
	
	/**
	 * <p>Loads the random life events from file.</p>
	 * @param console the current instance of the console
	 */
	public static void loadRandomLifeEvents(Console console) {
		console.writeDebugText("Loading random life events from XML File");
		randomLifeEvents = new ArrayList<String>();
		xmlFile = new XMLReader("/xml/rles.xml");
		
		for (String node : xmlFile.getNodeList("events")) {
			randomLifeEvents.add(xmlFile.getAttribute(node, "description"));		
		}
		
		console.writeNotification("Loaded %d Random Life Events from file.", randomLifeEvents.size());
	}
}
