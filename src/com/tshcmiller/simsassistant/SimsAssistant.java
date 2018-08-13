package com.tshcmiller.simsassistant;

import static com.tshcmiller.simsassistant.Settings.initSettings;
import static com.tshcmiller.simsassistant.Settings.saveSettings;
import static com.tshcmiller.simsassistant.sims.Aspirations.loadAspriations;
import static com.tshcmiller.simsassistant.stipulations.Stipulations.loadStipulations;
import static com.tshcmiller.simsassistant.sims.Traits.loadTraits;
import static com.tshcmiller.simsassistant.sims.Traits.writeMaps;
import static com.tshcmiller.simsassistant.stipulations.RandomLifeEvents.loadRandomLifeEvents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.tshcmiller.simsassistant.commands.Command;
import com.tshcmiller.simsassistant.commands.SaveCommand;
import com.tshcmiller.simsassistant.sims.Sim;

public class SimsAssistant {
		
	public static final String VERSION = "1.0.1";
			
	private ArrayList<String> commands;
	private Console console;
	private Legacy legacy;
	private XMLReader xmlFile;
	
	private boolean running;
	
	private SimsAssistant() {
		this.commands = new ArrayList<String>();
		this.console = new Console();
		this.legacy = new Legacy();
		this.running = false;
	}
	
	/**
	 * <p>Deletes the specified legacy after confirmation.</p>
	 * @param console the current instance of the console
	 * @param name the name of the legacy to delete
	 */
	public static void deleteLegacy(Console console, String name) {
		File file = new File("res/saves/" + name + ".ser");
		console.partitionLine(2);
		
		if (!file.exists()) {
			console.writeNotification("No legacy called \"%s\" was found.", name);
			console.partitionLine(2);
			return;
		}
		
		if (console.confirmActionAsString("Delete the \"" + name + "\" legacy?")) {
			file.delete();
			console.writeNotification("The legacy was deleted.");
			console.partitionLine(2);
			return;
		}
		
		console.writeNotification("The legacy was not deleted.");
		console.partitionLine(2);
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
	 * <p>Gets the current Legacy.</p>
	 * @return the current legacy.
	 */
	public Legacy getLegacy() {
		return legacy;
	}
	
	/**
	 * <p>Gets the currently selected sim.</p>
	 * @return the currently selected sim.
	 */
	public Sim getSelectedSim() {
		return legacy.getSelectedSim();
	}
	
	/**
	 * <p>Loads the commands from the commands.xml file.</p>
	 */
	private void loadCommands() {
		console.writeDebugText("Loading commands from xml-file");
		this.xmlFile = new XMLReader("/xml/commands.xml");
		this.commands = xmlFile.getNodeList("commands");
		console.writeNotification("Loaded %d commands from file.", commands.size());
	}
	
	/**
	 * <p>Loads a legacy from a file.</p>
	 * @param console the current instance of the console
	 * @param name the name of the legacy
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 */
	public void loadLegacy(Console console, String name) throws IOException, ClassNotFoundException {
		File file = new File("res/saves/" + name + ".ser");
		
		if (!file.exists()) {
			console.writeNotification("No legacy with name \"%s\" was found.", name);
			return;
		}
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		legacy = (Legacy) (ois.readObject());
		console.writeNotification("Loaded \"%s\" legacy.", name);
		
		ois.close();
		fis.close();
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
	 * <p>The main loop for SimsAssistant. When the loop is exited, it proceeds to stop().</p>
	 */
	private void run() {
		running = true;
		
		console.partitionLine(3);
		
		String[] input;
		while (running) {
			Sim selectedSim = legacy.getSelectedSim();
			
			if (legacy.hasName()) {
				console.printfln("The %s Legacy: %s", legacy.getName(), legacy.getSimsAsList());
			} else {
				console.printfln("Current Legacy: %s", legacy.getSimsAsList());
			}
			
			if (selectedSim != null) {
				console.write("> (" + selectedSim.getFirstName() + " is selected): ");
			} else {
				console.write("> ");
			}
			input = console.readLineArray();
			
			if (!runCommand(input)) {
				console.writeNotification("Unknown command. Type \"help\" for a list of commands.");
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
	 * <p>Saves the legacy before closing the program.</p>
	 * @return if the legacy was saved
	 */
	private boolean saveLegacyOnClose() {
		if (console.confirmActionAsString("Do you want to save the current legacy?")) {
			String name = "";
			
			while (name.equals("")) {
				console.write("Legacy name: ");
				name = console.readLine();
			}
			
			new SaveCommand().execute(this, new String[] {"save", name});
			return true;
		}
		
		return false;
	}
	
	/**
	 * <p>Saves a legacy to a file with a specified name.</p>
	 * @param console the current instance of the console
	 * @param name the name of the file
	 * @throws IOException
	 */
	public void saveLegacyToFile(Console console, String name) throws IOException {
		File file = new File("res/saves/" + name + ".ser");
		
		if (file.exists()) {			
			if (!console.confirmAction("This legacy already exists. Do you wish to overwrite it?")) {
				return;
			}
		}
		
		console.writeDebugText("Attempting to save legacy: \"%s\"", name);
		FileOutputStream fos = new FileOutputStream("res/saves/" + name + ".ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(legacy);
		oos.close();
		fos.close();
		
		console.printfln("Legacy \"%s\" successfully saved!", name);
	}

	/**
	 * <p>Starts SimsAssistant and then proceeds to run()</p>
	 */
	private void start() {
		long start = System.currentTimeMillis();
		
		console.breakLine();
		console.partitionLine(3);
		console.printfln("Starting Sims Legacy Assistant%nVersion: %s", VERSION);
		console.partitionLine(3);
		
		initSettings(console);
		
		loadCommands();
		loadTraits(console);
		writeMaps(console);
		loadAspriations(console);
		loadRandomLifeEvents(console);
		loadStipulations(console);
		
		long stop = System.currentTimeMillis();
		console.writeDebugText("Start-up complete in %dms.", (stop - start));
		
		run();
	}
	
	/**
	 * <p>Stops and then exits SimsAssistant.</p>
	 */
	private void stop() {
		console.partitionLine(3);
		console.printfln("Exiting Sims Legacy Assistant.");
		console.breakLine();

		saveLegacyOnClose();
		saveSettings(console);

		console.printfln("Sims Legacy Assistant has closed.");
		console.partitionLine(3);
		console.close();
		System.exit(0);
	}

	/**
	 * <p>Stops SimsAssistant from running.</p>
	 */
	public void stopRunning() {
		running = false;
	}
}
