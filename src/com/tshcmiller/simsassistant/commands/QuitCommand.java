package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.SimsAssistant;

public class QuitCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		assistant.stopRunning();
	}

}
