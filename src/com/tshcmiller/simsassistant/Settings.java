package com.tshcmiller.simsassistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import com.tshcmiller.simsassistant.sims.TraitSystem;
import com.tshcmiller.simsassistant.sims.TraitSystemMode;

public final class Settings implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>This class shouldn't be instantiated.</p>
	 */
	private Settings() {}

	private static HashMap<String, Object> settings = new HashMap<String, Object>();
	
	/**
	 * <p>Creates the default Settings.ser file, if it does not exist.</p>
	 * @param console the current instance of the console
	 */
	private static void createDefaultSettingsFile(Console console) {
		console.writeDebugText("Creating default settings file");
		
		settings.put("clrsize", 30);
		settings.put("debug", false);
		settings.put("trmode", TraitSystemMode.RANDOM);
		settings.put("trshare", false);

		saveSettings(console);
	}
	
	/**
	 * <p>Gets the value from the specified key.</p>
	 * @param key the desired setting
	 * @return the value for that setting
	 */
	public static Object getValue(String key) {
		return settings.get(key);
	}
	
	/**
	 * <p>Initializes settings on start-up and creates a default Settings.ser file if needed.</p>
	 * @param console the current instance of the console.
	 */
	public static void initSettings(Console console) {
		File file = new File("res/settings.ser");
		
		if (!file.exists()) {
			createDefaultSettingsFile(console);
			return;
		}
		
		try {
			loadSettingsFromFile(console);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * <p>Loads the Settings.ser file, if it exists.</p>
	 * @param console the current instance of the console
	 * @throws IOException if the file was not found
	 * @throws ClassNotFoundException if the class was not found
	 */
	@SuppressWarnings("unchecked")
	public static void loadSettingsFromFile(Console console) throws IOException, ClassNotFoundException {
		console.writeNotification("Loading settings from file.");
		
		FileInputStream fis = new FileInputStream(new File("res/settings.ser"));
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		settings = (HashMap<String, Object>) (ois.readObject());
		
		console.setShowDebugText((boolean) (getValue("debug")));
		TraitSystem.mode = (TraitSystemMode) (getValue("trmode"));
		Legacy.preventTraitSharing = (boolean) (getValue("trshare"));
		
		ois.close();
		fis.close();
	}
	
	/**
	 * <p>Saves the settings to file.</p>
	 * @param console the current instance of the console
	 */
	public static void saveSettings(Console console) {		
		try {
			FileOutputStream fos = new FileOutputStream(new File("res/settings.ser"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(settings);
			
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		console.printfln("Settings have been saved to file.");
	}

	/**
	 * <p>Sets the value of a certain key - if it is valid for that setting.</p>
	 * @param console the current instance of the console
	 * @param key the desired setting
	 * @param value the value for that key
	 */
	public static void setValue(Console console, String key, String value) {
		if (!settings.containsKey(key)) {
			console.writeNotification("%s wasn\'t a recognized key.", key);
			return;
		}
		
		if (verifyKeyAndValue(key, value)) {
			console.writeNotification("%s was set to %s", key, value);
			return;
		}
		
		console.writeNotification("Unknown or invalid key or value. Unable to edit settings.");		
	}
	
	/**
	 * <p>Shows the current value of the setting.</p>
	 * @param console the current instance of the console
	 * @param key the desired setting
	 */
	public static void showValue(Console console, String key) {		
		if (!settings.containsKey(key)) {
			console.writeNotification("%s wasn\'t a recognized key.", key);
			return;
		}
		
		console.writeNotification("%s is set to %s.", key, settings.get(key).toString());		
	}
	
	/**
	 * <p>Verifies that the specified value is a valid input for that setting.</p>
	 * @param key the desired setting
	 * @param value the value to change the setting to
	 * @return if the setting was replaced (true) or if it was not (false)
	 */
	private static boolean verifyKeyAndValue(String key, String value) {
		if (key.equalsIgnoreCase("debug") || key.equalsIgnoreCase("trshare")) {
			if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
				boolean val = (boolean) Boolean.valueOf(value);
				settings.put(key, val);
				return true;
			}
		}
		
		int k = Tools.convertInteger(value);
		
		if (k == -1) {
			return false;
		}
		
		if (key.equalsIgnoreCase("trmode") && Tools.validateInteger(k, 1, 3)) {
			settings.put(key, k);
			return true;
		}
		
		if (key.equalsIgnoreCase("clrsize") && Tools.validateInteger(k, 1, 150)) {
			settings.put(key, k);
			return true;
		}
		
		return false;
	}
}
