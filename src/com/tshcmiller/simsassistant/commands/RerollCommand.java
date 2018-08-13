package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.sims.Sim;

public class RerollCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		Sim sim = null;
		
		int length = args.length;
		
		if (hasEnoughArguments(assistant, length, 2)) {
			String action = args[1];
			
			if (length == 2) {
				sim = assistant.getSelectedSim();
			}
			
			if (length == 3) {
				sim = assistant.getLegacy().getSimByID(args[2]);
			}
			
			if (sim != null) {
				if (action.equals("aspiration")) {
					console.printfln("Rerolling aspiration for %s.", sim.getName());
					sim.rollAspiration(assistant);
				}
				
				else if (action.equals("trait")) {
					console.printfln("Rerolling traits for %s.", sim.getName());
					sim.getTraitSystem().getTraits().clear();
					sim.rollTraits(assistant);
				}
			} else {
				console.printfln("Sim not recognized.");
			}
		}
	}

}
