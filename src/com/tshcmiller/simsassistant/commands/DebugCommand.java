package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;

public class DebugCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		
		int length = args.length;
		if (hasEnoughArguments(assistant, length, 2)) {
			String input = args[1].toLowerCase();
			
			if (input.equals("on")) {
				console.setShowDebugText(true);
			} else if (input.equals("off")) {
				console.setShowDebugText(false);
			} else {
				this.warnIllegalArgument(assistant, args[1], args[0]);
			}
		}
	}
	
}
