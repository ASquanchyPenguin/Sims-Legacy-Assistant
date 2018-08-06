package com.tshcmiller.simsassistant;

import java.io.Serializable;

import com.tshcmiller.simsassistant.sims.TraitSystemMode;

public final class Settings implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>This class shouldn't be instantiated.</p>
	 */
	private Settings() {}

	public static TraitSystemMode defaultTraitSystem = TraitSystemMode.RANDOM;
	
	public static boolean defaultDebugText = false;
	public static boolean defaultTraitSharing = false;
	
	public static int defaultClearSize = 30;
}
