package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.sims.Adult;
import com.tshcmiller.simsassistant.sims.Child;
import com.tshcmiller.simsassistant.sims.Sim;
import com.tshcmiller.simsassistant.sims.Teen;
import com.tshcmiller.simsassistant.sims.Toddler;
import com.tshcmiller.simsassistant.sims.TraitSystem;

public class CreateCommand extends Command {

	private Sim createNewSim(SimsAssistant assistant, int age, String name) {
		switch (age) {
		case 0:
			return new Toddler(assistant, name);
		case 1:
			return new Child(assistant, new TraitSystem(), name);
		case 2:
			return new Teen(assistant, new TraitSystem(), name);
		default:
			return new Adult(assistant, name, age, new TraitSystem());
		}
	}
	
	/**
	 * <p>Creates the sim's name from the command line</p>
	 * @param args the command line
	 * @return the sim's name
	 */
	private String getName(String[] args) {
		String name = "";
		
		for (int i = 1; i < args.length; i++) {
			name += (" " + args[i]);
		}
		
		return name.trim();
	}
	
	@Override
	public void execute(SimsAssistant assistant, String[] args) {		
		if (hasEnoughArguments(assistant, args.length, 2)) {
			Console console = assistant.getConsole();
			String name = getName(args);
			String input = "";
			
			console.partitionLine(3);
			console.writeNotification("%s has been created!", name);
			console.partitionLine(3);
			
			int age = -1;
			while (!Tools.validateInteger(age, 0, 6)) {
				console.writeNotification("Note: 0=toddler, 1=child, 2=teen, 3=young-adult, 4=adult, 5=elder, 6=random.");
				console.write("Please enter an age for " + name + ": ");
				input = console.readLine();
				age = Tools.convertInteger(input);
				console.breakLine();
			}
			
			age = (age == 6) ? (Tools.generateRandomInteger(0, 5)) : (age);
			
			Sim sim = createNewSim(assistant, age, name);
			
			if (!console.confirmActionAsString("Do you want to randomize age appropriate traits and aspirations?")) {
				console.writeNotification("You can edit this Sim when needed with \"/rr [trait/aspiration].\"");
				sim.setAspriation("None");
			} else {
				sim.rollTraits(assistant);
				sim.rollAspiration(assistant);
			}
			
			console.partitionLine(3);
			assistant.getLegacy().addSim(console, sim);
		}
	}
}
