package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.SimsAssistant;

public class Toddler extends Sim {
	
	private static final long serialVersionUID = 1L;
	
	public Toddler(SimsAssistant assistant, String name) {
		super(name);
		
		this.age = 0;
		this.aspiration = "Become a child";
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		assistant.getConsole().writeNotification("Happy birthday! %s is now a Child!", this.toString());
		Child child = new Child(assistant, this);
		child.traitSystem.addTrait(assistant, name);
		child.rollAspiration(assistant);
		return child;
	}
	
	@Override
	public void rollAspiration(SimsAssistant assistant) {
		aspiration = "Become a child";
	}

	@Override
	public void rollTraits(SimsAssistant assistant) {
		this.traitSystem.addTrait(assistant, name);
	}
}
