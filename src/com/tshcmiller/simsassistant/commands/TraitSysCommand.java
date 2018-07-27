package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.sims.TraitSystem;
import com.tshcmiller.simsassistant.sims.TraitSystemMode;

public class TraitSysCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		
		int length = args.length;
		
		if (hasEnoughArguments(assistant, length, 2)) {
			if (args[1].equalsIgnoreCase("mode")) {
				if (length >= 3) {
					int k = Tools.convertInteger(args[2]);
					if (Tools.validateInteger(k, 0, 2)) {
						switch (k) {
						case 0:
							TraitSystem.mode = TraitSystemMode.RANDOM;
							break;
						case 1:
							TraitSystem.mode = TraitSystemMode.BALANCED_MOOD;
							break;
						case 2:
							TraitSystem.mode = TraitSystemMode.BALANCED_TYPE;
							break;
						}
						console.writeln("Set Trait System Mode to: " + TraitSystem.mode);
					} else {
						console.writeNotification("%d isn\'t in the range [0, 2]", k);
					}
					return;
				}
				
				console.writeln("Current Trait System Mode: " + TraitSystem.mode);
			}
		}
	}

}
