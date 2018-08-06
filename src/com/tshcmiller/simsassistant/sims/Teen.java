package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.SimsAssistant;

public class Teen extends Sim {

	private static final long serialVersionUID = 1L;
	
	public Teen(SimsAssistant assistant, TraitSystem traitSystem, String name) {
		super(name);
		
		this.age = 2;
		this.traitSystem = traitSystem;
	}
	
	public Teen(SimsAssistant assistant, Child child) {
		this(assistant, child.traitSystem, child.name);
		this.id = child.id;
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		assistant.getConsole().writeNotification("They grow up so fast. %s is now a Young-Adult!", this.toString());
		Adult adult = new Adult(assistant, this);
		adult.traitSystem.addTrait(assistant, name);
		return adult;
	}

	@Override
	public void rollTraits(SimsAssistant assistant) {
		this.traitSystem.fillTraits(assistant, name, 3);
	}

}
