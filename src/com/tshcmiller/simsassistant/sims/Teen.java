package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.SimsAssistant;

public class Teen extends Sim {

	private static final long serialVersionUID = 1L;
	
	public Teen(SimsAssistant assistant, TraitSystem traitSystem, String name) {
		super(name);
		
		this.age = 2;
		this.traitSystem = traitSystem;
		this.aspiration = Aspirations.getRandomAdultAspiration(assistant);
		this.traitSystem.fillTraits(assistant, 3);
		
		assistant.getConsole().writeNotification("The aspiration \"%s\" has been acquired!", aspiration);
	}
	
	public Teen(SimsAssistant assistant, Child child) {
		this(assistant, child.traitSystem, child.name);
		this.id = child.id;
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		assistant.getConsole().writeNotification("They grow up so fast. %s is now a Young-Adult!", this.toString());
		return new Adult(assistant, this);
	}

}
