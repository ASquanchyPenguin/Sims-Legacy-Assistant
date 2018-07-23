package com.tshcmiller.simsassistant;

public class SimsAssistant {
	
	public static final String VERSION = "0.1.1";
	
	private Console console;
	
	private boolean running;
	
	private SimsAssistant() {
		this.console = new Console();
		this.running = false;
	}
	
	/**
	 * <p>Gets the console for the SimsAssistant.</p>
	 * @return the console object
	 */
	public Console getConsole() {
		return console;
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
	}
	
	/**
	 * <p>The main loop for SimsAssistant. When the loop is exited, it proceeds to stop().</p>
	 */
	private void run() {
		while (running) {
			//TODO: Process Commands
		}
		
		stop();
	}
	
	/**
	 * <p>Starts SimsAssistant and then proceeds to run()</p>
	 */
	private void start() {
		console.printfln("Starting Sims Assistant [Version: %s]", VERSION);
		
		run();
	}
	
	/**
	 * <p>Stops and then exits SimsAssistant.</p>
	 */
	private void stop() {
		console.writeln("Exiting SimsAssistant.");
		System.exit(0);
	}
	
}
