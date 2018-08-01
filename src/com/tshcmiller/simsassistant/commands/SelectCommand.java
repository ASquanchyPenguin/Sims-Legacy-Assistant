package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.commands.Command;

public class SelectCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		if (hasEnoughArguments(assistant, args.length, 2)) {
			assistant.getLegacy().selectSim(assistant.getConsole(), args[1]);
		}
	}

}
