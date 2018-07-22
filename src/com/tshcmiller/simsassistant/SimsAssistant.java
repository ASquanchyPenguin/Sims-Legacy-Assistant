package com.tshcmiller.simsassistant;

import java.util.ArrayList;

import com.tshcmiller.simsassistant.commands.Command;

public class SimsAssistant {
	
	public static final String VERSION = "0.1.2";
	
	private ArrayList<String> commands;
	private Console console;
	private XMLReader xmlFile;
	
	private boolean running;
	
	private SimsAssistant() {
		this.commands = new ArrayList<String>();
		this.console = new Console();
		this.running = false;
	}
	
	/**
	 * <p>Gets the list of commands loaded into SimsAssistant.</p>
	 * @return the command list
	 */
	public ArrayList<String> getCommands() {
		return commands;
	}
	
	/**
	 * <p>Gets the console for the SimsAssistant.</p>
	 * @return the console object
	 */
	public Console getConsole() {
		return console;
	}
	
	/**
	 * <p>Gets the XMLReader for the Command.xml file.</p>
	 * @return the XMLReader
	 */
	public XMLReader getCommandFile() {
		return xmlFile;
	}
	
	/**
	 * <p>Loads the commands from the commands.xml file.</p>
	 */
	private void loadCommands() {
		this.xmlFile = new XMLReader("/xml/commands.xml");
		this.commands = xmlFile.getNodeList("commands");
		console.writeDebugText("Loaded all commands.");
	}
	
	/**
	 * <p>Creates a new instance of the SimsAssistant.</p>
	 * @param args the command line arguments at the start of the program.
	 */
	public static void main(String[] args) {
		new SimsAssistant().start();
	}

	/**
	 * <p>Returns if SimsAssistant is currently running.</p>
	 * */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * <p>Stops SimsAssistant from running.</p>
	 */
	public void stopRunning() {
		running = false;
		console.close();
	}
	
	/**
	 * <p>The main loop for SimsAssistant. When the loop is exited, it proceeds to stop().</p>
	 */
	private void run() {
		running = true;
		
		console.partitionLine(2);
		
		String[] input;
		while (running) {
			console.write("[Enter a command]: ");
			input = console.readLineArray();
			
			if (!runCommand(input)) {
				console.writeNotification("Unknown command. Type \"help\" for help.");
			}
			
			console.breakLine();
		}
		
		stop();
	}
	
	/**
	 * <p>Processes the command (if any).</p>
	 * @param input The command entered into the console
	 * @return if a command was executed
	 */
	private boolean runCommand(String[] input) {
		String commandName = input[0].toLowerCase();
		
		if (commands.contains(commandName)) {
			Command command = Command.getCommand(commandName);
			command.execute(this, input);
			
			return true;
		} 
		
		return false;
	}

	/**
	 * <p>Starts SimsAssistant and then proceeds to run()</p>
	 */
	private void start() {
		console.breakLine();
		console.partitionLine(2);
		console.writeNotification("Starting Sims Assistant [Version: %s]", VERSION);
		console.setShowDebugText(true);
		loadCommands();
		
		run();
	}
	
	/**
	 * <p>Stops and then exits SimsAssistant.</p>
	 */
	private void stop() {
		console.writeNotification("Exiting SimsAssistant.");
		System.exit(0);
	}
}
