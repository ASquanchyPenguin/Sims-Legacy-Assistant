package com.tshcmiller.simsassistant.commands;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;
import com.tshcmiller.simsassistant.XMLReader;

public abstract class Command {
	
	/**
	 * <p>Executes the command specified by its name.</p>
	 */
	public abstract void execute(SimsAssistant assistant, String[] args);
	
	/**
	 * <p>Gets the command associated with a specified name.</p>
	 * @param name the name of the command
	 * @return the Command Object to execute.
	 */
	public static Command getCommand(String name) {
		switch (name) {
		case "clear":
			return new ClearCommand();
		case "debug":
			return new DebugCommand();
		case "help":
			return new HelpCommand();
		case "quit":
			return new QuitCommand();
		default:
			System.err.printf(">>>NOTE: %s is not a registered command!<<<%n", name);
			return null;
		}
	}
	
	/**
	 * <p>Validates that the command has the proper number of arguments.</p>
	 * @param assistant the current instance of the program
	 * @param length the length of the command line
	 * @param arguments the minimum number of arguments in that command
	 */
	protected boolean hasEnoughArguments(SimsAssistant assistant, int length, int arguments) {
		Console console = assistant.getConsole();
		
		if (length < arguments) {
			console.writeNotification("This command requires %d arguments to execute.", arguments);
			return false;
		}
		
		return true;
	}
	
	/**
	 * <p> Warns the user that they have entered an illegal argument.</p>
	 * @param assistant the current instance of the program
	 * @param illegalArgument the illegal argument to warn them about
	 * @param name the name of the command
	 */
	protected void warnIllegalArgument(SimsAssistant assistant, String illegalArgument, String name) {
		Console console = assistant.getConsole();
		XMLReader xmlFile = assistant.getCommandFile();
		
		String usage = xmlFile.getAttribute(name, "usage");
		console.writeNotification("Unable to execute this command with these arguments. Try \"%s\" instead.", usage);
	}
}
