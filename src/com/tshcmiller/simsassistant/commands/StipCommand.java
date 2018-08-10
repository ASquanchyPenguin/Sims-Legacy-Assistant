package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.sims.Adult;
import com.tshcmiller.simsassistant.sims.Sim;

public class StipCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		Sim sim = null;

		int length = args.length;		
		if (length == 1) {
			sim = assistant.getSelectedSim();
		}
		
		if (length == 2) {
			sim = assistant.getLegacy().getSimByID(args[1]);
		}
		
		if (sim != null) {
			if (sim instanceof Adult) {
				Adult adult = (Adult) sim;
				adult.addStipulation(console);
				return;
			}
			
			console.printfln("%s is not old enough for stipulations.", sim.getName());
			return;
		}
	}
}
