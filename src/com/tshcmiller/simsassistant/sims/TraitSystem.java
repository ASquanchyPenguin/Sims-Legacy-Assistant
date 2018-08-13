package com.tshcmiller.simsassistant.sims;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.Tools;

public class TraitSystem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * <p>The current mode of the TraitSystem. Each mode generates a random trait in a different way.</p>
	 * <ol>
	 *  <li>RANDOM: Randomly assigns traits, but avoids conflicts.</li>
	 *  <li>BALANCED_MOOD: Assigns up to one emotional trait, with no other filters.</li>
	 *  <li>BALANCED_TYPE: Assigns up to one trait per type.</li>
	 * </ol>
	 */
	public static TraitSystemMode mode;
		
	private ArrayList<String> traits;
	
	public TraitSystem() {
		this(new ArrayList<String>());
	}
	
	public TraitSystem(ArrayList<String> traits) {
		this.traits = traits;
	}
	
	/**
	 * <p>Adds a random trait to the system that filters out emotional traits if it already has one.</p>
	 * @param console the current instance of the console
	 * @param sublist the current sublist of possible traits
	 */
	private String addBalancedMoodTrait(Console console, ArrayList<String> sublist) {
		HashSet<String> types = this.getTraitTypes();

		if (types.contains("emotional")) {
			console.writeDebugText("Found emotional trait. Removing them from sublist.");
			sublist.removeAll(Traits.createSubListByType("emotional"));
		}
		
		return addRandomTrait(console, sublist);
	}
	
	/**
	 * <p>Adds a random trait that only allows one trait per type.</p>
	 * @param console the current instance of the console
	 * @param sublist the current sublist of possible traits
	 */
	private String addBalancedTypeTrait(Console console, ArrayList<String> sublist) {
		HashSet<String> types = this.getTraitTypes();
		
		for (String type : types) {
			sublist.removeAll(Traits.createSubListByType(type));
		}
		
		return addRandomTrait(console, sublist);
	}
	
	/**
	 * <p>Adds a random trait to the system.</p>
	 * @param console the current instance of the console
	 * @param sublist the current sublist of possible traits
	 */
	private String addRandomTrait(Console console, ArrayList<String> sublist) {
		if (sublist.isEmpty()) {
			console.writeNotification("WARNING: Due to the current restrictions, no more unique traits are available. A random trait will be added.");
			sublist = Traits.createSubListByAge(console, traits.size());
			sublist.removeAll(getConflicts());
		}
		
		String trait = sublist.get(Tools.generateRandomInteger(0, sublist.size() - 1));
		traits.add(trait);
		
		return trait;
	}
	
	/**
	 * <p>Adds a random trait to the system, depending on its current mode.</p>
	 * @param console the current instance of the console
	 */
	public void addTrait(SimsAssistant assistant, String name) {		
		Console console = assistant.getConsole();
		Legacy legacy = assistant.getLegacy();
		
		if (traits.size() >= 4) {
			console.writeNotification("%s already has all their traits.");
			return;
		}
		
		ArrayList<String> sublist = Traits.createSubListByAge(console, traits.size());
		sublist.removeAll(getConflicts());
		
		if (Legacy.preventTraitSharing) {
			sublist.removeAll(legacy.getLegacyTraits());
		}
		
		String trait = "";
		switch (mode) {
		case BALANCED_MOOD:
			trait = addBalancedMoodTrait(console, sublist);
			break;
		
		case BALANCED_TYPE:
			trait = addBalancedTypeTrait(console, sublist);
			break;
			
		default:
			trait = addRandomTrait(console, sublist);
			break;
		}
		
		console.writeNotification("%s has acquired the trait %s!", name, trait);
	}
	
	/**
	 * <p>Displays the current traits in the system.</p>
	 * @param console the current instance of the console
	 */
	public void displayTraits(Console console) {
		console.partitionLine(2);
		console.printfln("Current Traits");
		
		String[] ages = {"Toddler", "Childhood", "Adolescence", "Adulthood"};
		for (int i = 0; i < traits.size(); i++) {
			console.printfln("%-12s: %s", ages[i], traits.get(i));
		}		
	}
	
	/**
	 * <p>Adds a certain number of traits to the system. No more than 4 total traits can be used at a time.</p>
	 * @param console the current instance of the console
	 * @param totalTraits the number of traits to add to the system
	 */
	public void fillTraits(SimsAssistant assistant, String name, int totalTraits) {
		Console console = assistant.getConsole();
		
		if (totalTraits <= 0 || totalTraits > 4) {
			console.writeNotification("%d isn\'t a valid number of traits this system can have.", totalTraits);
			return;
		}
		
		int initSize = traits.size();
		
		while (traits.size() < totalTraits) {
			addTrait(assistant, name);
		}
		
		console.writeDebugText("Added %d traits to system.", traits.size() - initSize);
	}
	
	/**
	 * <p>Creates a list of traits that conflict with the current traits in the system.</p>
	 * @return the list of conflicting traits
	 */
	public ArrayList<String> getConflicts() {
		ArrayList<String> conflicts = new ArrayList<String>();
		
		for (String trait : traits) {
			conflicts.add(trait);
			
			String[] list = Traits.getTraitConflicts(trait);
			
			if (list == null) {
				continue;
			}
			
			for (String conflict : list) {
				conflicts.add(conflict);
			}			
		}
		
		return conflicts;
	}
	
	/**
	 * <p>Gets the number of traits in the system.</p>
	 * @return the number of traits
	 */
	public int getSize() {
		return traits.size();
	}
	
	/**
	 * <p>Gets the number of traits in this system.</p>
	 * @return the number of traits.
	 */
	public int getNumTraits() {
		return traits.size();
	}
	
	/**
	 * <p>Gets the traits for this system.</p>
	 * @return the traits
	 */
	public ArrayList<String> getTraits() {
		return traits;
	}
	
	/**
	 * <p>Gets the system traits as a list.</p>
	 * @return the list
	 */
	public String getTraitsAsList() {
		return traits.toString();
	}
	
	/**
	 * <p>Creates a set of the current types of traits in the system</p>
	 * @return the set of trait types
	 */
	private HashSet<String> getTraitTypes() {
		HashSet<String> set = new HashSet<String>();
		
		for (String trait : traits) {
			set.add(Traits.getType(trait));
		}
		
		return set;
	}
	
	/**
	 * <p>Determines if the system is full of traits.</p>
	 * @return the result of this check
	 */
	public boolean isFull() {
		return (traits.size() == 4);
	}
}