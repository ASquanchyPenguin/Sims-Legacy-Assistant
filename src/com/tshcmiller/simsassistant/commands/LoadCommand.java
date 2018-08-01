package com.tshcmiller.simsassistant.commands;

import java.io.IOException;

import com.tshcmiller.simsassistant.SimsAssistant;

public class LoadCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		if (hasEnoughArguments(assistant, args.length, 2)) {
			try {
				assistant.loadLegacy(assistant.getConsole(), args[1]);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
