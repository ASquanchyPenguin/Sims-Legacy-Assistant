package com.tshcmiller.simsassistant;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.tshcmiller.simsassistant.sims.Sim;

public class Legacy implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Sim> sims;
	private Sim selectedSim;
	
	public Legacy() {
		this.sims = new HashMap<String, Sim>();
	}
	
	/**
	 * <p>Adds a sim to the legacy and assigns them an id.</p>
	 * @param console the current instance of the console
	 * @param sim the sim to be added
	 */
	public void addSim(Console console, Sim sim) {
		String name = sim.getFirstName().toLowerCase();
		String key = name;
		
		int tag = 1;
		while (sims.containsKey(key)) {
			key = (name) + (++tag);
		}
		
		sims.put(key, sim);
		sim.assignID(key);
		console.writeNotification("Added %s to the list of current sims! Their ID is: %s", sim.getName(), key);
		console.partitionLine(2);
	}
	
	/**
	 * <p>Deletes all sims from the legacy.</p>
	 * @param console the current instance of the console
	 */
	public void deleteAllSims(Console console) {
		console.partitionLine(2);
		console.printfln("WARNING: All sims will be deleted from this legacy.");
		
		if (console.confirmAction("Are you sure?")) {
			sims.clear();
			console.printfln("All sims have been deleted from the legacy.");
			return;
		}
		
		console.printfln("No sims were deleted from the legacy.");
	}
	
	/**
	 * <p>Deletes a specified sim from the current legacy.</p>
	 * @param console the current instance of the console
	 * @param ID the ID of the specified sim.
	 */
	public void deleteSim(Console console, String ID) {
		Sim sim = getSimByID(ID); 
		
		if (sim != null) {			
			console.partitionLine(2);
			console.printfln("WARNING: %s is about to be deleted.", sim.getName());
			if (console.confirmAction("Are you sure?")) {
				if (selectedSim == sim) {
					selectedSim = null;
				}
				
				sims.remove(ID);
				console.writeNotification("%s has been deleted.", sim.getName());
				
				return;
			} 
				
			console.writeNotification("%s lives another day!", sim.getName());
			return;
		}
		
		console.writeNotification("\"%s\" was not a recognized Sim-ID.", ID);
	}
	
	/**
	 * <p>Gets the currently selected sim.</p>
	 * @return the currently selected sim
	 */
	public Sim getSelectedSim() {
		return selectedSim;
	}
	
	/**
	 * <p>Gets the sim from the current legacy by their ID.</p>
	 * @param id the id of the desired sim
	 * @return the sim with that id
	 */
	public Sim getSimByID(String id) {
		return sims.get(id);
	}
	
	/**
	 * <p>Selects a certain sim in the legacy.</p>
	 * @param console the current instance of the console
	 * @param id the id of the specified sim
	 */
	public void selectSim(Console console, String id) {
		if (sims.containsKey(id)) {
			selectedSim = sims.get(id);
			
			return;
		}
		
		console.printfln("No sim with ID \"%s\" was found", id);
	}
	
	/**
	 * <p>Sets the selected sim to the desired sim.</p>
	 * @param sim the sim to select
	 */
	public void setSelectedSim(Sim sim) {
		this.selectedSim = sim;
	}
	
	/**
	 * <p>Shows the sims in the current legacy.</p>
	 * @param console the current instance of the console
	 */
	public void showSims(Console console) {
		Collection<Sim> sims = this.sims.values();	
		console.printfln(sims.toString());
	}
	
	/**
	 * <p>Updates a sim in the current legacy.</p>
	 * @param id the id of the sim
	 * @param sim the new representation of that sim
	 */
	public void updateSim(String id, Sim sim) {
		sims.replace(id, sim);
	}
	
}
