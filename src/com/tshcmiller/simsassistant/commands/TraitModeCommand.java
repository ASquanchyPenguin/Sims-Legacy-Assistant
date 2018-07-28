package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.Tools;
import com.tshcmiller.simsassistant.sims.TraitSystem;
import com.tshcmiller.simsassistant.sims.TraitSystemMode;

public class TraitModeCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		
		int length = args.length;
		
		if (length == 1) {
			console.writeln("Current Trait System Mode: " + TraitSystem.mode);			
			return;
		}
		
		if (hasEnoughArguments(assistant, length, 2)) {
			int k = Tools.convertInteger(args[1]);
			if (Tools.validateInteger(k, 1, 3)) {
				switch (k) {
				case 1:
					TraitSystem.mode = TraitSystemMode.RANDOM;
					break;
				case 2:
					TraitSystem.mode = TraitSystemMode.BALANCED_MOOD;
					break;
				case 3:
					TraitSystem.mode = TraitSystemMode.BALANCED_TYPE;
					break;
				}
				
				console.writeln("Set Trait System Mode to: " + TraitSystem.mode);
				return;
			}
			this.warnIllegalArgument(assistant, args[1], args[0]);
		}
	}

}
