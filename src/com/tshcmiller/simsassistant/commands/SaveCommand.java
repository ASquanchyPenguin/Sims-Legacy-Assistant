package com.tshcmiller.simsassistant.commands;

import java.io.IOException;

import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;

public class SaveCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Legacy legacy = assistant.getLegacy();
		String name = "";
		
		if (args.length == 1 && legacy.hasName()) {
			name = legacy.getName();
		} 
		else if (hasEnoughArguments(assistant, args.length, 2)) {
			name = args[1];
		}
		else {
			assistant.getConsole().printfln("Unable to determine the name of the legacy.");
			return;
		}
		
		try {
			assistant.saveLegacyToFile(assistant.getConsole(), name);
		} catch (IOException e) {
			System.err.printf("Failed to save legacy \"%s\"%n", name);
			e.printStackTrace();
		}
	}
}
