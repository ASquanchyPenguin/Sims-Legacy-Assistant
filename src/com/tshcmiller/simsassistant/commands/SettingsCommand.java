package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.Settings;
import com.tshcmiller.simsassistant.SimsAssistant;

public class SettingsCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		int length = args.length;
		
		if (length == 2) {
			Settings.showValue(console, args[1]);
			return;
		}
		
		if (length == 3) {
			Settings.setValue(console, args[1], args[2]);
			return;
		}
	}

}
