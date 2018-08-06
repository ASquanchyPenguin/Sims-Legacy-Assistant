package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;

public class BLCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		
		int length = args.length;
		if (hasEnoughArguments(assistant, length, 2)) {
			String input = args[1];
			
			if (input.equalsIgnoreCase("on")) {
				Legacy.preventTraitSharing = true;
				console.writeNotification("Balanced-Legacy is on.");
			} else if (input.equalsIgnoreCase("off")) {
				Legacy.preventTraitSharing = false;
				console.writeNotification("Balanced-Legacy is off.");
			} else {
				this.warnIllegalArgument(assistant, args[1], args[0]);
			}
		}
	}

}
