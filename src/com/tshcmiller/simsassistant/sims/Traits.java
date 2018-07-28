package com.tshcmiller.simsassistant.sims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.XMLReader;

public final class Traits {
	
	private static XMLReader traits;
	private static HashMap<String, String[]> conflictMap;
	private static HashMap<String, String> typeMap;
		
	/**<p>This class shouldn't be instantiated.</p> */
	private Traits() {}
	
	/**
	 * <p>Creates a sublist of age appropriate traits.</p>
	 * @param console the current instance of the console
	 * @param age the age of the sim (0=toddler, 1=child, 2=teen, 3=adult)
	 * @return the list of age appropriate traits
	 */
	public static ArrayList<String> createSubListByAge(Console console, int age) {
		ArrayList<String> sublist = new ArrayList<String>();

		String[] ages = {"toddler", "child", "teen", "adult"};

		if (age > 3 || age < 0) {
			console.writeNotification("%d isn\'t a valid age for this function.", age);
			return sublist;
		}
		
		if (age == 0) {
			return traits.getNodeList(ages[0]);
		}
		
		for (int i = 1; i < (age + 1); i++) {
			sublist.addAll(traits.getNodeList(ages[i]));
		}
		
		return sublist;
	}
	
	/**
	 * <p>Creates a sublist of traits by their type.</p>
	 * @param type The desired type of traits
	 * @return the list of traits with that type
	 */
	public static ArrayList<String> createSubListByType(String type) {
		ArrayList<String> sublist = new ArrayList<String>();
		Set<String> traits = typeMap.keySet();
		
		for (String trait : traits) {
			if (typeMap.get(trait).equals(type)) {
				sublist.add(trait);
			}
		}
		
		return sublist;
	}
	
	/**
	 * <p>Gets a list of Traits that conflict with the specified trait.</p>
	 * @param trait the trait to get conflicts for
	 * @return the list of conflicts
	 */
	public static String[] getTraitConflicts(String trait) {
		return conflictMap.get(trait);
	}
	
	/**
	 * <p>Gets the conflicts associated with this trait.</p>
	 * @param trait the trait to retrieve conflicts for
	 * @return the traits that conflict with it.
	 */
	private static String[] getConflicts(String trait) {
		String data = traits.getAttribute(trait, "conflicts");
		String[] attributes = data.split(",\\s+");
				
		return attributes;
	}
	
	/**
	 * <p>Gets the Trait's type.</p>
	 * @param trait the trait to retrieve the type for
	 * @return the trait type
	 */
	public static String getType(String trait) {
		return traits.getAttribute(trait, "type");
	}
	
	/**
	 * <p>Loads the traits from the console into the XMLReader.</p>
	 * @param console the current instance of the console
	 */
	public static void loadTraits(Console console) {
		int numTraits = 0;
		
		console.writeDebugText("Loading traits from xml-file.");
		traits = new XMLReader("/xml/traits.xml");
		numTraits = traits.getNodeList("adult").size()
			      + traits.getNodeList("teen").size()
			      + traits.getNodeList("child").size()
			      + traits.getNodeList("toddler").size();
		console.writeDebugText("Loaded %d traits.", numTraits);
	}
	
	/**
	 * <p>Writes the Type and Conflict Map from the XMLReader.</p>
	 * @param console the current instance of the console
	 */
	public static void writeMaps(Console console) {
		console.writeDebugText("Writing the type and conflict map.");
		conflictMap = new HashMap<String, String[]>();
		typeMap = new HashMap<String, String>();
		
		String[] ages = {"adult", "teen", "child", "toddler"};
		
		for (String age : ages) {
			ArrayList<String> traits = Traits.traits.getNodeList(age);
			for (String trait : traits) {
				String[] conflicts = getConflicts(trait);
				
				typeMap.put(trait, getType(trait));
				
				if (!conflicts[0].equals("")) {
					conflictMap.put(trait, conflicts);
				}
			}
		}
		
		console.writeDebugText("Wrote %d trait types.", typeMap.size());
		console.writeDebugText("Wrote %d traits with conflicts", conflictMap.size());
	}
	
}
