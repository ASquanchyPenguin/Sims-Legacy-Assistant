package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.SimsAssistant;

public class DeleselectCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		assistant.getLegacy().setSelectedSim(null);
	}

}
