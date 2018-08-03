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

	/**
	 * <p>Creates the sim's name from the command line</p>
	 * @param args the command line
	 * @return the sim's name
	 */
	private String getName(String[] args) {
		String name = "";
		
		for (int i = 2; i < args.length; i++) {
			name += (args[i] + ' ');
		}
		
		name = name.substring(0, name.length() - 1);
		
		return name;
	}
	
	@Override
	public void execute(SimsAssistant assistant, String[] args) {		
		if (hasEnoughArguments(assistant, args.length, 3)) {
			Console console = assistant.getConsole();
			String name = getName(args);
			
			if (name.contains("-all")) {
				console.writeNotification("Sims may not have -all in their name.");
				return;
			}
			
			Sim sim;
			int k = Tools.convertInteger(args[1]);
			if (Tools.validateInteger(k, 0, 5)) {
				console.partitionLine(2);
				switch (k) {
				case 0:
					sim = new Toddler(assistant, name);
					break;
				case 1:
					sim = new Child(assistant, new TraitSystem(), name);
					break;
				case 2:
					sim = new Teen(assistant, new TraitSystem(), name);
					break;
				default:
					sim = new Adult(assistant, name);
					break;
				}
				
				assistant.getLegacy().addSim(console, sim);
				return;
			} else {
				console.writeNotification("Possible ages for the sims are [0=toddler, 1=child, 2=teen,3=young-adult, 4=adult, 5=elder");
			}
		}
	}
}
