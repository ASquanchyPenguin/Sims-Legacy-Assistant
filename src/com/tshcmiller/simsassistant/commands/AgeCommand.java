package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.sims.Sim;

public class AgeCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		Sim sim = null;

		int length = args.length;		
		if (length == 1) {
			sim = SimsAssistant.selectedSim;
			
			if (sim != null) {
				console.partitionLine(2);
				Sim agedSim = sim.ageUp(console);
				SimsAssistant.updateSim(sim.getID(), agedSim);
				SimsAssistant.selectedSim = agedSim;
				console.partitionLine(2);
			} else {
				this.warnNoSelectedSim(console);
			}
			
			return;
		}
		
		if (hasEnoughArguments(assistant, length, 2)) {
			sim = SimsAssistant.getSimByID(args[1]);
			
			if (sim != null) {
				console.partitionLine(2);
				SimsAssistant.updateSim(sim.getID(), sim.ageUp(console));
				console.partitionLine(2);
			} else {
				console.writeNotification("No sim with id \"%s\" was found.", args[1]);
				return;
			}
		}
	}

}
