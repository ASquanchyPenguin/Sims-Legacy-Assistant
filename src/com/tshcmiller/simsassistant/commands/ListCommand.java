package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.SimsAssistant;

public class ListCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		SimsAssistant.showCurrentLegacy(assistant.getConsole());
	}

}
