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
		case "age":
			return new AgeCommand();
		case "clear":
			return new ClearCommand();
		case "create":
			return new CreateCommand();
		case "debug":
			return new DebugCommand();
		case "delete":
			return new DeleteCommand();
		case "deselect":
			return new DeleselectCommand();
		case "help":
			return new HelpCommand();
		case "list":
			return new ListCommand();
		case "load":
			return new LoadCommand();
		case "quit":
			return new QuitCommand();
		case "save":
			return new SaveCommand();
		case "select":
			return new SelectCommand();
		case "trmode":
			return new TraitModeCommand();
		case "show":
			return new ShowCommand();
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
	 * <p>Warns the user that there is no sim current selected.</p>
	 * @param console the current instance of the console
	 */
	protected void warnNoSelectedSim(Console console) {
		console.printfln("No sim is currently selected. Use \'select\' [sim-ID] to select a sim.");
	}
	
	/**
	 * <p>Warns the user that they have entered an illegal argument.</p>
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
