package com.tshcmiller.simsassistant.commands;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.XMLReader;

public class HelpCommand extends Command {

	@Override
	public void execute(SimsAssistant assistant,  String[] args) {
		Console console = assistant.getConsole();
		ArrayList<String> commands = assistant.getCommands();
		XMLReader xmlFile = assistant.getCommandFile();
		
		console.breakLine();
		console.partitionLine(3);
		
		for (String command : commands) {
			console.printfln("%-25s: %s", xmlFile.getAttribute(command, "usage"), xmlFile.getAttribute(command, "description"));
		}
		
		console.partitionLine(3);
	}

}
