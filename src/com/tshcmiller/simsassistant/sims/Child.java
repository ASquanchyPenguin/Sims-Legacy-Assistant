package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.SimsAssistant;

public class Child extends Sim {

	private static final long serialVersionUID = 1L;

	public Child(SimsAssistant assistant, TraitSystem traitSystem, String name) {
		super(name);
		
		this.age = 1;
		this.traitSystem = traitSystem;
		this.aspiration = Aspirations.getRandomChildAspiration();
		this.traitSystem.fillTraits(assistant, 2);
		
		assistant.getConsole().writeNotification("The aspiration \"%s\" has been acquired!", aspiration);
	}
	
	public Child(SimsAssistant assistant, Toddler toddler) {
		this(assistant, toddler.traitSystem, toddler.name);
		this.id = toddler.id;
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		assistant.getConsole().writeNotification("Can you believe it? %s is now a Teen!", this.toString());
		return new Teen(assistant, this);
	}
}
