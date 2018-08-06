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
		
		if (args.length == 1) {
			console.breakLine();
			console.partitionLine(5);
			
			console.printfln("The list of available commands.%n");
			
			for (String command : commands) {
				console.printfln("%-12s: %s.", command, xmlFile.getAttribute(command, "description"));
			}
			
			console.printfln("%nUse \"help <command>\" for help with a specific command.");
			
			console.partitionLine(5);
		}
		
		if (args.length == 2) {
			if (!commands.contains(args[1])) {
				console.writeNotification("\"%s\" was not a recognized command.", args[1]);
			}
			
			console.breakLine();
			console.partitionLine(5);
			
			console.printfln("Command format: name [required-arguments] (optional-argument)");
			
			console.printfln("Usage: %s", xmlFile.getAttribute(args[1], "usage"));
			console.partitionLine(5);
		}
	}

}
