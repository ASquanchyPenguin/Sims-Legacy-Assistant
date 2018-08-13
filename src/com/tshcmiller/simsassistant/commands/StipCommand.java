package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.sims.Adult;
import com.tshcmiller.simsassistant.sims.Sim;

public class StipCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		Legacy legacy = assistant.getLegacy();
		Sim sim = null;

		String stip = "trait";
		int length = args.length;
		
		switch (length) {
		case 1:
			sim = legacy.getSelectedSim();
		break;
		
		case 2:
			sim = legacy.getSimByID(args[1]);
		break;
		
		case 3:
			sim = legacy.getSimByID(args[2]);
			stip = args[1];
		break;
		
		default:
			console.printfln("No sim with that ID was found.");
			return;
		}
		
		if (sim instanceof Adult) {
			Adult adult = (Adult) sim;
			
			stip = stip.toLowerCase();
			if (stip.equals("job")) {
				adult.rollJobStipulation(console);
			}
			
			else if (stip.equals("trait")) {
				adult.addStipulation(console);
			}
		}
	}
}
