package com.tshcmiller.simsassistant.commands;

import java.io.IOException;

import com.tshcmiller.simsassistant.SimsAssistant;

public class SaveCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		if (hasEnoughArguments(assistant, args.length, 2)) {
			try {
				SimsAssistant.saveLegacyToFile(assistant.getConsole(), args[1]);
			} catch (IOException e) {
				System.err.printf("Failed to save legacy \"%s\"%n", args[1]);
				e.printStackTrace();
			}
		}
	}
}
