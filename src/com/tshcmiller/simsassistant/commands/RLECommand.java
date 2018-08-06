package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.stipulations.RandomLifeEvents;

public class RLECommand extends Command {

	@Override
	public void execute(SimsAssistant assistant, String[] args) {
		Console console = assistant.getConsole();
		console.partitionLine(4);
		
		String event = RandomLifeEvents.getRandomLifeEvent();
		String[] lines = event.split("\\s+%n\\s+");
		
		for (String line : lines) {
			console.printfln("%s", line);
		}
		
		console.partitionLine(4);
	}
}
