package com.tshcmiller.simsassistant;

import static com.tshcmiller.simsassistant.sims.Aspirations.loadAspriations;
import static com.tshcmiller.simsassistant.sims.Traits.loadTraits;
import static com.tshcmiller.simsassistant.sims.Traits.writeMaps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.tshcmiller.simsassistant.commands.Command;
import com.tshcmiller.simsassistant.sims.Sim;

public class SimsAssistant {
		
	public static final String VERSION = "0.4.1";
	
	public static Sim selectedSim;
	
	private static HashMap<String, Sim> currentLegacy = new HashMap<String, Sim>();
	
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
	 * <p>Adds a sim to the current legacy and assigns them an id.</p>
	 * @param console the current instance of the console
	 * @param sim the sim to be added
	 */
	public static void addSimToCurrentLegacy(Console console, Sim sim) {
		String name = sim.getFirstName();
		String lcName = name.toLowerCase();
		String key = lcName;
		
		int tag = 1;
		while (currentLegacy.containsKey(key)) {
			key = (lcName) + (++tag);
		}
		
		currentLegacy.put(key, sim);
		sim.assignID(key);
		console.writeNotification("Added %s to the list of current sims! Their ID is: %s", sim.getName(), key);
		console.partitionLine(2);
	}
	
	/**
	 * <p>Deletes all sims from the legacy.</p>
	 * @param console the current instance of the console
	 */
	public static void deleteAllSimsFromLegacy(Console console) {
		console.partitionLine(2);
		console.printfln("WARNING: All sims will be deleted from this legacy.");
		
		if (console.confirmAction("Are you sure?")) {
			currentLegacy.clear();
			console.printfln("All sims have been deleted from the legacy.");
			return;
		}
		
		console.printfln("No sims were deleted from the legacy.");		
	}
	
	/**
	 * <p>Deletes the specified legacy after confirmation.</p>
	 * @param console the current instance of the console
	 * @param name the name of the legacy to delete
	 */
	public static void deleteLegacy(Console console, String name) {
		File file = new File("res/saves/" + name + ".ser");
		
		if (!file.exists()) {
			console.writeNotification("No legacy called \"%s\" was found.", name);
			return;
		}
		
		if (console.confirmAction("Delete the \"" + name + "\" legacy?")) {
			file.delete();
			console.writeNotification("The legacy was deleted.");
			return;
		}
		
		console.writeNotification("The legacy was not deleted.");
	}
	
	/**
	 * <p>Deletes a specified sim from the current legacy.</p>
	 * @param console the current instance of the console
	 * @param ID the ID of the specified sim.
	 */
	public static void deleteSimFromCurrentLegacy(Console console, String ID) {
		Sim sim = getSimByID(ID); 
		
		if (sim != null) {			
			console.partitionLine(2);
			console.printfln("WARNING: %s is about to be deleted.", sim.getName());
			if (console.confirmAction("Are you sure?")) {
				currentLegacy.remove(ID);
				console.writeNotification("%s has been deleted.", sim.getName());
				return;
			} 
				
			console.writeNotification("%s lives another day!", sim.getName());
			return;
		}
		
		console.writeNotification("\"%s\" was not a recognized Sim-ID.", ID);
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
	 * <p>Gets the sim from the current legacy by their ID.</p>
	 * @param id the id of the desired sim
	 * @return the sim with that id
	 */
	public static Sim getSimByID(String id) {
		if (currentLegacy.containsKey(id)) {
			return currentLegacy.get(id);
		}
		
		return null;
	}
	
	/**
	 * <p>Loads the commands from the commands.xml file.</p>
	 */
	private void loadCommands() {
		console.writeDebugText("Loading commands from xml-file");
		this.xmlFile = new XMLReader("/xml/commands.xml");
		this.commands = xmlFile.getNodeList("commands");
		console.writeDebugText("Loaded %d commands.", commands.size());
	}
	
	/**
	 * <p>Loads a legacy from a file.</p>
	 * @param console the current instance of the console
	 * @param name the name of the legacy
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static void loadLegacy(Console console, String name) throws IOException, ClassNotFoundException {
		File file = new File("res/saves/" + name + ".ser");
		
		if (!file.exists()) {
			console.writeNotification("No legacy with name \"%s\" was found.", name);
			return;
		}
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		currentLegacy = (HashMap<String, Sim>) (ois.readObject());
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
		
		console.partitionLine(2);
		
		String[] input;
		while (running) {
			if (selectedSim != null) {
				console.write("[Enter a command, \"" + selectedSim.getName() + "\" is selected]: ");
			} else {
				console.write("[Enter a command]: ");
			}
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
	 * <p>Saves a legacy to a file with a specified name.</p>
	 * @param console the current instance of the console
	 * @param name the name of the file
	 * @throws IOException
	 */
	public static void saveLegacyToFile(Console console, String name) throws IOException {
		File file = new File("res/saves/" + name + ".ser");
		
		if (file.exists()) {			
			if (!console.confirmAction("This legacy already exists. Do you wish to overwrite it?")) {
				return;
			}
		}
		
		console.writeDebugText("Attempting to save legacy: \"%s\"", name);
		FileOutputStream fos = new FileOutputStream("res/saves/" + name + ".ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(currentLegacy);
		oos.close();
		fos.close();
		
		console.writeNotification("Legacy \"%s\" successfully saved!", name);
	}
	
	/**
	 * <p>Selects a sim for easier command entry.</p>
	 * @param console the current instance of the console
	 * @param id the id of the specified sim
	 */
	public static void selectSim(Console console, String id) {
		if (currentLegacy.containsKey(id)) {
			selectedSim = currentLegacy.get(id);
			return;
		}
		
		console.printfln("No sim with ID \"%s\" was found", id);
	}
	
	/**
	 * <p>Shows the sims in the current legacy.</p>
	 * @param console the current instance of the console
	 */
	public static void showCurrentLegacy(Console console) {
		Collection<Sim> sims = currentLegacy.values();		
		console.printfln(sims.toString());
	}

	/**
	 * <p>Starts SimsAssistant and then proceeds to run()</p>
	 */
	private void start() {
		long start = System.currentTimeMillis();
		
		console.breakLine();
		console.partitionLine(2);
		console.writeNotification("Starting Sims Legacy Assistant [Version: %s]", VERSION);
		
		loadCommands();
		loadTraits(console);
		writeMaps(console);
		loadAspriations(console);
		
		long stop = System.currentTimeMillis();
		console.writeDebugText("Start-up complete in %dms.", (stop - start));
		
		run();
	}
	
	/**
	 * <p>Stops and then exits SimsAssistant.</p>
	 */
	private void stop() {
		console.writeNotification("Exiting SimsAssistant.");
		System.exit(0);
	}
	
	/**
	 * <p>Stops SimsAssistant from running.</p>
	 */
	public void stopRunning() {
		running = false;
		console.close();
	}
	
	/**
	 * <p>Updates a sim in the current legacy.</p>
	 * @param id the id of the sim
	 * @param sim the new representation of that sim
	 */
	public static void updateSim(String id, Sim sim) {
		currentLegacy.replace(id, sim);
	}
}
