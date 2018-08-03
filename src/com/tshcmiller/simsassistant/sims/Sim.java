package com.tshcmiller.simsassistant.sims;

import java.io.Serializable;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;

public abstract class Sim implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected TraitSystem traitSystem;
	
	protected String aspiration;
	protected String id;
	protected String name;
	
	protected int age;
	
	public Sim(String name) {
		this.name = name;
		this.traitSystem = new TraitSystem();
	}
	
	/**
	 * <p>Ages up the sim.</p>
	 * @param console the current instance of the console
	 * @return the new representation of this sim
	 */
	public abstract Sim ageUp(SimsAssistant assistant);
	
	/**
	 * <p>Assigns an ID to this sim.</p>
	 * @param id the ID this sim will receive
	 */
	public void assignID(String id) {
		this.id = id;
	}
	
	/**
	 * <p>Gets the aspiration for this sim.</p>
	 * @return the aspiration
	 */
	public String getAspiration() {
		return aspiration;
	}
	
	/**
	 * <p>Gets this sim's first name.</p>
	 * @return the name
	 */
	public String getFirstName() {
		return (name.split("\\s+"))[0];
	}
	
	/**
	 * <p>Gets this sim's full name.</p>
	 * @return the full name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * <p>Gets this sim's ID</p>
	 * @return
	 */
	public String getID() {
		return id;
	}

	/**
	 * <p>Displays this sim's aspiration</p>
	 * @param console the current instance of the console
	 */
	public void displayAspiration(Console console) {
		console.partitionLine(2);
		console.printfln("Aspiration: %s", aspiration);
	}
	
	/**
	 * <p>Displays the sim's current traits.</p>
	 * @param console the current instance of the console
	 */
	public void displayTraits(Console console) {
		traitSystem.displayTraits(console);
	}
	
	/**
	 * <p>Display a full list of information about this sim.</p>
	 * @param console the current instance of the console
	 */
	public void displayWhoIs(Console console) {
		console.partitionLine(2);
		console.printfln("Here are the current details for %s", this.toString());
		console.printfln("Sim-ID: %s", id);
		console.printfln("Age: %d (%s)", age, getAgeAsString());
		console.printfln("Aspiration: %s", aspiration);
		console.printfln("Traits: %s", traitSystem.getTraitsAsList());
		console.partitionLine(2);
	}
	
	/**
	 * <p>Gets the title for the current age</p>
	 * @return the title for that age
	 */
	private String getAgeAsString() {
		switch(age) {
			case 0: return "Toddler";
			case 1: return "Child";
			case 2: return "Teen";
			case 3: return "Young Adult";
			case 4: return "Adult";
			case 5: return "Elder";
			default: return "???";
		}		
	}
	
	/**
	 * <p>Gets the trait system for this sim</p>
	 * @return the trait system
	 */
	public TraitSystem getTraitSystem() {
		return traitSystem;
	}
	
	/**
	 * <p>Prints this sim's name out when called.</p>
	 * @return this sim's name
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * <p>Sets the aspiration for this sim.</p>
	 * @param aspiration the new aspiration
	 */
	public void setAspriation(String aspiration) {
		this.aspiration = aspiration;
	}
	
	/**
	 * <p>Sets the name of this sim.</p>
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * <p>Sets the trait system for this system </p>
	 * @param traitSystem the new trait system.
	 */
	public void setTraitSystem(TraitSystem traitSystem) {
		this.traitSystem = traitSystem;
	}
	
}
