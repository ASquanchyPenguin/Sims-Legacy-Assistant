package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Legacy;
import com.tshcmiller.simsassistant.SimsAssistant;

public class NameCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		Legacy legacy = assistant.getLegacy();
		
		console.partitionLine(3);
		if (hasEnoughArguments(assistant, args.length, 2)) {
			if (legacy.hasName() && !console.confirmActionAsString("Do you want to rename the legacy?")) {
				console.printfln("Did not change the legacy name.");
				console.partitionLine(3);
				return;
			} 
			
			legacy.setName(args[1]);
			console.printfln("Legacy is now called %s", args[1]);
			console.partitionLine(3);
		}
	}
}
