package com.tshcmiller.simsassistant.sims;

import java.util.ArrayList;
import java.util.HashSet;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Tools;

public class TraitSystem {
	
	/**
	 * <p>The current mode of the TraitSystem. Each mode generates a random trait in a different way.</p>
	 * <ol>
	 *  <li>RANDOM: Randomly assigns traits, but avoids conflicts.</li>
	 *  <li>BALANCED_MOOD: Assigns up to one emotional trait, with no other filters.</li>
	 *  <li>BALANCED_TYPE: Assigns up to one trait per type.</li>
	 * </ol>
	 */
	public static TraitSystemMode mode = TraitSystemMode.BALANCED_MOOD;
	
	private ArrayList<String> traits;
	
	public TraitSystem() {
		this.traits = new ArrayList<String>();
	}
	
	public TraitSystem(ArrayList<String> traits) {
		this.traits = traits;
	}
	
	/**
	 * <p>Adds a random trait to the system that filters out emotional traits if it already has one.</p>
	 * @param console the current instance of the console
	 * @param sublist the current sublist of possible traits
	 */
	private void addBalancedMoodTrait(Console console, ArrayList<String> sublist) {
		HashSet<String> types = this.getTraitTypes();

		if (types.contains("emotional")) {
			console.writeDebugText("Found emotional trait. Removing them from sublist.");
			sublist.removeAll(Traits.createSubListByType("emotional"));
		}
		
		addRandomTrait(console, sublist);
	}
	
	/**
	 * <p>Adds a random trait that only allows one trait per type.</p>
	 * @param console the current instance of the console
	 * @param sublist the current sublist of possible traits
	 */
	private void addBalancedTypeTrait(Console console, ArrayList<String> sublist) {
		HashSet<String> types = this.getTraitTypes();
		
		for (String type : types) {
			sublist.removeAll(Traits.createSubListByType(type));
		}
		
		addRandomTrait(console, sublist);
	}
	
	/**
	 * <p>Adds a random trait to the system.</p>
	 * @param console the current instance of the console
	 * @param sublist the current sublist of possible traits
	 */
	private void addRandomTrait(Console console, ArrayList<String> sublist) {
		String trait = sublist.get(Tools.generateRandomInteger(0, sublist.size() - 1));
		console.writeNotification("The trait \"%s\" has been acquired!", trait);
		traits.add(trait);
	}
	
	/**
	 * <p>Adds a random trait to the system, depending on its current mode.</p>
	 * @param console the current instance of the console
	 */
	public void addTrait(Console console) {		
		if (traits.size() >= 4) {
			console.writeNotification("No more traits may be added.");
			return;
		}
		
		ArrayList<String> sublist = Traits.createSubListByAge(console, traits.size());
		sublist.removeAll(getConflicts());
				
		switch (mode) {
		case BALANCED_MOOD:
			addBalancedMoodTrait(console, sublist);
			break;
		
		case BALANCED_TYPE:
			addBalancedTypeTrait(console, sublist);
			break;
			
		default:
			addRandomTrait(console, sublist);
			break;
		}
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
	public void fillTraits(Console console, int totalTraits) {
		if (totalTraits <= 0 || totalTraits > 4) {
			console.writeNotification("%d isn\'t a valid number of traits this system can have.", totalTraits);
			return;
		}
		
		int initSize = traits.size();
		
		while (traits.size() < totalTraits) {
			addTrait(console);
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
	 * <p>Gets the number of traits in this system.</p>
	 * @return the number of traits.
	 */
	public int getNumTraits() {
		return traits.size();
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