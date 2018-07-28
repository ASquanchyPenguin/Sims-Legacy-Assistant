package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.sims.Sim;

public class ShowCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		Sim sim = null;

		int length = args.length;		
		if (length == 1) {
			sim = SimsAssistant.selectedSim;
			
			if (sim != null) {
				sim.displayWhoIs(console);
			} else {
				this.warnNoSelectedSim(console);
			}
			
			return;
		}
		
		if (hasEnoughArguments(assistant, length, 2)) {
			sim = SimsAssistant.getSimByID(args[1]);
			
			if (sim != null) {
				sim.displayWhoIs(console);
			} else {
				console.writeNotification("No sim with id \"%s\" was found.", args[1]);
				return;
			}
		}
	}

}
