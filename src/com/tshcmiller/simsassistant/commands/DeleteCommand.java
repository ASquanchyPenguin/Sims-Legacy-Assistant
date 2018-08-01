package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.sims.Sim;

public class DeleteCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		int length = args.length;
		Console console = new Console();
		Legacy legacy = assistant.getLegacy();
		
		if (length == 1) {
			Sim sim = assistant.getSelectedSim();
			
			if (sim != null) {
				legacy.deleteSim(console, sim.getID());
			} else {
				this.warnNoSelectedSim(console);
			}
			
			return;
		}
		
		if (length == 2) {
			if (args[1].equalsIgnoreCase("-all")) {
				legacy.deleteAllSims(console);
				return;
			}
			
			legacy.deleteSim(console, args[1]);
			return;
		}
		
		if (hasEnoughArguments(assistant, length, 3)) {
			if (args[1].equalsIgnoreCase("-legacy")) {
				SimsAssistant.deleteLegacy(console, args[2]);
			}
		}
	}

}
