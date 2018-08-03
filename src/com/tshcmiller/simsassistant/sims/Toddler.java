package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.SimsAssistant;

public class Toddler extends Sim {
	
	private static final long serialVersionUID = 1L;
	
	public Toddler(SimsAssistant assistant, String name) {
		super(name);
		
		this.age = 0;
		this.aspiration = "Become a child";
		this.traitSystem.addTrait(assistant);
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		assistant.getConsole().writeNotification("Happy birthday! %s is now a Child!", this.toString());
		return new Child(assistant, this);
	}
}
