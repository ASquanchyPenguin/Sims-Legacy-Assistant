package com.tshcmiller.simsassistant.sims;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.stipulations.Stipulations;

public class Adult extends Sim {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<String> stipulations;
	private int maxCareerLevel;

	public Adult(SimsAssistant assistant, String name, int age, TraitSystem traitSystem) {
		super(name);
		
		this.age = age;
		this.traitSystem = traitSystem;
		this.stipulations = new ArrayList<String>();
		this.maxCareerLevel = -1;
	}
	
	public Adult(SimsAssistant assistant, TraitSystem system, String name) {
		this(assistant, name, 3, system);
	}

	
	public Adult(SimsAssistant assistant, Teen teen) {
		this(assistant, teen.name, 3, teen.traitSystem);
		this.id = teen.id;
		this.aspiration = teen.aspiration;
	}

	public void addStipulation(Console console) {
		if (stipulations.size() == 3) {
			console.printfln("%s already has the max number of stipulations.", name);
			return;
		}
		
		ArrayList<String> validAspirations = Stipulations.getStipulations();
		validAspirations.removeAll(stipulations);
		
		String stipulation = validAspirations.get(Tools.generateRandomInteger(0, validAspirations.size() - 1));
		stipulations.add(stipulation);
		console.partitionLine(4);
		console.printfln(stipulation);
		console.partitionLine(4);
	}
	
	@Override
	public Sim ageUp(SimsAssistant assistant) {
		Console console = assistant.getConsole();
		
		switch (++age) {
		case 4:
			console.writeNotification("Well, another year, another age. %s is now an Adult", this.toString());
			break;
		case 5:
			console.writeNotification("%s has reached the golden years.", this.toString());
			break;
		default:
			console.writeNotification("%s is already max age!", this.toString());
			age = 5;
		}
		
		return this;
	}
	
	@Override
	public void displayWhoIs(Console console) {
		console.partitionLine(2);
		console.printfln("Here are the current details for %s", this.toString());
		console.printfln("Sim-ID: %s", id);
		console.printfln("Age: %d (%s)", age, getAgeAsString());
		console.printfln("%nAspiration: %s", aspiration);
		console.printfln("Traits: %s%n", traitSystem.getTraitsAsList());
		showStipulations(console);
		console.partitionLine(2);
	}
	
	public int getMaxCareerLevel() {
		return maxCareerLevel;
	}
	
	public ArrayList<String> getStipulations() {
		return stipulations;
	}

	@Override
	public void rollTraits(SimsAssistant assistant) {
		this.traitSystem.fillTraits(assistant, name, 4);
	}
	
	public void setMaxCareerLevel(int level) {
		this.maxCareerLevel = level;
	}
	
	public void showStipulations(Console console) {
		if (stipulations.isEmpty()) {
			console.printfln("No general stipulations.");
			return;
		} 
		
		console.printfln("Max Career Level: %d%n", maxCareerLevel);
		
		int k = 1;
		console.printfln("Stipulations");
		for (String stip : stipulations) {
			console.printfln("  %d. %s", k++, stip);
		}
	}
}
