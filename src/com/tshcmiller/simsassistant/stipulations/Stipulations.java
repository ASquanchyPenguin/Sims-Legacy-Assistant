package com.tshcmiller.simsassistant.stipulations;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.XMLReader;

public final class Stipulations {

	private static ArrayList<String> stipulations;
	private static XMLReader xmlFile;
	
	/** <p>This class shouldn't be instantiated.</p> */
	private Stipulations() {}
	
	/**
	 * <p>Gets a random life event from the list.</p>
	 * @return the event
	 */
	public static String getRandomStipulation() {
		return stipulations.get(Tools.generateRandomInteger(0, stipulations.size() - 1));
	}
	
	public static ArrayList<String> getStipulations() {
		return stipulations;
	}
	
	/**
	 * <p>Loads the stipulations from file.</p>
	 * @param console the current instance of the console
	 */
	public static void loadStipulations(Console console) {
		console.writeDebugText("Loading random life events from XML File");
		stipulations = new ArrayList<String>();
		xmlFile = new XMLReader("/xml/stipulations.xml");
		
		for (String node : xmlFile.getNodeList("stipulations")) {
			stipulations.add(xmlFile.getAttribute(node, "description"));		
		}
		
		console.writeNotification("Loaded %d Stipulations from file.", stipulations.size());
	}
	
	/**
	 * <p>Rolls job stipulations for the sim.</p>
	 * @param console the current instance of the console
	 */
	public static void rollJobStipulations(Console console) {
		int deadDays = Tools.generateRandomInteger(1, 7);
		int maxLevel = Tools.generateRandomInteger(1, 10);
		
		console.partitionLine(4);
		console.printfln("This Sim must wait %d days to get a job. Their max career level is %d.", deadDays, maxLevel);
		
	}
}
